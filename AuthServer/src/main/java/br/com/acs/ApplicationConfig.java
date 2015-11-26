package br.com.acs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class ApplicationConfig extends AbstractMongoConfiguration {
	
	private static final String IP = "127.0.0.1";
	private static final String DATABASE_NAME = "user";
	
	@Override
	protected String getDatabaseName() {
		return DATABASE_NAME;
	}

	@Override
	public Mongo mongo() throws Exception {
		return  new MongoClient(IP);
	}
	
	@Bean
	MongoMappingContext springDataMongoMappingContext() {
	    return new MongoMappingContext();
	}
}