package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Role;
import com.chuvannam.applogin.repository.RoleRepository;
import com.chuvannam.applogin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public Role save(Role role) {
        if (role.getId() == null) role.setId(UUID.randomUUID());
        return roleRepository.save(role);
    }

    @Override
    public Role update(UUID id, Role role) {
        Role old = findById(id);
        old.setRoleName(role.getRoleName());
        old.setPrivileges(role.getPrivileges());
        return roleRepository.save(old);
    }

    @Override
    public void delete(UUID id) {
        roleRepository.deleteById(id);
    }
}
