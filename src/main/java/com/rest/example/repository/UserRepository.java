package com.rest.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.example.entity.User;
import com.rest.example.entity.UserType;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByUserType(UserType usertype);

}
