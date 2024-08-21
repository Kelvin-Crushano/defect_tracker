package com.sgic.semita.controllers;

import com.sgic.semita.dtos.UserDto;
import com.sgic.semita.entities.User;
import com.sgic.semita.enums.RestApiResponseStatusCodes;
import com.sgic.semita.services.UserService;
import com.sgic.semita.utils.EndpointBundle;
import com.sgic.semita.utils.ResponseWrapper;
import com.sgic.semita.utils.ValidationMessages;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointBundle.USER)
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<ResponseWrapper<User>> createUser(@Valid @RequestBody UserDto userDto) {
		User createdUser = userService.createUser(userDto);
		if (createdUser != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
					RestApiResponseStatusCodes.CREATED.getCode(),
					ValidationMessages.SAVED_SUCCESSFULL,
					null
			));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
					RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
					ValidationMessages.SAVE_FAILED,
					null
			));
		}
	}

	@GetMapping(EndpointBundle.ID)
	public ResponseEntity<ResponseWrapper<User>> getUserById(@PathVariable("id") Long id) {
		User user = userService.getUserById(id);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
					RestApiResponseStatusCodes.SUCCESS.getCode(),
					ValidationMessages.RETRIEVED,
					user
			));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatusCodes.NOT_FOUND.getCode(),
					ValidationMessages.RETRIEVED_FAILED,
					null
			));
		}
	}

	@GetMapping
	public ResponseEntity<ResponseWrapper<List<UserDto>>> getAllUsers(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<UserDto> users = userService.getAllUsers(pageable);
		if (users != null && !users.isEmpty()) {
			return ResponseEntity.ok(new ResponseWrapper<>(
					RestApiResponseStatusCodes.SUCCESS.getCode(),
					ValidationMessages.RETRIEVED,
					users
			));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatusCodes.NOT_FOUND.getCode(),
					ValidationMessages.RETRIEVED_FAILED,
					null
			));
		}
	}

	@PutMapping(EndpointBundle.ID)
	public ResponseEntity<ResponseWrapper<User>> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
		User updatedUser = userService.updateUser(id, userDto);
		if (updatedUser != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
					RestApiResponseStatusCodes.SUCCESS.getCode(),
					ValidationMessages.SAVED_SUCCESSFULL,
					null
			));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseWrapper<>(
					RestApiResponseStatusCodes.BAD_REQUEST.getCode(),
					ValidationMessages.SAVE_FAILED,
					null
			));
		}
	}

	@DeleteMapping(EndpointBundle.ID)
	public ResponseEntity<ResponseWrapper<Void>> deleteUser(@PathVariable Long id) {
		boolean deleted = userService.deleteUser(id);
		if (deleted) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper<>(
					RestApiResponseStatusCodes.SUCCESS.getCode(),
					ValidationMessages.DELETE_SUCCESS,
					null
			));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper<>(
					RestApiResponseStatusCodes.NOT_FOUND.getCode(),
					ValidationMessages.DELETE_FAILED,
					null
			));
		}
	}
}

