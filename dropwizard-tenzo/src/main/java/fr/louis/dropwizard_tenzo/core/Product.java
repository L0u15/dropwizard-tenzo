package fr.louis.dropwizard_tenzo.core;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product")
@NamedQueries({ @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
		@NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.productName = :name"),
		@NamedQuery(name = "Product.findAllValidated", query = "SELECT p FROM Product p WHERE p.validated = true"),
		@NamedQuery(name = "Product.findAllToValidate", query = "SELECT p FROM Product p WHERE p.validated = false"), })
public class Product {

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private Integer productId;

	@Column(unique = true, nullable = false)
	private String productName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unitId")
	private Unit productUnit;

	private boolean validated=false;

	public Product() {
	}

	public Product(String name, Unit unit) {
		this.productName = name;
		this.productUnit = unit;
	}

	public Product(String name, Unit unit, boolean validated) {
		this.productName = name;
		this.productUnit = unit;
		this.validated = validated;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Unit getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(Unit productUnit) {
		this.productUnit = productUnit;
	}

	public boolean isValidate() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}
}
