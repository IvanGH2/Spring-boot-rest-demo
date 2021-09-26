package telemach.rest.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	@Bean
	@ConfigurationProperties("datasource.telemach")
	public DataSource telemachDS() {
		return DataSourceBuilder.create().build();
	}
}
