package fr.louis.dropwizard_tenzo.auth;

import fr.louis.dropwizard_tenzo.core.User;
import io.dropwizard.auth.Authorizer;

public class MyAuthorizer implements Authorizer<User> {

	public boolean authorize(User user, String role) {
		return user.getRole() != null && user.getRole().equals(role);
	}

}
