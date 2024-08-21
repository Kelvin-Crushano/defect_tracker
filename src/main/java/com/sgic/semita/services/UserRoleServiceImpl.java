package com.sgic.semita.services;

import com.sgic.semita.dtos.UserRoleDTO;
import com.sgic.semita.entities.Role;
import com.sgic.semita.entities.User;
import com.sgic.semita.entities.UserRole;
import com.sgic.semita.repositories.RoleRepository;
import com.sgic.semita.repositories.UserRepository;
import com.sgic.semita.repositories.UserRoleRepository;
import com.sgic.semita.utils.ValidationMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserRoleDTO createUserRole(UserRoleDTO userRoleDTO) {
        try {
            UserRole userRole = new UserRole();

            User user = userRepository.findById(userRoleDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Role role = roleRepository.findById(userRoleDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            userRole.setUser(user);
            userRole.setRole(role);

            Instant now = Instant.now();
            userRole.setCreatedAt(now);
            userRole.setUpdatedAt(now);

            return convertToDTO(userRoleRepository.save(userRole));

        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("User already has this role");
        }
    }


    @Override
    public UserRoleDTO updateUserRole(Long id, UserRoleDTO userRoleDTO) {
        try {
            // Retrieve the existing UserRole entity
            UserRole existingUserRole = userRoleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("UserRole not found"));

            // Retrieve User and Role based on IDs
            User user = userRepository.findById(userRoleDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Role role = roleRepository.findById(userRoleDTO.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            // Update the entity
            existingUserRole.setUser(user);
            existingUserRole.setRole(role);
            existingUserRole.setUpdatedAt(Instant.now());

            // Save the updated entity
            return convertToDTO(userRoleRepository.save(existingUserRole));
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("User already has this role", e);
        }
    }



    @Override
    public List<UserRoleDTO> getUserRolesWithPagination(Pageable pageable) {
        Page<UserRole> userPage = userRoleRepository.findAll(pageable);

        if (userPage.isEmpty()) {
            throw new ResourceNotFoundException(ValidationMessages.NO_RECORDS_FOUND);
        }

        return userPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }



    @Override
    public UserRoleDTO getUserRoleById(Long id) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserRole not found"));
        return convertToDTO(userRole);
    }




    private UserRoleDTO convertToDTO(UserRole userRole) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        BeanUtils.copyProperties(userRole, userRoleDTO);
        if (userRole.getUser() != null) {
            userRoleDTO.setUserId(userRole.getUser().getId());
        }
        if (userRole.getRole() != null) {
            userRoleDTO.setRoleId(userRole.getRole().getId());
        }
        return userRoleDTO;
    }

    @Override
    public boolean deleteUserRole(Long id) {
        if (userRoleRepository.existsById(id)) {
            userRoleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
