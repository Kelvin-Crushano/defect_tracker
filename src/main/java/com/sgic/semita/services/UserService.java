package com.sgic.semita.services;

import com.sgic.semita.dtos.UserDto;
import com.sgic.semita.entities.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
	User createUser(UserDto userDto);
	User getUserById(Long id);
	List<UserDto> getAllUsers(Pageable pageable);
	User updateUser(Long id, UserDto userDto);
	boolean deleteUser(Long id);
}
