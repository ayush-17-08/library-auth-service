package com.library.Auth_service.repo;

import com.library.Auth_service.entity.CredentialEntity;
import com.library.Auth_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity,Long> {
    CredentialEntity findByUsername(String username);

}
