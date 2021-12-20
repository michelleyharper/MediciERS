package service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.UserDao;
import dao.UserJdbcDaoImpl;
import exception.ApplicationException;
import pojo.UserPojo;

public class UserServiceImpl implements UserService{
	
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	UserDao userDao;
	
	public UserServiceImpl() {
		userDao = new UserJdbcDaoImpl();
	}

	@Override
	public UserPojo register(UserPojo userPojo) throws ApplicationException {
		logger.info("Entered register() in service.");
		UserPojo returnUserPojo = this.userDao.register(userPojo);
		logger.info("Exited register() in service.");
		return returnUserPojo;
	}

	@Override
	public UserPojo validateUser(UserPojo userPojo) throws ApplicationException {
		logger.info("Entered validateUser() in service.");
		UserPojo returnUserPojo = this.userDao.validateUser(userPojo);
		logger.info("Exited validateUser() in service.");
		return returnUserPojo;
	}

	@Override
	public UserPojo updateUser(UserPojo userPojo) throws ApplicationException {
		logger.info("Entered updateUser() in service.");
		UserPojo returnUserPojo = this.userDao.updateUser(userPojo);
		logger.info("Exited updateUser() in service.");
		return returnUserPojo;
	}

	@Override
	public boolean deleteUser(int userId) throws ApplicationException {
		logger.info("Entered deleteUser() in service.");
		boolean returnFlag = this.userDao.deleteUser(userId);
		logger.info("Exited deleteItem() in service.");
		return returnFlag;
	}

	@Override
	public List<UserPojo> getAllUsers() throws ApplicationException {
		logger.info("Entered getAllUsers() in service.");
		List<UserPojo> allUsers = this.userDao.getAllUsers();
		logger.info("Exited getAllItems() in service.");
		return allUsers;
	}

	@Override
	public UserPojo getAUser(int userId) throws ApplicationException {
		logger.info("Entered getAUser() in service.");
		UserPojo returnUserPojo = this.userDao.getAUser(userId);
		logger.info("Exited getAUser() in service.");
		return returnUserPojo;
	}

	@Override
	public void exitApplication() {
		logger.info("Entered exitApplication() in service.");
		userDao.exitApplication();
		logger.info("Exited exitApplication() in service.");
		
	}

	

}
