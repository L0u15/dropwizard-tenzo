package fr.louis.dropwizard_tenzo.jdbi;

import java.util.List;

import org.hibernate.SessionFactory;

import fr.louis.dropwizard_tenzo.core.Product;
import fr.louis.dropwizard_tenzo.core.Recipe;
import io.dropwizard.hibernate.AbstractDAO;

public class RecipeDAO extends AbstractDAO<Recipe> implements MyDAO<Recipe> {

	public RecipeDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Recipe> getAll() {
		return super.list(namedQuery("Recipe.findAll"));
	}

	@Override
	public Recipe findById(Integer id) {
		return super.get(id);
	}

	@Override
	public void delete(Recipe recipe) {
		super.currentSession().delete(recipe);
		
	}

	@Override
	public Recipe saveOrUpdate(Recipe recipe) {
		return super.persist(recipe);
	}
	
	public Recipe findByName(String name) {
		return super.uniqueResult(namedQuery("Recipe.findByName").setParameter("name", name));
	}

	public boolean contains(String name) {
		return this.findByName(name) != null;
	}

}
