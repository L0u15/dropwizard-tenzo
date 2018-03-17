package fr.louis.dropwizard_tenzo.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.louis.dropwizard_tenzo.core.Product;
import fr.louis.dropwizard_tenzo.core.Recipe;
import fr.louis.dropwizard_tenzo.jdbi.RecipeDAO;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/recipe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeResource {

	RecipeDAO recipeDAO;

	public RecipeResource(RecipeDAO productDAO) {
		this.recipeDAO = productDAO;
	}

	@GET
	@UnitOfWork
	public List<Recipe> getAll() {
		return this.recipeDAO.getAll();
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Recipe get(@PathParam("id") Integer id) {
		return this.recipeDAO.findById(id);
	}
	
	

}
