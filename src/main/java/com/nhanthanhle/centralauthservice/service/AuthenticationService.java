package com.nhanthanhle.centralauthservice.service;

import com.nhanthanhle.centralauthservice.dto.request.AuthenticationRequest;
import com.nhanthanhle.centralauthservice.dto.request.IntrospectRequest;
import com.nhanthanhle.centralauthservice.dto.response.AuthenticationResponse;
import com.nhanthanhle.centralauthservice.dto.response.IntrospectResponse;
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

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationService {

//    @NonFinal
//    @Value("${jwt.signerKey}")
//    protected String SIGNER_KEY;
    UserRepository userRepository;
    @NonFinal // để ko inject vào constructor
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    public IntrospectResponse introspect1(IntrospectRequest request) throws JOSEException, ParseException {

        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        var verifed = signedJWT.verify(verifier);
        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return IntrospectResponse.builder()
                .valid(verifed && expityTime.after(new Date()))
                .build();
    }


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
        var token = generateTokenv1(authenticationRequest.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }
    private String generateToken(String username){

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("nhanthanhle.centralauthservice")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1h
                .claim("customClaim", "Custom")
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

    private String generateTokenv1(String username) {
        // 1 token gồm có header.payload và signature
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512); // header
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("nhanthanhle.centralauthservice")
                .issueTime(new Date()) // thời gian khởi tạo
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000)) // thời gian token này hết hạn: 1h
                .claim("NhanCustomClaim", "nhanCustom")
                .build();

        // tạo payload
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // tạo jws object
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        // sign chữ ký với mã macsinger

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes())); // dong dấu đỏ vào tài liệu
            return jwsObject.serialize(); // biến jwsobject thành 1 chuỗi string để gửi đi,vi http chỉ nhân dưới chuỗi text
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }

    }

}
