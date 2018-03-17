package fr.louis.dropwizard_tenzo.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.louis.dropwizard_tenzo.core.Unit;
import fr.louis.dropwizard_tenzo.core.User;
import fr.louis.dropwizard_tenzo.jdbi.UnitDAO;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/unit")
@Produces(MediaType.APPLICATION_JSON)
public class UnitResource {

	UnitDAO unitDAO;

	public UnitResource(UnitDAO UnitDAO) {
		this.unitDAO = UnitDAO;
	}

	@GET
	@UnitOfWork
	@RolesAllowed({ "ADMIN", "TENZO" })
	public List<Unit> getAll(@Auth User user) {
		return this.unitDAO.getAll();
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	@RolesAllowed({ "ADMIN", "TENZO" })
	public Unit get(@PathParam("id") Integer id) {
		return this.unitDAO.findById(id);
	}

	@POST
	@Path("/add")
	@RolesAllowed({ "ADMIN" })
	@UnitOfWork
	public Unit add(Unit unit) {
		return this.unitDAO.saveOrUpdate(unit);
	}

}
