package com.selimsql.springboot.service;

import java.util.List;

import com.selimsql.springboot.model.User;

public interface UserService {

	User findOne(Long keyId);

	List<User> findAll();

	User insert(User user);

	User update(User user);

	void delete(User user);
}
