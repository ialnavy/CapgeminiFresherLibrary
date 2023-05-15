package com.capgemini.library.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.library.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	public Optional<User> findByUsername(String username);
}
