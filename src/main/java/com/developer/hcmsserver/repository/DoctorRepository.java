package com.developer.hcmsserver.repository;

import com.developer.hcmsserver.entity.DoctorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CrudRepository<DoctorEntity,Long> {
    DoctorEntity findDoctorEntityByUser_Email(String email);
}
