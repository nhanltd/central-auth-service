package com.nhanthanhle.centralauthservice.repository;
import com.nhanthanhle.centralauthservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
