package fr.louis.dropwizard_tenzo.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.louis.dropwizard_tenzo.core.User;
import fr.louis.dropwizard_tenzo.jdbi.UserDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	UserDAO userDAO;

	public UserResource(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@GET
	@UnitOfWork
	@RolesAllowed({ "ADMIN", "TENZO" })
	public User getUser(@Auth User user) {
		return user;
	}

	@GET
	@UnitOfWork
	@Path("/all")
	@RolesAllowed({ "ADMIN" })
	public List<User> getAll() {
		return this.userDAO.getAll();
	}

	@POST
	@Path("/add")
	@UnitOfWork
	@RolesAllowed({ "ADMIN" })
	public User add(User user) {
		return this.userDAO.saveOrUpdate(user);
	}

}
