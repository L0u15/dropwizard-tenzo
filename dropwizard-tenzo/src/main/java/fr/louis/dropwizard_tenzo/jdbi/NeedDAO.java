package fr.louis.dropwizard_tenzo.jdbi;

import java.util.List;

import org.hibernate.SessionFactory;

import fr.louis.dropwizard_tenzo.core.Need;
import fr.louis.dropwizard_tenzo.core.Product;
import io.dropwizard.hibernate.AbstractDAO;

public class NeedDAO extends AbstractDAO<Need> implements MyDAO<Need> {

	public NeedDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<Need> getAll() {
		return super.list(namedQuery("Need.findAll"));

	}

	@Override
	public Need findById(Integer id) {
		return super.get(id);
	}

	public void delete(Need need) {
		super.currentSession().delete(need);

	}

	@Override
	public Need saveOrUpdate(Need need) {
		return super.persist(need);
	}

}
