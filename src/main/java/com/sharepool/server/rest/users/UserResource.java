package com.sharepool.server.rest.users;

import javax.validation.Valid;

import com.sharepool.server.rest.users.dto.LoginUserDto;
import com.sharepool.server.rest.users.dto.RegisterUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sharepool.server.dal.AppUserRepository;
import com.sharepool.server.domain.AppUser;

@RestController
@RequestMapping("users")
public class UserResource {

	private final AppUserRepository appUserRepository;

	@Autowired
	public UserResource(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}

	@PutMapping
	public String createUser(@RequestBody @Valid RegisterUserDto registerUserDto) {
//		return appUserRepository.save(appUser);

		return "ajndf";
	}

	@PostMapping
	public String loginUser(@RequestBody @Valid LoginUserDto loginUserDto) {
//		return appUserRepository.save(appUser);

		return "aoisdo";
	}
}
