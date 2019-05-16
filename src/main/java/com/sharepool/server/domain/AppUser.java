package com.sharepool.server.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class AppUser {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String userName;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	private String passwordHash;

	@ManyToMany
	private Set<AppUser> friends;

	public AppUser() {
	}

	public AppUser(
			@NotNull String userName,
			@NotNull String firstName,
			@NotNull String lastName,
			@NotNull String passwordHash,
			Set<AppUser> friends
	) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.passwordHash = passwordHash;
		this.friends = friends;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Set<AppUser> getFriends() {
		return friends;
	}

	public void setFriends(Set<AppUser> friends) {
		this.friends = friends;
	}
}
