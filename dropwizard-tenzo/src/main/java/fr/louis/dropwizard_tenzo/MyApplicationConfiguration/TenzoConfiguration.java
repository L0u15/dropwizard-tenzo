package fr.louis.dropwizard_tenzo.MyApplicationConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class TenzoConfiguration extends Configuration {
	
	
	@NotEmpty
	private String template;

	@Valid
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory database = new DataSourceFactory();

	@NotEmpty
	private String defaultName = "Stranger";

	@JsonProperty
	public String getTemplate() {
		return template;
	}

	@JsonProperty
	public void setTemplate(String template) {
		this.template = template;
	}

	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String name) {
		this.defaultName = name;
	}


	public DataSourceFactory getDataSourceFactory() {
		return database;
	}
	
    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
}
}
