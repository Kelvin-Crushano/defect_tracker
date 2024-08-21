package com.sgic.semita.services;


import com.sgic.semita.dtos.RoleDto;
import com.sgic.semita.entities.Role;
import com.sgic.semita.repositories.RoleRepository;
import com.sgic.semita.utils.ValidationMessages;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createRole(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        return roleRepository.save(role);
    }


    @Override
    public List<RoleDto> getAllRoles(Pageable pageable) {
        Page<Role> rolePage = roleRepository.findAll(pageable);

        if (rolePage.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NO_RECORDS_FOUND);
        }

        return rolePage.getContent().stream()
                .map(role -> new RoleDto(role.getId(),
                        role.getName())).collect(Collectors.toList());

    }



    @Override

    public Role updateRole(Long id, RoleDto roleDto) {
        Role role = roleRepository.findById(id).orElseThrow(()-> new RuntimeException(ValidationMessages.ROLE_NOT_FOUND + ": " + id));
        role.setName(roleDto.getName());
        return roleRepository.save(role);

    }

    @Override
    public boolean deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ValidationMessages.ROLE_NOT_FOUND + ": " + id));
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(role, roleDto);
        return roleDto;
    }



}





















