package fr.louis.dropwizard_tenzo.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "unit")
@NamedQueries({ @NamedQuery(name = "Unit.findAll", query = "SELECT u FROM Unit u"),
		@NamedQuery(name = "Unit.findByName", query = "SELECT u FROM Unit u WHERE u.unitName = :unitName") })
public class Unit {

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private Integer unitId;

	@Column(unique = true)
	private String unitName;

	public Unit() {
	}

	public Unit(String name) {
		this.unitName = name;
	}

	public Integer getId() {
		return unitId;
	}

	public void setId(Integer id) {
		this.unitId = id;
	}

	public String getName() {
		return unitName;
	}

	public void setName(String name) {
		this.unitName = name;
	}

}
