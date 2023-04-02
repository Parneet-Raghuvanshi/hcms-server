package com.developer.hcmsserver.services.interfaces;

import com.developer.hcmsserver.dto.AlertDto;

public interface AlertService {
    void add(AlertDto alertDto,String email);
    void delete(String email,String publicId);
    void updateAll(AlertDto alertDto,String publicId);
    void updateLevel(String level,String publicId);
}
