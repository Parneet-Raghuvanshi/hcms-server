package com.developer.hcmsserver.controller;

import com.developer.hcmsserver.dto.AddressDto;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.models.GeneralResponse;
import com.developer.hcmsserver.models.requests.AddressDeleteRequest;
import com.developer.hcmsserver.models.requests.AddressUpdateRequest;
import com.developer.hcmsserver.services.interfaces.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AddressService addressService;

    @PostMapping("/delete")
    public GeneralResponse delete(@RequestBody AddressDeleteRequest addressDeleteRequest) {
        if (addressDeleteRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        addressService.delete(addressDeleteRequest.getEmail());
        return new GeneralResponse(false,"SUCCESS","Address Details deleted Successfully!",null);
    }

    @PostMapping("/update")
    public GeneralResponse update(@RequestBody AddressUpdateRequest addressUpdateRequest) {
        if (addressUpdateRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        AddressDto addressDto = mapper.map(addressUpdateRequest, AddressDto.class);
        addressService.update(addressDto, addressUpdateRequest.getEmail());
        return new GeneralResponse(false,"SUCCESS","Address Details updated Successfully!",null);
    }
}
