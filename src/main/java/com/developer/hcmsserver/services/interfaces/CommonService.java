package com.developer.hcmsserver.services.interfaces;

import com.developer.hcmsserver.dto.CommonDto;

public interface CommonService {
    void updateMain(CommonDto commonDto,String email);
    void updateMedical(CommonDto commonDto,String email);
}
