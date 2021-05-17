package com.selimsql.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.selimsql.springboot.dao.UserDAO;
import com.selimsql.springboot.model.User;

@Service
@Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.SUPPORTS, rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

  private UserDAO userDAO;

  @Autowired
  public UserServiceImpl(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User findOne(Long id) {
    User user = userDAO.findOne(id);
    return user;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<User> findAll() {
    List<User> list = userDAO.findAll();
    return list;
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  public User insert(User user) {
    User userInserted = userDAO.insert(user);
    return userInserted;
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  public User update(User user) {
    User userUpdated = userDAO.update(user);
    return userUpdated;
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
  public void delete(User user) {
    userDAO.delete(user);
  }
}
