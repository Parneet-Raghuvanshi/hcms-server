package com.developer.hcmsserver.repository;

import com.developer.hcmsserver.entity.CommonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonRepository extends CrudRepository<CommonEntity,Long> {
}
