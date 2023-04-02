package com.developer.hcmsserver.services.interfaces;

import com.developer.hcmsserver.dto.DoctorDto;
import com.developer.hcmsserver.dto.HospitalDto;

import java.util.List;

public interface HospitalService {
    void add(HospitalDto hospitalDto);
    void assign(String email,String publicId);
    List<DoctorDto> getDoctors(String publicId);
    void rate(String publicId,String rating);
    void remove(String email,String publicId);
}
