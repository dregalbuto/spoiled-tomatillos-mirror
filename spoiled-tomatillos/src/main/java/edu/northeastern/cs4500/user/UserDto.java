package edu.northeastern.cs4500.user;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDto {
	@NotNull
	@NotEmpty
	private String firstName;
	
	@NotNull
	@NotEmpty
	private String lastName;
	
	@NotNull
	@NotEmpty
	private String email;
	
	@NotNull
	@NotEmpty
	private String username;
	
	@NotNull
	@NotEmpty
	private String password;
	private String matchingPassword;

	
	
}
