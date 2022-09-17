package com.developer.hcmsserver.repository;

import com.developer.hcmsserver.entity.PasswordResetTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRequestRepository extends CrudRepository<PasswordResetTokenEntity,Long> {
     PasswordResetTokenEntity findByToken(String token);
}
