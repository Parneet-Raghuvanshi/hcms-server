package com.developer.hcmsserver.repository;

import com.developer.hcmsserver.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findUserByEmail(String email);
    UserEntity findUserByEmailVerificationToken(String token);
}
