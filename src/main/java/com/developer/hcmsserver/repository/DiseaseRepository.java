package com.developer.hcmsserver.repository;

import com.developer.hcmsserver.entity.DiseaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends CrudRepository<DiseaseEntity,Long> {
    DiseaseEntity findDiseaseEntityByPublicId(String publicId);
}
