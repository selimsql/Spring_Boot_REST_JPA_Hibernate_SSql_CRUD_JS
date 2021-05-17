package com.selimsql.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.selimsql.springboot.dto.CustomResponseDTO;
import com.selimsql.springboot.exception.BusinessHttpStatusRuntimeException;
import com.selimsql.springboot.model.User;
import com.selimsql.springboot.service.UserService;
import com.selimsql.springboot.util.Util;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

  private UserService userService;

  @Autowired
  public UserRestController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping(path = "/userList",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<User>> userList() {
    System.out.println("userList");

    List<User> list = userService.findAll();
    ResponseEntity<List<User>> resp = new ResponseEntity<>(list, HttpStatus.OK);
    return resp;
  }


  @RequestMapping(path = "/userRow/{userId}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> userRow(@PathVariable Long userId) {
    System.out.println("userRow.id:" + userId);

    User user = userService.findOne(userId);
    ResponseEntity<User> resp = new ResponseEntity<>(user, HttpStatus.OK);
    return resp;
  }


  @RequestMapping(path = "/add",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomResponseDTO> addUser(@RequestBody User userNew) {
    String userCode = (userNew == null ? null : userNew.getCode());
    System.out.println("addUser.userCode:" + userCode);

    User userInserted = userService.insert(userNew);

    String message = "User registration is successful";
    ResponseEntity<CustomResponseDTO> responseEntity = newResponseEntityByCustomResponseDTO(userInserted.getId(), message, HttpStatus.CREATED);
    return responseEntity;
  }


  @RequestMapping(path = "/update/{userId}",
      method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userUpdate) {
    System.out.println("updateUser.id" + userId + ", row:" + userUpdate);

    User userUpdated = userService.update(userUpdate);
    ResponseEntity<User> responseEntity = new ResponseEntity<>(userUpdated, HttpStatus.OK);
    return responseEntity;
  }


  @RequestMapping(path = "/delete/{userId}",
      method = RequestMethod.DELETE,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomResponseDTO> deleteUserSave(@PathVariable Long userId) {
    System.out.println("deleteUser.id:" + userId);

    User userDb = userService.findOne(userId);
    if (userDb == null) {
      //throw new BusinessHttpStatusRuntimeException(HttpStatus.BAD_REQUEST, "RowId:" + userId + " invalid!");
      ResponseEntity<CustomResponseDTO> responseEntity = newResponseEntityByCustomResponseDTO(userId, "RowId:" + userId + " absent!", HttpStatus.NOT_FOUND);
      return responseEntity;
    }

    userService.delete(userDb);

    String message = "User deletion is successful";
    ResponseEntity<CustomResponseDTO> responseEntity = newResponseEntityByCustomResponseDTO(userId, message, HttpStatus.OK);
    return responseEntity;
  }


  private ResponseEntity<CustomResponseDTO> newResponseEntityByCustomResponseDTO(Long id, String message, HttpStatus httpStatus) {
    CustomResponseDTO customResponseDTO = new CustomResponseDTO(id, message);
    return new ResponseEntity<>(customResponseDTO, httpStatus);
  }


  @ExceptionHandler(BusinessHttpStatusRuntimeException.class)
  public ResponseEntity<String> handleBusinessHttpStatusRuntimeException(BusinessHttpStatusRuntimeException ex) {
    HttpStatus httpStatus = ex.getHttpStatus();
    String errmsg = ex.getErrorMessage();
    //logger.warn("httpStatus:" + httpStatus.value() + "; " + errmsg);
    return new ResponseEntity<>(errmsg, httpStatus);
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<String> handleAllException(Throwable ext) {
    String error = Util.getErrorMessageByCauses(ext);
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
