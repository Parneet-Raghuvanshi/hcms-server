package com.developer.hcmsserver.services.implementations;

import com.developer.hcmsserver.dto.CommonDto;
import com.developer.hcmsserver.entity.CommonEntity;
import com.developer.hcmsserver.entity.DoctorEntity;
import com.developer.hcmsserver.entity.PatientEntity;
import com.developer.hcmsserver.entity.UserEntity;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.repository.DoctorRepository;
import com.developer.hcmsserver.repository.PatientRepository;
import com.developer.hcmsserver.repository.UserRepository;
import com.developer.hcmsserver.services.interfaces.CommonService;
import com.developer.hcmsserver.utils.GeneralUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImplementation implements CommonService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void updateMain(CommonDto commonDto,String email) {
        UserEntity user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ServerException(UserServiceException.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user.getRole().equals("PAT")) {
            PatientEntity patient = patientRepository.findPatientEntityByUser_Email(email);
            CommonEntity oldCommon = patient.getCommon();
            if(oldCommon == null) {
                CommonEntity newCommon = mapper.map(commonDto,CommonEntity.class);
                newCommon.setPublicId(patient.getPublicId());
                patient.setCommon(newCommon);
                patientRepository.save(patient);
            } else {
                oldCommon.setAge(commonDto.getAge());
                oldCommon.setContact(commonDto.getContact());
                oldCommon.setHeight(commonDto.getHeight());
                oldCommon.setWeight(commonDto.getWeight());
                patient.setCommon(oldCommon);
                patientRepository.save(patient);
            }
        } else if (user.getRole().equals("DOC")) {
            DoctorEntity doctor = doctorRepository.findDoctorEntityByUser_Email(email);
            CommonEntity oldCommon = doctor.getCommon();
            if(oldCommon == null) {
                CommonEntity newCommon = mapper.map(commonDto,CommonEntity.class);
                newCommon.setPublicId(doctor.getPublicId());
                doctor.setCommon(newCommon);
                doctorRepository.save(doctor);
            } else {
                oldCommon.setAge(commonDto.getAge());
                oldCommon.setContact(commonDto.getContact());
                oldCommon.setHeight(commonDto.getHeight());
                oldCommon.setWeight(commonDto.getWeight());
                doctor.setCommon(oldCommon);
                doctorRepository.save(doctor);
            }
        }
    }

    @Override
    public void updateMedical(CommonDto commonDto,String email) {
        UserEntity user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ServerException(UserServiceException.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user.getRole().equals("PAT")) {
            PatientEntity patient = patientRepository.findPatientEntityByUser_Email(email);
            CommonEntity oldCommon = patient.getCommon();
            if(oldCommon == null) {
                CommonEntity newCommon = mapper.map(commonDto,CommonEntity.class);
                newCommon.setPublicId(patient.getPublicId());
                patient.setCommon(newCommon);
                patientRepository.save(patient);
            } else {
                oldCommon.setSpo2(commonDto.getSpo2());
                oldCommon.setBp(commonDto.getBp());
                oldCommon.setGlucose(commonDto.getGlucose());
                patient.setCommon(oldCommon);
                patientRepository.save(patient);
            }
        } else if (user.getRole().equals("DOC")) {
            DoctorEntity doctor = doctorRepository.findDoctorEntityByUser_Email(email);
            CommonEntity oldCommon = doctor.getCommon();
            if(oldCommon == null) {
                CommonEntity newCommon = mapper.map(commonDto,CommonEntity.class);
                newCommon.setPublicId(doctor.getPublicId());
                doctor.setCommon(newCommon);
                doctorRepository.save(doctor);
            } else {
                oldCommon.setSpo2(commonDto.getSpo2());
                oldCommon.setBp(commonDto.getBp());
                oldCommon.setGlucose(commonDto.getGlucose());
                doctor.setCommon(oldCommon);
                doctorRepository.save(doctor);
            }
        }
    }
}
