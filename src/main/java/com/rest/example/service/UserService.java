package com.rest.example.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rest.example.customexceptions.CustomDaoException;
import com.rest.example.entity.User;
import com.rest.example.entity.UserType;
import com.rest.example.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	UserTypeService userTypeService;

	/**
	 * The Custom Dao exception thrown should be a subclass of Unchecked
	 * exception, else the exception will not be propagated and handled by
	 * Controller Advice Exception handler class(ExceptionHandlerAdvice.java).
	 * 
	 * This happens because only unchecked exceptions cause rollbacks in spring
	 * transactions.
	 * 
	 * Or you can use @Transactional (rollbackFor= CustomDaoException.class) if
	 * customdaoexception class is checked exception class.
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = CustomDaoException.class)
	public User createUser(User user) throws CustomDaoException {
		// add user creation time
		user.setCreationDate(new Timestamp(System.currentTimeMillis()));
		//add isactive (default value = true)
		user.setActive(true);
		//add user type
		user.setUserType(userTypeService.geTypeOfUsers(user.getUserTypeValue()));
		return updateUser(user);
	}

	@Transactional(readOnly = true, rollbackFor = CustomDaoException.class)
	public User getUser(long userId) throws CustomDaoException {
		return userRepo.getOne(userId);
	}

	@Transactional(readOnly = true, rollbackFor = CustomDaoException.class)
	public List<User> getAllUsers() throws CustomDaoException {

		return userRepo.findAll();
	}

	@Transactional(readOnly = true, rollbackFor = CustomDaoException.class)
	public List<User> getAllUsersBasedOnUserType(UserType userType) throws CustomDaoException {

		return userRepo.findByUserType(userType);
	}
	
	@Transactional(isolation = Isolation.DEFAULT, rollbackFor = CustomDaoException.class)
	public String deleteUser(long userId) throws CustomDaoException {
		User user = getUser(userId);
		userRepo.delete(user);
		return "UserId " + user.getUserId() + " deleted successfully.";
	}

	@Transactional(isolation = Isolation.DEFAULT, rollbackFor = CustomDaoException.class)
	public User updateUserName(long userId, String userName) throws CustomDaoException {
		User user = getUser(userId);
		user.setUsername(userName);
		return updateUser(user);

	}

	@Transactional
	public User updateUser(User user) {
		return userRepo.save(user);
	}
	
	
}
