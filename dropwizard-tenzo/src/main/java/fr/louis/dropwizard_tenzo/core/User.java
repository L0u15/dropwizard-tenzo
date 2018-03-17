package fr.louis.dropwizard_tenzo.core;

import java.security.Principal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
		@NamedQuery(name = "User.findByNameAndPassword", query = "SELECT u FROM User u WHERE u.name = :name AND u.password = :password") })
public class User implements Principal {

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private long userId;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(nullable = false)
	@JsonIgnore
	private String password;

	private String role;


	public User() {

	}

	public User(String login) {
		this.name = login;
		this.role = null;
	}

	public User(String name, String role) {

	}

	public User(String name, String password, String role) {
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public long getId() {
		return userId;
	}

	public void setId(long id) {
		this.userId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
