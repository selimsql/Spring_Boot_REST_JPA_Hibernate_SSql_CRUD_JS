package com.selimsql.springboot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name="Id", unique=true, nullable=false)
  private Long id;  //uses SEQ_USER_PK to get new Id!

  @Column(name="Code", unique=true, nullable=false, length=30)
  private String code;

  @Column(name="Name", nullable=false, length=30)
  private String name;

  @Column(name="Surname", nullable=false, length=30)
  private String surname;

  @Column(name="Password", nullable=false, length=100)
  private String password;

  @Column(name="Phone", length=20)
  private String phone;

  @Column(name="Status", nullable=false)
  private Integer status;


  public User() {
    this.id = null;
    this.code = null;
    //..
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }


  @Override
  public String toString() {
    return "User.id:" + id + ", code:" + code
        + ", name:" + name+ ", surname:" + surname
        + ", status:" + status;
    //..
  }
}