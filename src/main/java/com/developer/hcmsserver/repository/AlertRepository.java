package com.developer.hcmsserver.repository;

import com.developer.hcmsserver.entity.AlertEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends CrudRepository<AlertEntity,Long> {
    AlertEntity findAlertEntityByPublicId(String alertId);
}
