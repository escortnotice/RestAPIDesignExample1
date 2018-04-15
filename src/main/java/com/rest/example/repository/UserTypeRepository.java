package com.rest.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.example.entity.UserType;

import java.lang.String;
import java.util.List;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long>{

	List<UserType> findByTypeOfUser(String typeofuser);
}
