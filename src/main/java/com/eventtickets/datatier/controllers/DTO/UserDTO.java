package com.eventtickets.datatier.controllers.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private Long id;
	private String email;
	private String fullName;
	private String password;
	@JsonProperty("admin")
	private Boolean isAdmin = false;
}
