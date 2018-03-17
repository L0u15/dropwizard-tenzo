package fr.louis.dropwizard_tenzo.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "need")
@NamedQueries({ @NamedQuery(name = "Need.findAll", query = "SELECT n FROM Need n") })

public class Need {

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private Integer needId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", nullable = false)
	private Product needProduct;

	@Column(nullable = false)
	private double quantity;

	@ManyToOne
	@JoinColumn(name = "recipeId")
	@JsonIgnore
	private Recipe recipe;

	public Need() {
	}

	public Need(Product product, double quantity) {
		this.needProduct = product;
		this.quantity = quantity;
	}

	public Need(Product product, double quantity, Recipe recipe) {
		this.needProduct = product;
		this.quantity = quantity;
		this.recipe = recipe;
	}

	public long getNeedId() {

		return needId;
	}

	public void setNeedId(Integer needId) {
		this.needId = needId;
	}

	public Product getNeedProduct() {
		return needProduct;
	}

	public void setNeedProduct(Product needProduct) {
		this.needProduct = needProduct;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
