package com.nurseit.orderproject.service.impl;

import com.nurseit.orderproject.entity.Role;
import com.nurseit.orderproject.repository.RoleRepository;
import com.nurseit.orderproject.service.RoleService;
import com.nurseit.orderproject.util.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException("Role not found: " + name));
    }
}
