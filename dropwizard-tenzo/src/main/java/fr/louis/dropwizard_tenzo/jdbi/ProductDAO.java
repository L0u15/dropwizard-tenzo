package fr.louis.dropwizard_tenzo.jdbi;

import java.util.List;

import org.hibernate.SessionFactory;

import fr.louis.dropwizard_tenzo.core.Product;
import io.dropwizard.hibernate.AbstractDAO;

public class ProductDAO extends AbstractDAO<Product> implements MyDAO<Product> {

	public ProductDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<Product> getAll() {
		return super.list(namedQuery("Product.findAll"));
	}

	public List<Product> getAllValidated() {
		return super.list(namedQuery("Product.findAllValidated"));
	}

	public List<Product> getAllToValidate() {
		return super.list(namedQuery("Product.findAllToValidate"));
	}

	public Product findById(Integer id) {
		return super.get(id);
	}

	public void delete(Product product) {
		super.currentSession().delete(product);
	}

	public Product saveOrUpdate(Product product) {
		return super.persist(product);
	}

	public Product findByName(String name) {
		return super.uniqueResult(namedQuery("Product.findByName").setParameter("name", name));
	}

	public boolean contains(String name) {
		return this.findByName(name) != null;
	}

}
