package com.selimsql.springboot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.selimsql.springboot.model.User;
import com.selimsql.springboot.util.Util;

@Repository
public class UserDAOImpl implements UserDAO {

  private String sequenceNamePk;

  private EntityManager entityManager;

  public UserDAOImpl() {
    this.sequenceNamePk = "SEQ_USER_PK";
  }

  @PersistenceContext
  private void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  @Override
  public User findOne(Long id) {
    System.out.println("findOne.keyId:" + id);

    if (id==null)
      return null;

    User row = entityManager.find(User.class, id);
    return row;
  }


  @Override
  public List<User> findAll() {
    System.out.println("findAll");

    try {
      String sql = "Select t From User t Order by t.id";
      TypedQuery<User> query = entityManager.createQuery(sql, User.class);

      List<User> list = query.getResultList();

      return list;
    }
    catch (Throwable ext) {
      throw ext;
    }
  }

  
  @Override
  public User insert(User user) {
    System.out.println("insert row: " + user);

    Long idSeq = user.getId();
    if (idSeq==null) {
      idSeq = getNextIdFromSequence();
      user.setId(idSeq);
    }

    entityManager.persist(user);

    return user;
  }


  public Long getNextIdFromSequence() {
    return getNextIdFromSequenceSSql(sequenceNamePk);
  }


  private Long getNextIdFromSequenceSSql(String sequenceName) {
    String sql = "Select SequenceNextValue(SequenceName)"
          + "  From _DB_Sequence Where SequenceName = :pSeqName";
    return getNextIdFromSequence(sql, sequenceName);
  }

  private Long getNextIdFromSequence(String sql, String sequenceName) {
    Object objNextId;
    try {
      Query query = entityManager.createNativeQuery(sql);
      query.setParameter("pSeqName", sequenceName);
      objNextId = query.getSingleResult();
    }
    catch (NoResultException norex) {
      objNextId = null;
    }

    return Util.getLongObj(objNextId);
  }


  @Override
  public User update(User user) {
    System.out.println("update row: " + user);
    User userUpdated = entityManager.merge(user);
    return userUpdated;
  }

  @Override
  public void delete(User user) { 
    System.out.println("delete row: " + user);
    entityManager.remove(user);
  }
}
