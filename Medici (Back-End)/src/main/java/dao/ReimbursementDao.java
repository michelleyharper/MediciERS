package dao;

import java.util.List;

import exception.ApplicationException;
import pojo.ReimbursementPojo;

public interface ReimbursementDao {
	
	// MANAGER
	List<ReimbursementPojo> getAllPendingRequests() throws ApplicationException; // VIEW ALL PENDING REQUESTS ***
	List<ReimbursementPojo> getAllResolvedRequests() throws ApplicationException; // VIEW ALL RESOLVED REQUESTS ***
	List<ReimbursementPojo> getSpecificRequests(int userId) throws ApplicationException; // VIEW ALL OF AN EMPLOYEE'S REQUEST ***
	boolean manageRequest(int rbId) throws ApplicationException; // APPROVE A REQUEST ***
	boolean denyRequest(int rbId) throws ApplicationException; // DENY A REQUEST ***
	boolean deleteRequest(int rbId) throws ApplicationException; // DELETE A REQUEST ***
		
	// EMPLOYEE
	ReimbursementPojo submitRequest(ReimbursementPojo reimbursementPojo) throws ApplicationException; // ADD A REIMBURSEMENT REQUEST ***
	List<ReimbursementPojo> getPendingRequests(int userId) throws ApplicationException; // VIEW ALL PENDING REQUESTS (EMPLOYEE) ***
	List<ReimbursementPojo> getResolvedRequests(int userId) throws ApplicationException; // VIEW ALL RESOLVED REQUESTS (EMPLOYEE) ***
	ReimbursementPojo updateReimbursement(ReimbursementPojo reimbursementPojo) throws ApplicationException; // UPDATE REIMBURSEMENT ***
	ReimbursementPojo getAReimbursement(int rbId) throws ApplicationException;
	void exitApplication();


}
