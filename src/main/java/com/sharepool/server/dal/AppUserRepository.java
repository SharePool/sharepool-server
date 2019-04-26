package com.sharepool.server.dal;

import org.springframework.data.repository.CrudRepository;

import com.sharepool.server.domain.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
}
