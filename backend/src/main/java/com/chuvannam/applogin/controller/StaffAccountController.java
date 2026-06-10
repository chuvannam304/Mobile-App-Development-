package com.chuvannam.applogin.controller;

import com.chuvannam.applogin.entity.StaffAccount;
import com.chuvannam.applogin.service.StaffAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/staff-accounts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StaffAccountController {

    private final StaffAccountService staffAccountService;

    @GetMapping
    public List<StaffAccount> getAll() {
        return staffAccountService.findAll();
    }

    @GetMapping("/{id}")
    public StaffAccount getById(@PathVariable UUID id) {
        return staffAccountService.findById(id);
    }

    @PostMapping
    public StaffAccount create(@RequestBody StaffAccount staffAccount) {
        return staffAccountService.save(staffAccount);
    }

    @PutMapping("/{id}")
    public StaffAccount update(@PathVariable UUID id, @RequestBody StaffAccount staffAccount) {
        return staffAccountService.update(id, staffAccount);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        staffAccountService.delete(id);
    }
}
