package com.sharepool.server.rest.users;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping
	public Iterable<AppUser> getAllUsers() {
		return appUserRepository.findAll();
	}

	@PostMapping
	public AppUser createUser(@RequestBody @Valid AppUser appUser) {
		return appUserRepository.save(appUser);
	}
}
