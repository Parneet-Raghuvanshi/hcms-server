package com.developer.hcmsserver.repository;

import com.developer.hcmsserver.entity.HospitalEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends CrudRepository<HospitalEntity,Long> {
    HospitalEntity findHospitalEntityByPublicId(String publicId);
}
