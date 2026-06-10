package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.StaffAccount;
import com.chuvannam.applogin.repository.StaffAccountRepository;
import com.chuvannam.applogin.service.StaffAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StaffAccountServiceImpl implements StaffAccountService {

    private final StaffAccountRepository staffAccountRepository;

    @Override
    public List<StaffAccount> findAll() {
        return staffAccountRepository.findAll();
    }

    @Override
    public StaffAccount findById(UUID id) {
        return staffAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff account not found"));
    }

    @Override
    public StaffAccount save(StaffAccount staffAccount) {
        if (staffAccount.getId() == null) staffAccount.setId(UUID.randomUUID());
        if (staffAccount.getActive() == null) staffAccount.setActive(true);
        if (staffAccount.getCreatedAt() == null) staffAccount.setCreatedAt(OffsetDateTime.now());
        staffAccount.setUpdatedAt(OffsetDateTime.now());
        return staffAccountRepository.save(staffAccount);
    }

    @Override
    public StaffAccount update(UUID id, StaffAccount staffAccount) {
        StaffAccount old = findById(id);
        old.setRole(staffAccount.getRole());
        old.setFirstName(staffAccount.getFirstName());
        old.setLastName(staffAccount.getLastName());
        old.setPhoneNumber(staffAccount.getPhoneNumber());
        old.setEmail(staffAccount.getEmail());
        old.setPasswordHash(staffAccount.getPasswordHash());
        old.setActive(staffAccount.getActive());
        old.setImage(staffAccount.getImage());
        old.setPlaceholder(staffAccount.getPlaceholder());
        old.setProvider(staffAccount.getProvider());
        old.setUpdatedAt(OffsetDateTime.now());
        return staffAccountRepository.save(old);
    }

    @Override
    public void delete(UUID id) {
        staffAccountRepository.deleteById(id);
    }
}
