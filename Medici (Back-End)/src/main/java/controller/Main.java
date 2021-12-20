package controller;

import io.javalin.Javalin;
import pojo.ReimbursementPojo;
import pojo.UserPojo;
import service.ReimbursementService;
import service.ReimbursementServiceImpl;
import service.UserService;
import service.UserServiceImpl;

public class Main {

	public static void main(String[] args) {

		ReimbursementService reimbursementService = new ReimbursementServiceImpl();
		UserService userService = new UserServiceImpl();
		Javalin server = Javalin.create((config) -> config.enableCorsForAllOrigins()).start(4040);
		
	
		// ______________________________  REIMBURSEMENT ENDPOINTS _______________________________
		
		// GET A REIMBURSEMENT
		// http://localhost:4040/api/reimbursements/11
		server.get("api/reimbursements/{rbid}", (ctx) -> {
			ctx.json(reimbursementService.getAReimbursement(Integer.parseInt(ctx.pathParam("rbid"))));
			
		});
			
		// GET ALL PENDING REIMBURSEMENTS (MANAGER)
		// http://localhost:4040/api/reimbursements/manager/pending
		server.get("api/reimbursements/manager/pending", (ctx) -> {
			ctx.json(reimbursementService.getAllPendingRequests());

		});

		// GET ALL RESOLVED REIMBURSEMENTS (MANAGER)
		// http://localhost:4040/api/reimbursements/manager/resolved
		server.get("api/reimbursements/manager/resolved", (ctx) -> {
			ctx.json(reimbursementService.getAllResolvedRequests());
		});

		// GET A SPECIFIC EMPLOYEE'S REIMBURSEMENTS (MANAGER)
		// http://localhost:4040/api/reimbursements/all/employee/1
		server.get("api/reimbursements/all/employee/{userid}", (ctx) -> {
			ctx.json(reimbursementService.getSpecificRequests(Integer.parseInt(ctx.pathParam("userid"))));
		});

		// GET ALL OF AN EMPLOYEE'S PENDING REIMBURSEMENTS (EMPLOYEE)
		// http://localhost:4040/api/reimbursements/pending/employee/2
		server.get("api/reimbursements/pending/employee/{userid}", (ctx) -> {
			ctx.json(reimbursementService.getPendingRequests(Integer.parseInt(ctx.pathParam("userid"))));

		});

		// GET ALL OF AN EMPLOYEE'S RESOLVED REIMBURSEMENTS (EMPLOYEE)
		// http://localhost:4040/api/reimbursements/resolved/employee/2
		server.get("api/reimbursements/resolved/employee/{userid}", (ctx) -> {
			ctx.json(reimbursementService.getResolvedRequests(Integer.parseInt(ctx.pathParam("userid"))));
		});

		// (SOFT) DELETE A REIMBURSEMENT
		// http://localhost:4040/api/reimbursements/remove/10
		server.delete("api/reimbursements/remove/{rbid}", (ctx) -> {
			reimbursementService.deleteRequest(Integer.parseInt(ctx.pathParam("rbid")));

		});

		// ADD A REIMBURSEMENT
		// http://localhost:4040/api/reimbursements/add
		server.post("api/reimbursements/add", (ctx) -> {
			ReimbursementPojo returnReimbursementPojo = reimbursementService
					.submitRequest(ctx.bodyAsClass(ReimbursementPojo.class));
			ctx.json(returnReimbursementPojo);

		});

		// UPDATE A REIMBURSEMENT
		// http://localhost:4040/api/reimbursements/update/1
		server.put("api/reimbursements/update/{rbid}", (ctx) -> {
			ReimbursementPojo returnReimbursementPojo = reimbursementService
					.updateReimbursement(ctx.bodyAsClass(ReimbursementPojo.class));
			ctx.json(returnReimbursementPojo);
		});

		// APPROVE A REIMBURSEMENT
		// http://localhost:4040/api/reimbursements/approve/1
		server.delete("api/reimbursements/approve/{rbid}", (ctx) -> {
			reimbursementService.manageRequest(Integer.parseInt(ctx.pathParam("rbid")));
		});

		// DENY A REIMBURSEMENT
		// http://localhost:4040/api/reimbursements/deny/1
		server.delete("api/reimbursements/deny/{rbid}", (ctx) -> {
			reimbursementService.denyRequest(Integer.parseInt(ctx.pathParam("rbid")));

		});
		
		// ________________________________ USER ENDPOINTS _____________________________________

		// ADD A USER
		// http://localhost:4040/api/users/register
		server.post("api/users/register", (ctx) -> {
			UserPojo returnUserPojo = userService.register(ctx.bodyAsClass(UserPojo.class));
			ctx.json(returnUserPojo);

		});

		// VALIDATE A USER
		// http://localhost:4040/api/users/login
		server.post("api/users/login", (ctx) -> {
			ctx.json(userService.validateUser(ctx.bodyAsClass(UserPojo.class)));

		});

		// UPDATE A USER
		// http://localhost:4040/api/users/update/1
		server.put("api/users/update/{userid}", (ctx) -> {
			UserPojo returnUserPojo = userService.updateUser(ctx.bodyAsClass(UserPojo.class));
			ctx.json(returnUserPojo);

		});

		// GET ALL USERS (EMPLOYEES ONLY)
		// http://localhost:4040/api/users/employees
		server.get("api/users/employees", (ctx) -> {
			ctx.json(userService.getAllUsers());

		});

		// (SOFT) DELETE A USER
		// http://localhost:4040/api/users/remove/5
		server.delete("api/users/remove/{userid}", (ctx) -> {
			userService.deleteUser(Integer.parseInt(ctx.pathParam("userid")));

		});

		// GET A USER (EMPLOYEES ONLY)
		// http://localhost:4040/api/users/employee/1
		server.get("api/users/employee/{userid}", (ctx) -> {
			ctx.json(userService.getAUser(Integer.parseInt(ctx.pathParam("userid"))));
			
		});

	}

}
