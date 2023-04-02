package com.developer.hcmsserver.repository;

import com.developer.hcmsserver.entity.PatientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
    PatientEntity findPatientEntityByUser_Email(String email);
}
