package com.smarteshop.common.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.smarteshop.common.entity.BusinessObjectEntity;
import com.smarteshop.repository.BusinessObjectRepository;
import com.smarteshop.utils.GenericEntityUtils;

public abstract class BusinessObjectEntityServiceImpl<K extends Serializable & Comparable<K>, E extends BusinessObjectEntity<K, ?>>
extends BusinessObjectRepositorySupport implements BusinessObjectEntityService<K, E> {

  private Class<E> objectClass;
  private BusinessObjectRepository<E, K> repository;

  protected void preFindAll(){};


  public BusinessObjectEntityServiceImpl(BusinessObjectRepository<E, K> repository){
    this.repository=repository;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<E> findAll(Pageable pageable) {
    Page<E> result = repository.findAll(pageable);
    return result;
  }


  @SuppressWarnings("unchecked")
  public BusinessObjectEntityServiceImpl() {
    this.objectClass = (Class<E>) GenericEntityUtils.getGenericEntityClassFromComponentDefinition(getClass());
  }

  protected final Class<E> getObjectClass() {
    return objectClass;
  }


  @Override
  public E getEntity(Class<? extends E> clazz, K id) {
    return super.getEntity(getObjectClass(), id);
  }


  @Override
  public E getById(K id) {
    return super.getEntity(getObjectClass(), id);
  }


  @Override
  public <V> E getByField(SingularAttribute<? super E, V> attribute, V fieldValue) {
    return super.getByField(getObjectClass(), attribute, fieldValue);
  }


  @Override
  public void update(E entity) {
    super.update(entity);
  }


  @Override
  public E save(E entity) {
    super.save(entity);
    return entity;
  }


  @Override
  public void delete(E entity) {
    super.delete(entity);
  }


  @Override
  public E refresh(E entity) {
    return super.refresh(entity);
  }


  @Override
  public List<E> list() {
    return super.listEntity(getObjectClass());
  }


  @Override
  public <V> List<E> listByField(SingularAttribute<? super E, V> attribute, V fieldValue) {
    return super.listEntityByField(getObjectClass(), attribute, fieldValue);
  }


  @Override
  public <T extends E> List<T> list(Class<T> objectClass, Expression<Boolean> filter, Integer limit, Integer offset, Order... orders) {
    return super.listEntity(objectClass, filter, limit, offset, orders);
  }


  @Override
  public Long count() {
    return super.countEntity(getObjectClass());
  }


  @Override
  public <V> Long countByField(SingularAttribute<? super E, V> attribute, V fieldValue) {
    return super.countEntityByField(getObjectClass(), attribute, fieldValue);
  }


  @Override
  public Long count(Expression<Boolean> filter) {
    return super.countEntity(getObjectClass(), filter);
  }


  @Override
  public EntityManager getEntityManager() {
    return super.getEntityManager();
  }
}
