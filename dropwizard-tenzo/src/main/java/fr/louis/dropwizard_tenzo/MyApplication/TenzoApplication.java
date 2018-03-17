package fr.louis.dropwizard_tenzo.MyApplication;

import java.io.File;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.hibernate.Session;

import fr.louis.dropwizard_tenzo.MyApplicationConfiguration.DataInjector;
import fr.louis.dropwizard_tenzo.MyApplicationConfiguration.TenzoConfiguration;
import fr.louis.dropwizard_tenzo.auth.MyAuthenticator;
import fr.louis.dropwizard_tenzo.auth.MyAuthorizer;
import fr.louis.dropwizard_tenzo.core.Product;
import fr.louis.dropwizard_tenzo.core.Unit;
import fr.louis.dropwizard_tenzo.core.User;
import fr.louis.dropwizard_tenzo.jdbi.ProductDAO;
import fr.louis.dropwizard_tenzo.jdbi.RecipeDAO;
import fr.louis.dropwizard_tenzo.jdbi.UnitDAO;
import fr.louis.dropwizard_tenzo.jdbi.UserDAO;
import fr.louis.dropwizard_tenzo.resources.ProductResource;
import fr.louis.dropwizard_tenzo.resources.RecipeResource;
import fr.louis.dropwizard_tenzo.resources.UnitResource;
import fr.louis.dropwizard_tenzo.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class TenzoApplication extends Application<TenzoConfiguration> {

	public static void main(String[] args) throws Exception {
		new TenzoApplication().run("server",
				"src/main/java/fr/louis/dropwizard_tenzo/MyApplicationConfiguration/config.yml");
	}

	@Override
	public String getName() {
		return "Tenzo App";
	}

	@Override
	public void initialize(Bootstrap<TenzoConfiguration> bootstrap) {
		bootstrap.addBundle(hibernateBundle);
	}

	/**
	 * Hibernate bundle.
	 */
	private final HibernateBundle<TenzoConfiguration> hibernateBundle = new HibernateBundle<TenzoConfiguration>(
			/* Classes à declarer pour Hibernate */
			fr.louis.dropwizard_tenzo.core.Unit.class, fr.louis.dropwizard_tenzo.core.Product.class,
			fr.louis.dropwizard_tenzo.core.Recipe.class, fr.louis.dropwizard_tenzo.core.User.class,
			fr.louis.dropwizard_tenzo.core.Need.class) {
		public DataSourceFactory getDataSourceFactory(TenzoConfiguration configuration) {
			return configuration.getDataSourceFactory();

		}

	};

	@Override
	public void run(TenzoConfiguration configuration, Environment environment) throws Exception {

		final ProductDAO productDAO = new ProductDAO(hibernateBundle.getSessionFactory());
		final UnitDAO unitDAO = new UnitDAO(hibernateBundle.getSessionFactory());
		final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());
		final RecipeDAO recipeDAO = new RecipeDAO(hibernateBundle.getSessionFactory());

		environment.jersey().register(new UnitResource(unitDAO));
		environment.jersey().register(new ProductResource(productDAO));
		environment.jersey().register(new UserResource(userDAO));
		environment.jersey().register(new RecipeResource(recipeDAO));

		/* Enregistrement de l'Authenticator */
		MyAuthenticator myAuthenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle).create(MyAuthenticator.class,
				UserDAO.class, userDAO);

		environment.jersey().register(
				new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>().setAuthenticator(myAuthenticator)
						.setAuthorizer(new MyAuthorizer()).setRealm("BASIC-AUTH-REALM").buildAuthFilter()));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		environment.jersey().register(new AuthValueFactoryProvider.Binder<User>(User.class));

		/* Enable CORS headers */
		Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

		/* Configure CORS parameters */
		filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		filter.setInitParameter("allowedHeaders",
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
		filter.setInitParameter("allowCredentials", "true");

		/* Insert des informations de base en BDD */
		Class[] constructorParamTypes = { ProductDAO.class, UnitDAO.class, UserDAO.class, RecipeDAO.class };
		Object[] constructorArguments = { productDAO, unitDAO, userDAO, recipeDAO };
		DataInjector dataInjector = new UnitOfWorkAwareProxyFactory(hibernateBundle).create(DataInjector.class,
				constructorParamTypes, constructorArguments);

		int productIndex = 0;
		int unitIndex = 1;
		File csv = new File("C:\\Users\\L0u15\\workspace2\\dropwizard-tenzo\\src\\main\\resources\\data\\data.csv");

		dataInjector.importProductAndUnitInDb(csv, productIndex, unitIndex);
		dataInjector.importUserInDb();
		dataInjector.importRecipeInDb();
	}

}
