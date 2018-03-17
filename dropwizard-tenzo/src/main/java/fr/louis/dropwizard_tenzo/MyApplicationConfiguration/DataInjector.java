package fr.louis.dropwizard_tenzo.MyApplicationConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import fr.louis.dropwizard_tenzo.core.Need;
import fr.louis.dropwizard_tenzo.core.Product;
import fr.louis.dropwizard_tenzo.core.Recipe;
import fr.louis.dropwizard_tenzo.core.Unit;
import fr.louis.dropwizard_tenzo.core.User;
import fr.louis.dropwizard_tenzo.jdbi.ProductDAO;
import fr.louis.dropwizard_tenzo.jdbi.RecipeDAO;
import fr.louis.dropwizard_tenzo.jdbi.UnitDAO;
import fr.louis.dropwizard_tenzo.jdbi.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;

public class DataInjector {

	private ProductDAO productDAO;
	private UnitDAO unitDAO;
	private UserDAO userDAO;
	private RecipeDAO recipeDAO;

	private String CSV_SPLIT_BY = ";";

	public DataInjector(ProductDAO productDAO, UnitDAO unitDAO, UserDAO userDAO, RecipeDAO recipeDAO) {
		this.productDAO = productDAO;
		this.unitDAO = unitDAO;
		this.userDAO = userDAO;
		this.recipeDAO = recipeDAO;
	}

	@UnitOfWork
	public void importProductAndUnitInDb(File file, int productColumn, int unitColumn) {

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			String line;

			while ((line = br.readLine()) != null) {
				String[] elts = line.split(CSV_SPLIT_BY);
				String productName = elts[productColumn];
				String unitName = elts[unitColumn];

				if (!unitDAO.contains(unitName)) {
					unitDAO.saveOrUpdate(new Unit(unitName));
				}
				Unit unit = unitDAO.findByName(unitName);

				if (!productDAO.contains(productName)) {
					productDAO.saveOrUpdate(new Product(productName, unit));
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@UnitOfWork
	public void importUserInDb() {
		ArrayList<String[]> userList = new ArrayList<>();
		userList.add(new String[] { "admin", "admin", "ADMIN" });
		userList.add(new String[] { "jean", "jean", "TENZO" });

		for (String[] infos : userList) {
			String adminName = infos[0];
			String adminPwd = infos[1];
			String adminRole = infos[2];
			User admin = new User(adminName, adminPwd, adminRole);
			userDAO.saveOrUpdate(admin);
		}
	}

	@UnitOfWork
	public void importRecipeInDb() {
		String[] recipeNames = { "Soupe à l'ail", "Purée de carotte" };

		Recipe recette1 = new Recipe(recipeNames[0]);
		Recipe recette2 = new Recipe(recipeNames[1]);

		recette1.getNeeds().add(new Need(productDAO.findByName("ail"), 1));
		recette1.getNeeds().add(new Need(productDAO.findByName("eau"), 4.5));

		recette2.getNeeds().add(new Need(productDAO.findByName("carotte"), 3.0));
		
		User admin = userDAO.findByName("admin");
		User jean = userDAO.findByName("jean");

		recette1.setUser(admin);
		recette2.setUser(jean);
		
	
		recipeDAO.saveOrUpdate(recette1);
		recipeDAO.saveOrUpdate(recette2);

	}

}
