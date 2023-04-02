package com.developer.hcmsserver.services.implementations;

import com.developer.hcmsserver.dto.DiseaseDto;
import com.developer.hcmsserver.dto.PatientDto;
import com.developer.hcmsserver.entity.DiseaseEntity;
import com.developer.hcmsserver.entity.PatientEntity;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.repository.DiseaseRepository;
import com.developer.hcmsserver.repository.DoctorRepository;
import com.developer.hcmsserver.repository.PatientRepository;
import com.developer.hcmsserver.repository.UserRepository;
import com.developer.hcmsserver.services.interfaces.DiseaseService;
import com.developer.hcmsserver.utils.GeneralUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiseaseServiceImplementation implements DiseaseService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private GeneralUtils utils;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DiseaseRepository diseaseRepository;

    @Override
    public void add(DiseaseDto diseaseDto) {
        DiseaseEntity disease = mapper.map(diseaseDto,DiseaseEntity.class);
        disease.setPublicId(utils.generateUniqueId(20));
        diseaseRepository.save(disease);
    }

    @Override
    public void assign(String email, String publicId) {
        DiseaseEntity disease = diseaseRepository.findDiseaseEntityByPublicId(publicId);
        PatientEntity patient = patientRepository.findPatientEntityByUser_Email(email);
        if (disease == null || patient == null) {
            throw new ServerException(UserServiceException.RECORD_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        patient.getDiseases().add(disease);
        disease.getPatients().add(patient);

        patientRepository.save(patient);
        diseaseRepository.save(disease);
    }

    @Override
    public List<PatientDto> getPatients(String publicId) {
        DiseaseEntity disease = diseaseRepository.findDiseaseEntityByPublicId(publicId);
        if (disease == null) {
            throw new ServerException(UserServiceException.RECORD_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<PatientEntity> patients = disease.getPatients();
        List<PatientDto> allPatients = new ArrayList<>();
        for(PatientEntity patient : patients) {
            allPatients.add(mapper.map(patient,PatientDto.class));
        }
        return allPatients;
    }

    @Override
    public void remove(String email, String publicId) {
        DiseaseEntity disease = diseaseRepository.findDiseaseEntityByPublicId(publicId);
        PatientEntity patient = patientRepository.findPatientEntityByUser_Email(email);
        if (disease == null || patient == null) {
            throw new ServerException(UserServiceException.RECORD_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        patient.getDiseases().remove(disease);
        disease.getPatients().remove(patient);
        patientRepository.save(patient);
        diseaseRepository.save(disease);
    }

    @Override
    public void update(DiseaseDto diseaseDto) {
        DiseaseEntity disease = diseaseRepository.findDiseaseEntityByPublicId(diseaseDto.getPublicId());
        if (disease == null) {
            throw new ServerException(UserServiceException.RECORD_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        disease.setName(diseaseDto.getName());
        disease.setImg(diseaseDto.getImg());
        disease.setDescription(diseaseDto.getDescription());
        disease.setSeverity(disease.getSeverity());
        diseaseRepository.save(disease);
    }
}
