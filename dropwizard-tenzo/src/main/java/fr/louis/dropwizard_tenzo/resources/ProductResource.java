package fr.louis.dropwizard_tenzo.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.louis.dropwizard_tenzo.core.Product;
import fr.louis.dropwizard_tenzo.jdbi.ProductDAO;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

	ProductDAO productDAO;

	public ProductResource(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@GET
	@UnitOfWork
	public List<Product> getAll() {
		return this.productDAO.getAll();
	}
	
	@GET
	@Path("/validated")
	@UnitOfWork
	public List<Product> getAllValidated(){
		return this.productDAO.getAllValidated();
	}
	
	@GET
	@Path("/tovalidate")
	@UnitOfWork
	public List<Product> getAllToValide(){
		return this.productDAO.getAllToValidate();
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Product get(@PathParam("id") Integer id) {
		return this.productDAO.findById(id);
	}
	
	

}
