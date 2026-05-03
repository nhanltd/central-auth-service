package com.nhanthanhle.centralauthservice.service;

import com.nhanthanhle.centralauthservice.dto.request.AuthenticationRequest;
import com.nhanthanhle.centralauthservice.dto.request.IntrospectRequest;
import com.nhanthanhle.centralauthservice.dto.response.AuthenticationResponse;
import com.nhanthanhle.centralauthservice.dto.response.IntrospectResponse;
import com.nhanthanhle.centralauthservice.entity.User;
import com.nhanthanhle.centralauthservice.exception.AppException;
import com.nhanthanhle.centralauthservice.exception.ErrorCode;
import com.nhanthanhle.centralauthservice.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationService {

    UserRepository userRepository;
    @NonFinal // để ko inject vào constructor
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;


    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        var verifed = signedJWT.verify(verifier);
        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponse.builder()
                .valid(verifed && expityTime.after(new Date()))
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findUserByUsername(authenticationRequest.getUsername()).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_EXISTED));


        // check password matches
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    /// raw password == user password
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }

    private String generateToken(User user){

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("nhanthanhle.centralauthservice")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1h
                .claim("scope", buildScope(user))
                .build();
        // claims là các thông tin lưu trong payload: ví dụ chuỗi gồm sub, role,.. th sub và role là claim
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject  = new JWSObject(jwsHeader, payload); // jws gồm header và payload

        // ký vào token, de sign cần 1 thuật toán để sign
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

}
