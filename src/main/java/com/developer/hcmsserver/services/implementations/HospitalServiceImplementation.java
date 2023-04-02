package com.developer.hcmsserver.services.implementations;

import com.developer.hcmsserver.dto.DoctorDto;
import com.developer.hcmsserver.dto.HospitalDto;
import com.developer.hcmsserver.entity.DiseaseEntity;
import com.developer.hcmsserver.entity.DoctorEntity;
import com.developer.hcmsserver.entity.HospitalEntity;
import com.developer.hcmsserver.entity.PatientEntity;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.repository.DiseaseRepository;
import com.developer.hcmsserver.repository.DoctorRepository;
import com.developer.hcmsserver.repository.HospitalRepository;
import com.developer.hcmsserver.repository.PatientRepository;
import com.developer.hcmsserver.services.interfaces.HospitalService;
import com.developer.hcmsserver.utils.GeneralUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalServiceImplementation implements HospitalService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private GeneralUtils utils;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void add(HospitalDto hospitalDto) {
        HospitalEntity hospital = mapper.map(hospitalDto,HospitalEntity.class);
        String publicId = utils.generateUniqueId(200);
        hospital.setPublicId(publicId);
        hospital.getAddress().setPublicId(publicId);
        hospitalRepository.save(hospital);
    }

    @Override
    public void assign(String email, String publicId) {
        HospitalEntity hospital = hospitalRepository.findHospitalEntityByPublicId(publicId);
        DoctorEntity doctor = doctorRepository.findDoctorEntityByUser_Email(email);
        if (hospital == null || doctor == null) {
            throw new ServerException(UserServiceException.RECORD_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        doctor.setHospital(hospital);
        hospital.getDoctors().add(doctor);
        hospitalRepository.save(hospital);
        doctorRepository.save(doctor);
    }

    @Override
    public List<DoctorDto> getDoctors(String publicId) {
        HospitalEntity hospital = hospitalRepository.findHospitalEntityByPublicId(publicId);
        if (hospital == null) {
            throw new ServerException(UserServiceException.RECORD_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<DoctorEntity> doctors = hospital.getDoctors();
        List<DoctorDto> allDoctors = new ArrayList<>();
        for(DoctorEntity doctor : doctors) {
            allDoctors.add(mapper.map(doctor,DoctorDto.class));
        }
        return allDoctors;
    }

    @Override
    public void rate(String publicId,String rating) {
        HospitalEntity hospital = hospitalRepository.findHospitalEntityByPublicId(publicId);
        if (hospital == null) {
            throw new ServerException(UserServiceException.RECORD_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        hospital.setRating(rating);
        hospitalRepository.save(hospital);
    }

    @Override
    public void remove(String email, String publicId) {
        HospitalEntity hospital = hospitalRepository.findHospitalEntityByPublicId(publicId);
        DoctorEntity doctor = doctorRepository.findDoctorEntityByUser_Email(email);
        if (hospital == null || doctor == null) {
            throw new ServerException(UserServiceException.RECORD_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        doctor.setHospital(null);
        hospital.getDoctors().remove(doctor);
        hospitalRepository.save(hospital);
        doctorRepository.save(doctor);
    }
}
