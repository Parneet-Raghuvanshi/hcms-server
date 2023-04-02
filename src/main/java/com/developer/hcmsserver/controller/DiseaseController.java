package com.developer.hcmsserver.controller;

import com.developer.hcmsserver.dto.DiseaseDto;
import com.developer.hcmsserver.dto.PatientDto;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.models.GeneralResponse;
import com.developer.hcmsserver.models.requests.DiseaseAddRequest;
import com.developer.hcmsserver.models.requests.DiseaseUpdateRequest;
import com.developer.hcmsserver.models.requests.GeneralAssignRemoveRequest;
import com.developer.hcmsserver.models.requests.GeneralPublicIdRequest;
import com.developer.hcmsserver.services.interfaces.DiseaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/disease")
public class DiseaseController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private DiseaseService diseaseService;

    @PostMapping("/add")
    public GeneralResponse add(@RequestBody DiseaseAddRequest diseaseAddRequest) {
        if (diseaseAddRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        DiseaseDto disease = mapper.map(diseaseAddRequest,DiseaseDto.class);
        diseaseService.add(disease);
        return new GeneralResponse(false,"SUCCESS","Disease Created Successfully!",null);
    }

    @PostMapping("/assign")
    public GeneralResponse assign(@RequestBody GeneralAssignRemoveRequest request) {
        if (request.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        diseaseService.assign(request.getEmail(),request.getPublicId());
        return new GeneralResponse(false,"SUCCESS","Disease Assigned Successfully!",null);
    }

    @PostMapping("/patients")
    public GeneralResponse getPatients(@RequestBody GeneralPublicIdRequest publicIdRequest) {
        if (publicIdRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        List<PatientDto> patients = diseaseService.getPatients(publicIdRequest.getPublicId());
        return new GeneralResponse(false,"SUCCESS","All patients fetched Successfully!",patients);
    }

    @PostMapping("/remove")
    public GeneralResponse remove(@RequestBody GeneralAssignRemoveRequest request) {
        if (request.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        diseaseService.remove(request.getEmail(),request.getPublicId());
        return new GeneralResponse(false,"SUCCESS","Disease Removed Successfully!",null);
    }

    @PostMapping("/update")
    public GeneralResponse update(@RequestBody DiseaseUpdateRequest diseaseUpdateRequest) {
        if (diseaseUpdateRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        DiseaseDto disease = mapper.map(diseaseUpdateRequest,DiseaseDto.class);
        diseaseService.update(disease);
        return new GeneralResponse(false,"SUCCESS","Disease Updated Successfully!",null);
    }
 }
