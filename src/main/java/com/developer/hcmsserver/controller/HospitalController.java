package com.developer.hcmsserver.controller;

import com.developer.hcmsserver.dto.DoctorDto;
import com.developer.hcmsserver.dto.HospitalDto;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.models.GeneralResponse;
import com.developer.hcmsserver.models.requests.GeneralAssignRemoveRequest;
import com.developer.hcmsserver.models.requests.GeneralPublicIdRequest;
import com.developer.hcmsserver.models.requests.HospitalAddRequest;
import com.developer.hcmsserver.models.requests.HospitalRateRequest;
import com.developer.hcmsserver.services.interfaces.HospitalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/add")
    public GeneralResponse add(@RequestBody HospitalAddRequest hospitalAddRequest) {
        if (hospitalAddRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        HospitalDto hospital = mapper.map(hospitalAddRequest,HospitalDto.class);
        hospitalService.add(hospital);
        return new GeneralResponse(false,"SUCCESS","Hospital Created Successfully!",null);
    }

    @PostMapping("/assign")
    public GeneralResponse assign(@RequestBody GeneralAssignRemoveRequest request) {
        if (request.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        hospitalService.assign(request.getEmail(),request.getPublicId());
        return new GeneralResponse(false,"SUCCESS","Hospital Assigned Successfully!",null);
    }

    @PostMapping("/doctors")
    public GeneralResponse getDoctors(@RequestBody GeneralPublicIdRequest request) {
        if (request.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        List<DoctorDto> doctors = hospitalService.getDoctors(request.getPublicId());
        return new GeneralResponse(false,"SUCCESS","Hospital all doctors fetched Successfully!",doctors);
    }

    @PostMapping("/rate")
    public GeneralResponse rate(@RequestBody HospitalRateRequest hospitalRateRequest) {
        if (hospitalRateRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        hospitalService.rate(hospitalRateRequest.getPublicId(),hospitalRateRequest.getRating());
        return new GeneralResponse(false,"SUCCESS","Hospital Rated Successfully!",null);
    }

    @PostMapping("/remove")
    public GeneralResponse remove(@RequestBody GeneralAssignRemoveRequest request) {
        if (request.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        hospitalService.remove(request.getEmail(),request.getPublicId());
        return new GeneralResponse(false,"SUCCESS","Hospital Removed Successfully!",null);
    }
}
