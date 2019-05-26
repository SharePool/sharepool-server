package com.sharepool.server.dal;

import org.springframework.data.repository.CrudRepository;

import com.sharepool.server.domain.AppUser;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

}
