package com.smarteshop.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BusinessObjectRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

}
