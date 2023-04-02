package com.developer.hcmsserver.services.interfaces;

import com.developer.hcmsserver.dto.AddressDto;

public interface AddressService {
    void delete(String email);
    void update(AddressDto addressDto,String email);
}
