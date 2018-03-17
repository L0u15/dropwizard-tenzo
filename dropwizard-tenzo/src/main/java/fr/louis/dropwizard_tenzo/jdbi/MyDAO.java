package fr.louis.dropwizard_tenzo.jdbi;

import java.util.List;

public interface MyDAO<T> {

	public List<T> getAll();

	public T findById(Integer id);

	public void delete(T obj);

	public T saveOrUpdate(T obj);

}
