package com.developer.hcmsserver.services.implementations;

import com.developer.hcmsserver.dto.AlertDto;
import com.developer.hcmsserver.entity.*;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.repository.AlertRepository;
import com.developer.hcmsserver.repository.DoctorRepository;
import com.developer.hcmsserver.repository.PatientRepository;
import com.developer.hcmsserver.repository.UserRepository;
import com.developer.hcmsserver.services.interfaces.AlertService;
import com.developer.hcmsserver.utils.GeneralUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AlertServiceImplementation implements AlertService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private GeneralUtils utils;

    @Override
    public void add(AlertDto alertDto,String email) {
        UserEntity user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ServerException(UserServiceException.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user.getRole().equals("PAT")) {
            PatientEntity patient = patientRepository.findPatientEntityByUser_Email(email);
            AlertEntity alert = mapper.map(alertDto,AlertEntity.class);
            alert.setPublicId(utils.generateUniqueId(20));
            patient.getAlerts().add(alert);
            patientRepository.save(patient);
        } else if (user.getRole().equals("DOC")) {
            DoctorEntity doctor = doctorRepository.findDoctorEntityByUser_Email(email);
            AlertEntity alert = mapper.map(alertDto,AlertEntity.class);
            alert.setPublicId(utils.generateUniqueId(20));
            doctor.getAlerts().add(alert);
            doctorRepository.save(doctor);
        }
    }

    @Override
    public void delete(String email,String publicId) {
        AlertEntity alert = alertRepository.findAlertEntityByPublicId(publicId);
        if (alert != null) {
            UserEntity user = userRepository.findUserByEmail(email);
            if (user.getRole().equals("PAT")) {
                PatientEntity patient = patientRepository.findPatientEntityByUser_Email(email);
                patient.getAlerts().remove(alert);
                patientRepository.save(patient);
            } else if (user.getRole().equals("DOC")) {
                DoctorEntity doctor = doctorRepository.findDoctorEntityByUser_Email(email);
                doctor.getAlerts().remove(alert);
                doctorRepository.save(doctor);
            }
            alertRepository.delete(alert);
        }
    }

    @Override
    public void updateAll(AlertDto alertDto, String publicId) {
        AlertEntity alert = alertRepository.findAlertEntityByPublicId(publicId);
        if (alert == null) {
            throw new ServerException(UserServiceException.RECORD_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        alert.setName(alertDto.getName());
        alert.setDescription(alertDto.getDescription());
        alert.setLevel(alertDto.getLevel());
        alertRepository.save(alert);
    }

    @Override
    public void updateLevel(String level, String publicId) {
        AlertEntity alert = alertRepository.findAlertEntityByPublicId(publicId);
        if (alert == null) {
            throw new ServerException(UserServiceException.RECORD_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        alert.setLevel(level);
        alertRepository.save(alert);
    }
}
