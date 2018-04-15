package com.rest.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.example.customexceptions.CustomDaoException;
import com.rest.example.entity.UserType;
import com.rest.example.repository.UserTypeRepository;

@Service
public class UserTypeService {

	@Autowired
	UserTypeRepository userTypeRepo;
	
	@Transactional(readOnly=true)
	public UserType getUserType(long id) throws CustomDaoException{
		return userTypeRepo.getOne(id);
	}
	
	
	@Transactional(readOnly=true)
	public UserType geTypeOfUsers(String typeofuser) throws CustomDaoException{
		return userTypeRepo.findByTypeOfUser(typeofuser).get(0);
	}
}
