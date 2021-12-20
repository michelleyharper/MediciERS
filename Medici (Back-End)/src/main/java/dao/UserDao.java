package dao;

import java.util.List;

import exception.ApplicationException;
import pojo.UserPojo;

public interface UserDao {
	
	UserPojo register(UserPojo userPojo) throws ApplicationException; // CREATE A NEW USER (ADD) ***
	UserPojo validateUser(UserPojo userPojo) throws ApplicationException; // VALIDATE LOGIN (EMPLOYEE OR MANAGER)
	List<UserPojo> getAllUsers() throws ApplicationException; // VIEW ALL USERS (EMPLOYEES) ***
	UserPojo getAUser(int userId) throws ApplicationException; // VIEW THE INFORMATION OF A USER
	UserPojo updateUser(UserPojo userPojo) throws ApplicationException; // UPDATE INFORMATION OF A USER ***
	boolean deleteUser(int userId) throws ApplicationException; // DELETE A USER ***
	void exitApplication();

}
