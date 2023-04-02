package com.developer.hcmsserver.controller;

import com.developer.hcmsserver.dto.AlertDto;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.models.GeneralResponse;
import com.developer.hcmsserver.models.requests.AlertAddRequest;
import com.developer.hcmsserver.models.requests.AlertDeleteRequest;
import com.developer.hcmsserver.models.requests.AlertUpdateAllRequest;
import com.developer.hcmsserver.models.requests.AlertUpdateLevelRequest;
import com.developer.hcmsserver.services.interfaces.AlertService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alert")
public class AlertController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AlertService alertService;

    @PostMapping("/add")
    public GeneralResponse add(@RequestBody AlertAddRequest alertAddRequest) {
        if (alertAddRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        AlertDto alertDto = mapper.map(alertAddRequest,AlertDto.class);
        alertService.add(alertDto,alertAddRequest.getEmail());
        return new GeneralResponse(false,"SUCCESS","Alert Details updated Successfully!",null);
    }

    @PostMapping("/delete")
    public GeneralResponse delete(@RequestBody AlertDeleteRequest alertDeleteRequest) {
        if (alertDeleteRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        alertService.delete(alertDeleteRequest.getEmail(),alertDeleteRequest.getPublicId());
        return new GeneralResponse(false,"SUCCESS","Alert Deleted Successfully!",null);
    }

    @PostMapping("/updateAll")
    public GeneralResponse updateAll(@RequestBody AlertUpdateAllRequest updateAllRequest) {
        if (updateAllRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        AlertDto alertDto = mapper.map(updateAllRequest, AlertDto.class);
        alertService.updateAll(alertDto,updateAllRequest.getPublicId());
        return new GeneralResponse(false,"SUCCESS","Alert Details updated Successfully!",null);
    }

    @PostMapping("/updateLevel")
    public GeneralResponse updateLevel(@RequestBody AlertUpdateLevelRequest updateLevelRequest) {
        if (updateLevelRequest.isEmpty())
            throw new ServerException(UserServiceException.REQUIRED_FIELD_EMPTY, HttpStatus.BAD_REQUEST);
        alertService.updateLevel(updateLevelRequest.getLevel(),updateLevelRequest.getPublicId());
        return new GeneralResponse(false,"SUCCESS","Alert Level updated Successfully!",null);
    }
}
