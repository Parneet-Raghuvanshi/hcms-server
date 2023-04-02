package com.developer.hcmsserver.services.implementations;

import com.developer.hcmsserver.dto.AddressDto;
import com.developer.hcmsserver.entity.*;
import com.developer.hcmsserver.exceptions.ServerException;
import com.developer.hcmsserver.exceptions.classes.UserServiceException;
import com.developer.hcmsserver.repository.AddressRepository;
import com.developer.hcmsserver.repository.DoctorRepository;
import com.developer.hcmsserver.repository.PatientRepository;
import com.developer.hcmsserver.repository.UserRepository;
import com.developer.hcmsserver.services.interfaces.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImplementation implements AddressService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void delete(String email) {
        UserEntity user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ServerException(UserServiceException.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user.getRole().equals("PAT")) {
            PatientEntity patient = patientRepository.findPatientEntityByUser_Email(email);
            AddressEntity address = patient.getAddress();
            if (address != null) {
                patient.setAddress(null);
                patientRepository.save(patient);
                addressRepository.delete(address);
            }
        } else if (user.getRole().equals("DOC")) {
            DoctorEntity doctor = doctorRepository.findDoctorEntityByUser_Email(email);
            AddressEntity address = doctor.getAddress();
            if (address != null) {
                doctor.setAddress(null);
                doctorRepository.save(doctor);
                addressRepository.delete(address);
            }
        }
    }

    @Override
    public void update(AddressDto addressDto, String email) {
        UserEntity user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ServerException(UserServiceException.USER_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user.getRole().equals("PAT")) {
            PatientEntity patient = patientRepository.findPatientEntityByUser_Email(email);
            AddressEntity address = mapper.map(addressDto,AddressEntity.class);
            address.setPublicId(patient.getPublicId());
            patient.setAddress(address);
            patientRepository.save(patient);
        } else if (user.getRole().equals("DOC")) {
            DoctorEntity doctor = doctorRepository.findDoctorEntityByUser_Email(email);
            AddressEntity address = mapper.map(addressDto,AddressEntity.class);
            address.setPublicId(doctor.getPublicId());
            doctor.setAddress(address);
            doctorRepository.save(doctor);
        }
    }
}
