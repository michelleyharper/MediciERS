package service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.ReimbursementDao;
import dao.ReimbursementJdbcDaoImpl;
import exception.ApplicationException;
import pojo.ReimbursementPojo;
import pojo.UserPojo;

public class ReimbursementServiceImpl implements ReimbursementService {

	public static final Logger logger = LogManager.getLogger(ReimbursementServiceImpl.class);

	ReimbursementDao reimbursementDao;

	public ReimbursementServiceImpl() {
		this.reimbursementDao = new ReimbursementJdbcDaoImpl();
	}

	@Override
	public ReimbursementPojo submitRequest(ReimbursementPojo reimbursementPojo) throws ApplicationException {
		logger.info("Entered submitRequest() in service.");
		ReimbursementPojo returnedReimbursementPojo = this.reimbursementDao.submitRequest(reimbursementPojo);
		logger.info("Exited submitRequest() in service.");
		return returnedReimbursementPojo;
	}

	@Override
	public List<ReimbursementPojo> getAllPendingRequests() throws ApplicationException {
		logger.info("Entered getAllPendingRequests() in service.");
		List<ReimbursementPojo> allPendingRequests = this.reimbursementDao.getAllPendingRequests();
		logger.info("Exited getAllPendingRequests() in service.");
		return allPendingRequests;
	}

	@Override
	public List<ReimbursementPojo> getAllResolvedRequests() throws ApplicationException {
		logger.info("Entered getAllResolvedRequests() in service.");
		List<ReimbursementPojo> allResolvedRequests = this.reimbursementDao.getAllResolvedRequests();
		logger.info("Exited getAllResolvedRequests() in service.");
		return allResolvedRequests;
	}

	@Override
	public boolean manageRequest(int rbId) throws ApplicationException {
		logger.info("Entered manageRequest() in service.");
		boolean returnFlag = this.reimbursementDao.manageRequest(rbId);
		logger.info("Exited manageRequest() in service.");
		return returnFlag;
	}

	@Override
	public List<ReimbursementPojo> getSpecificRequests(int userId) throws ApplicationException {
		logger.info("Entered getSpecificRequests() in service.");
		List<ReimbursementPojo> SpecificRequests = this.reimbursementDao.getSpecificRequests(userId);
		logger.info("Exited getPendingRequests() in service.");
		return SpecificRequests;

	}

	@Override
	public boolean deleteRequest(int rbId) throws ApplicationException {
		logger.info("Entered deleteRequest() in service.");
		boolean returnFlag = this.reimbursementDao.deleteRequest(rbId);
		logger.info("Exited deleteRequest() in service.");
		return returnFlag;
	}

	@Override
	public List<ReimbursementPojo> getPendingRequests(int userId) throws ApplicationException {
		logger.info("Entered getPendingRequests() in service.");
		List<ReimbursementPojo> PendingRequests = this.reimbursementDao.getPendingRequests(userId);
		logger.info("Exited getPendingRequests() in service.");
		return PendingRequests;
	}

	@Override
	public List<ReimbursementPojo> getResolvedRequests(int userId) throws ApplicationException {
		logger.info("Entered getResolvedRequests() in service.");
		List<ReimbursementPojo> ResolvedRequests = this.reimbursementDao.getResolvedRequests(userId);
		logger.info("Exited getResolvedRequests() in service.");
		return ResolvedRequests;
	}

	@Override
	public boolean denyRequest(int rbId) throws ApplicationException {
		logger.info("Entered denyRequest() in service.");
		boolean returnFlag = this.reimbursementDao.denyRequest(rbId);
		logger.info("Exited denyRequest() in service.");
		return returnFlag;
	}

	@Override
	public ReimbursementPojo updateReimbursement(ReimbursementPojo reimbursementPojo) throws ApplicationException {
		logger.info("Entered updateReimbursement() in service.");
		ReimbursementPojo returnReimbursementPojo = this.reimbursementDao.updateReimbursement(reimbursementPojo);
		logger.info("Exited updateReimbursement() in service.");
		return returnReimbursementPojo;
	}

	@Override
	public void exitApplication() {
		logger.info("Entered exitApplication() in service.");
		reimbursementDao.exitApplication();
		logger.info("Exited exitApplication() in service.");

	}

	@Override
	public ReimbursementPojo getAReimbursement(int rbId) throws ApplicationException {
		logger.info("Entered getAReimbursement() in service.");
		ReimbursementPojo returnReimbursementPojo = this.reimbursementDao.getAReimbursement(rbId);
		logger.info("Exited getAReimbursement() in service.");
		return returnReimbursementPojo;
	}
}
