package com.sgic.semita.services;

import com.sgic.semita.dtos.UserRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRoleService {
    UserRoleDTO createUserRole(UserRoleDTO userRoleDTO);
    UserRoleDTO updateUserRole(Long id, UserRoleDTO userRoleDTO);
    boolean deleteUserRole(Long id);
    UserRoleDTO getUserRoleById(Long id);
    List<UserRoleDTO> getUserRolesWithPagination(Pageable pageable);
}
