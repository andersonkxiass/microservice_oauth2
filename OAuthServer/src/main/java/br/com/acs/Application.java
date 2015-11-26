package br.com.acs;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@ComponentScan(basePackages={"br.com.acs"})
public class Application  extends SpringBootServletInitializer {

    private static final String CREATE_OAUTH_ACCESS_TOKEN_SQL = "create table if not exists oauth_access_token ("+
            "token_id VARCHAR(256),"+
            "token LONGVARBINARY,"+
            "authentication_id VARCHAR(256),"+
            "user_name VARCHAR(256),"+
            "client_id VARCHAR(256),"+
            "authentication LONGVARBINARY,"+
            "refresh_token VARCHAR(256)"+
            ");";

    private static final String DELETE_TOKENS_SQL = "delete from oauth_access_token";

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void setUpTokenDatasource() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(CREATE_OAUTH_ACCESS_TOKEN_SQL);
        jdbcTemplate.execute(DELETE_TOKENS_SQL);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}