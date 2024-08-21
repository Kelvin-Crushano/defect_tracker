package com.sgic.semita.services;

import com.sgic.semita.dtos.RoleDto;
import com.sgic.semita.entities.Role;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    Role createRole(RoleDto roleDto);
    List<RoleDto> getAllRoles(Pageable pageable);
    Role updateRole(Long id, RoleDto roleDto);
    boolean deleteRole(Long id);
    RoleDto getRoleById(Long id);

}





