package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exception.ApplicationException;
import pojo.ReimbursementPojo;
import pojo.UserPojo;

public class ReimbursementJdbcDaoImpl implements ReimbursementDao {

	private static final Logger logger = LogManager.getLogger(ReimbursementJdbcDaoImpl.class);

	// EMPLOYEE - SUBMIT A REIMBURSEMENT
	@Override
	public ReimbursementPojo submitRequest(ReimbursementPojo reimbursementPojo) throws ApplicationException {
		logger.info("Entered submitRequest() in dao.");
		String status = "pending";
		reimbursementPojo.setRbStatus(status);
		reimbursementPojo.setRbResolved(false);
		reimbursementPojo.setRbRemoved(false);

		Connection conn = DBUtil.makeConnection();
		try {
			Statement stmt = conn.createStatement();
			String query = "insert into reimbursement_details(user_id, rb_date, rb_amount, rb_status, rb_resolved, rb_removed)"
					+ "values('" + reimbursementPojo.getUserId() + "','" + reimbursementPojo.getRbDate() + "',"
					+ reimbursementPojo.getRbAmount() + ",'" + reimbursementPojo.getRbStatus() + "',"
					+ reimbursementPojo.isRbResolved() + "," + reimbursementPojo.isRbRemoved() + ") returning rb_id";
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			reimbursementPojo.setRbId(rs.getInt(1));
		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}

		logger.info("Exited submitRequest() in dao.");
		return reimbursementPojo;
	}

	// MANAGER - GET ALL PENDING REQUESTS
	@Override
	public List<ReimbursementPojo> getAllPendingRequests() throws ApplicationException {
		logger.info("Entered getAllPendingRequests()");

		List<ReimbursementPojo> allPendingRequests = new ArrayList<ReimbursementPojo>();

		Connection conn = DBUtil.makeConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "select * from reimbursement_details where rb_status='pending' ";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				ReimbursementPojo reimbursementPojo = new ReimbursementPojo(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), rs.getBoolean(6), rs.getBoolean(7));

				allPendingRequests.add(reimbursementPojo);
			}
		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
		logger.info("Exited getAllPendingRequests");
		return allPendingRequests;
	}

	// MANAGER - GET ALL RESOLVED REQUESTS
	@Override
	public List<ReimbursementPojo> getAllResolvedRequests() throws ApplicationException {
		logger.info("Entered getAllResolvedRequests()");

		List<ReimbursementPojo> allResolvedRequests = new ArrayList<ReimbursementPojo>();

		Connection conn = DBUtil.makeConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "select * from reimbursement_details where rb_resolved=true";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				ReimbursementPojo reimbursementPojo = new ReimbursementPojo(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), rs.getBoolean(6), rs.getBoolean(7));

				allResolvedRequests.add(reimbursementPojo);
			}
		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
		logger.info("Exited getAllResolvedRequests");
		return allResolvedRequests;
	}

	@Override
	public boolean manageRequest(int rbId) throws ApplicationException {
		logger.info("entered manageRequest() in dao.");

		boolean flag = true;
		Connection conn = DBUtil.makeConnection();
		int rowsAffected = 0;
		try {
			Statement stmt = conn.createStatement();
			String query1 = "update reimbursement_details set rb_status='approved' where rb_id=" + rbId;
			String query2 = "update reimbursement_details set rb_resolved=true where rb_id=" + rbId;
			rowsAffected = stmt.executeUpdate(query1);
			rowsAffected = stmt.executeUpdate(query2);

		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
		if (rowsAffected == 0)
			flag = false;

		logger.info("Exited manageRequest() in dao.");
		return flag;
}

	@Override
	public List<ReimbursementPojo> getSpecificRequests(int userId) throws ApplicationException {
		logger.info("Entered getSpecificRequests()");

		List<ReimbursementPojo> SpecificRequests = new ArrayList<ReimbursementPojo>();

		Connection conn = DBUtil.makeConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "select * from reimbursement_details where user_id=" + userId;
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				ReimbursementPojo reimbursementPojo = new ReimbursementPojo(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), rs.getBoolean(6), rs.getBoolean(7));

				SpecificRequests.add(reimbursementPojo);
			}
		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
		logger.info("Exited getSpecificRequests");
		return SpecificRequests;
	}

	@Override
	public boolean deleteRequest(int rbId) throws ApplicationException {
		logger.info("Entered deleteRequest() in dao.");

		boolean flag = true;
		Connection conn = DBUtil.makeConnection();
		int rowsAffected = 0;
		try {
			Statement stmt = conn.createStatement();
			String query1 = "update reimbursement_details set rb_removed=true where rb_id=" + rbId;
			String query2 = "update reimbursement_details set rb_resolved=true where rb_id=" + rbId;
			String query3 = "update reimbursement_details set rb_status='deleted' where rb_id=" +rbId;
			rowsAffected = stmt.executeUpdate(query1);
			rowsAffected = stmt.executeUpdate(query2);
			rowsAffected = stmt.executeUpdate(query3);
//			System.out.println(rowsAffected);

		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
		if (rowsAffected == 0)
			flag = false;

		logger.info("Exited deleteRequest() in dao.");
		return flag;
	}

	@Override
	public List<ReimbursementPojo> getPendingRequests(int userId) throws ApplicationException {
		logger.info("Entered getPendingRequests()");

		List<ReimbursementPojo> PendingRequests = new ArrayList<ReimbursementPojo>();

		Connection conn = DBUtil.makeConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "select * from reimbursement_details where user_id=" + userId + "and rb_status='pending' ";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				ReimbursementPojo reimbursementPojo = new ReimbursementPojo(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), rs.getBoolean(6), rs.getBoolean(7));

				PendingRequests.add(reimbursementPojo);
			}
		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
		logger.info("Exited getPendingRequests");
		return PendingRequests;
	}

	@Override
	public List<ReimbursementPojo> getResolvedRequests(int userId) throws ApplicationException {
		logger.info("Entered getResolvedRequests() in dao.");

		List<ReimbursementPojo> ResolvedRequests = new ArrayList<ReimbursementPojo>();

		Connection conn = DBUtil.makeConnection();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "select * from reimbursement_details where user_id=" + userId + "and rb_resolved='true' ";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				ReimbursementPojo reimbursementPojo = new ReimbursementPojo(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), rs.getBoolean(6), rs.getBoolean(7));

				ResolvedRequests.add(reimbursementPojo);
			}
		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
		logger.info("Exited getResolvedRequests() in dao.");
		return ResolvedRequests;

	}

	@Override
	public boolean denyRequest(int rbId) throws ApplicationException {
			logger.info("entered denyRequest() in dao.");

			boolean flag = true;
			Connection conn = DBUtil.makeConnection();
			int rowsAffected = 0;
			try {
				Statement stmt = conn.createStatement();
				String query1 = "update reimbursement_details set rb_status='denied' where rb_id=" + rbId;
				String query2 = "update reimbursement_details set rb_resolved=true where rb_id=" + rbId;
				rowsAffected = stmt.executeUpdate(query1);
				rowsAffected = stmt.executeUpdate(query2);

			} catch (SQLException e) {
				throw new ApplicationException(e.getMessage());
			}
			if (rowsAffected == 0)
				flag = false;

			logger.info("Exited denyRequests() in dao.");
			return flag;
	}
	// UPDATE REIMBURSEMENT
	@Override
	public ReimbursementPojo updateReimbursement(ReimbursementPojo reimbursementPojo) throws ApplicationException {
		logger.info("Entered updateReimbursement() in dao.");
		
		Connection conn = DBUtil.makeConnection();
		try {
			Statement stmt = conn.createStatement();
			String query = "update reimbursement_details set rb_date='" + reimbursementPojo.getRbDate()
					 + "'," + "rb_amount=" + reimbursementPojo.getRbAmount() + " where rb_id=" + reimbursementPojo.getRbId();
			
			int rowsAffected = stmt.executeUpdate(query);
		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
		
		logger.info("Exited updateReimbursement() in dao.");
		return reimbursementPojo;
	}
	
	// GET A REIMBURSEMENT
	@Override
	public ReimbursementPojo getAReimbursement(int rbId) throws ApplicationException {
		logger.info("Entered getAReimbursement() in dao.");
		
		Connection conn = DBUtil.makeConnection();
		Statement stmt;
		ReimbursementPojo reimbursementPojo = null;
		try {
			stmt = conn.createStatement();
			String query = "select * from reimbursement_details where rb_id="+rbId
							+ "and rb_removed=false";
			ResultSet rs = stmt.executeQuery(query);
			
			if(rs.next()) {
				reimbursementPojo = new ReimbursementPojo(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), rs.getBoolean(6), rs.getBoolean(7));
			}
		} catch (SQLException e) {
			throw new ApplicationException(e.getMessage());
		}
		logger.info("Exited getAReimbursement() in dao.");
		return reimbursementPojo;
	}
	
	@Override
	public void exitApplication() {
		logger.info("Entered exitApplication() in dao.");
		DBUtil.closeConnection();
		logger.info("Exited exitApplication() in dao.");
	}

}
