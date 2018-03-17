package fr.louis.dropwizard_tenzo.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "recipe")
@NamedQueries({ @NamedQuery(name = "Recipe.findAll", query = "SELECT r FROM Recipe r"),
		@NamedQuery(name = "Recipe.findByName", query = "SELECT r FROM Recipe r WHERE r.name = :name") })
public class Recipe {

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private long recipeId;

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "recipeId")
	private Set<Need> needs = new HashSet<>();

	public Recipe() {
	}

	public Recipe(String name) {
		this.name = name;
	}

	public long getId() {
		return recipeId;
	}

	public void setId(long id) {
		this.recipeId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Need> getNeeds() {
		return needs;
	}

	public void setNeeds(Set<Need> needs) {
		this.needs = needs;
	}

	public long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
