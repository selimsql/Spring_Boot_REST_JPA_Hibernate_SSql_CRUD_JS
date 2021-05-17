package com.selimsql.springboot.dao;

import java.util.List;

import com.selimsql.springboot.model.User;

public interface UserDAO {

  User findOne(Long id);

  List<User> findAll();

  User insert(User user);

  User update(User user);

  void delete(User user);
}
