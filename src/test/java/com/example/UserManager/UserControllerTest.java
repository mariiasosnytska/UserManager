package com.example.UserManager;

import com.example.UserManager.controller.UserController;
import com.example.UserManager.exception.ExceptionUserService;
import com.example.UserManager.model.UserDTO;
import com.example.UserManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

	private UserController userController;
	private UserRepository userRepo;

	@BeforeEach
	public void setUp() {
		userRepo = mock(UserRepository.class);
		userController = new UserController(userRepo);
	}

	@Test
	public void testGetUserById() throws ExceptionUserService {
		String id = "1";
		UserDTO user = new UserDTO(id, "John", "Doe", "", "asfd@gmail.com", "spkf78", true);

		when(userRepo.GetUserById(id)).thenReturn(user);

		ResponseEntity<?> response = userController.getUserById(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(user, response.getBody());
	}

	@Test
	public void testGetUserById_UserNotFound() throws ExceptionUserService {
		String id = "1";

		when(userRepo.GetUserById(id)).thenThrow(new ExceptionUserService("User not found."));

		ResponseEntity<?> response = userController.getUserById(id);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("User not found.", response.getBody());
	}

	@Test
	public void testAddUser() {
		UserDTO user = new UserDTO("1", "John", "Doe", "", "asfd@gmail.com", "spkf78", true);

		when(userRepo.CreateUser(user)).thenReturn(user);

		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		ResponseEntity<?> response = userController.addUser(user, bindingResult);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(user, response.getBody());
	}

	@Test
	public void testAddUser_ValidationErrors() {
		UserDTO user = new UserDTO("1", "", "", "", "", "", true);

		BindingResult bindingResult = mock(BindingResult.class);
		List<FieldError> errors = new ArrayList<>();
		errors.add(new FieldError("user", "firstName", "{name.notempty}"));
		errors.add(new FieldError("user", "lastName", "{surname.notempty}"));
		errors.add(new FieldError("user", "email", "{email.notempty}"));
		when(bindingResult.hasErrors()).thenReturn(true);
		when(bindingResult.getFieldErrors()).thenReturn(errors);

		ResponseEntity<?> response = userController.addUser(user, bindingResult);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		List<String> errorMessages = (List<String>) response.getBody();
		assert errorMessages != null;
		assertEquals(3, errorMessages.size());
		assertTrue(errorMessages.contains("{name.notempty}"));
		assertTrue(errorMessages.contains("{surname.notempty}"));
		assertTrue(errorMessages.contains("{email.notempty}"));
	}
}
