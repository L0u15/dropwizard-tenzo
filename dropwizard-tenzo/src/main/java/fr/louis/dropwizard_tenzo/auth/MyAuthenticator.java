package fr.louis.dropwizard_tenzo.auth;

import java.util.Optional;

import fr.louis.dropwizard_tenzo.core.User;
import fr.louis.dropwizard_tenzo.jdbi.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

public class MyAuthenticator implements Authenticator<BasicCredentials, User> {

	private UserDAO userDAO;

	public MyAuthenticator(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	// public static final Set<String> ADMIN_ROLES = new
	// HashSet<String>(Arrays.asList("COOK", "ADMIN"));
	// public static final Set<String> COOK_ROLES = new
	// HashSet<String>(Arrays.asList("COOK"));
	// public static final Set<String> VISITOR_ROLES = new HashSet<String>();
	//
	// private static final Map<String, Set<String>> VALID_USERS =
	// ImmutableMap.of("toto", VISITOR_ROLES, "ducros",
	// COOK_ROLES, "merlin", ADMIN_ROLES);
    
	@UnitOfWork
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		User requestedUser = userDAO.findByNameAndPassword(credentials.getUsername(), credentials.getPassword());
		if (requestedUser != null) {
			return Optional.of(requestedUser);
		}
		return Optional.empty();
	}

}
