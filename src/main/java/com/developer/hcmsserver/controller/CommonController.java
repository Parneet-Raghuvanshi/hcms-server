package com.developer.hcmsserver.controller;

import com.developer.hcmsserver.dto.CommonDto;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.models.GeneralResponse;
import com.developer.hcmsserver.models.requests.CommonUpdateMainRequest;
import com.developer.hcmsserver.models.requests.CommonUpdateMedicalRequest;
import com.developer.hcmsserver.services.interfaces.CommonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CommonService commonService;

    @PostMapping("/updateMain")
    public GeneralResponse updateMain(@RequestBody CommonUpdateMainRequest updateRequest) {
        if (updateRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        CommonDto commonDto = mapper.map(updateRequest, CommonDto.class);
        commonService.updateMain(commonDto, updateRequest.getEmail());
        return new GeneralResponse(false, "SUCCESS", "Common Main Details updated Successfully!", null);
    }

    @PostMapping("/updateMedical")
    public GeneralResponse updateMain(@RequestBody CommonUpdateMedicalRequest updateRequest) {
        if (updateRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        CommonDto commonDto = mapper.map(updateRequest, CommonDto.class);
        commonService.updateMedical(commonDto, updateRequest.getEmail());
        return new GeneralResponse(false, "SUCCESS", "Common Medical Details updated Successfully!", null);
    }
}
