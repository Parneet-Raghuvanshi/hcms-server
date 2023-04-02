package com.developer.hcmsserver.services.interfaces;

import com.developer.hcmsserver.dto.DiseaseDto;
import com.developer.hcmsserver.dto.PatientDto;
import com.developer.hcmsserver.entity.PatientEntity;

import java.util.List;

public interface DiseaseService {
    void add(DiseaseDto diseaseDto);
    void assign(String email,String publicId);
    List<PatientDto> getPatients(String publicId);
    void remove(String email,String publicId);
    void update(DiseaseDto diseaseDto);
}
