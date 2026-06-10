package com.chuvannam.applogin.service;

import com.chuvannam.applogin.entity.StaffAccount;
import java.util.List;
import java.util.UUID;

public interface StaffAccountService {
    List<StaffAccount> findAll();
    StaffAccount findById(UUID id);
    StaffAccount save(StaffAccount staffAccount);
    StaffAccount update(UUID id, StaffAccount staffAccount);
    void delete(UUID id);
}
