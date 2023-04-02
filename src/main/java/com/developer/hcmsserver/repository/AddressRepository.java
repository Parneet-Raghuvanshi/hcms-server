package com.developer.hcmsserver.repository;

import com.developer.hcmsserver.entity.AddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity,Long> {
}
