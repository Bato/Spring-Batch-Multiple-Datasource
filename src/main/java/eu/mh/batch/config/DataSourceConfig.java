package eu.mh.batch.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


/**
 * Definition of data sources used in this project
 *
 * @author <a href="mailto:mhasanbegovic@cad-schroer.de">Mugdin Hasanbegovic</a>
 * @version 1.0.0, 21.09.2016
 * @created 21.09.2016
 */
@Configuration
public class DataSourceConfig {

//	@Value("classpath:schema-mysql.sql")
//	private Resource schemaScript;
	  
	
	/**
	 * embedded database: url='jdbc:hsqldb:mem:testdb'
	 * Spring batch meta-data tables
	 * 
	 * @return
	 */
	@Bean(name = "hsqlEmbeddedDataSource")
    public DataSource hsqlEmbeddedDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.HSQL)
                // .addScript("classpath:org/springframework/batch/core/schema-drop-hsqldb.sql")
                .addScript("classpath:org/springframework/batch/core/schema-hsqldb.sql")
                // .addScript("schema-all-hsql.sql")
                .build();
    }
	
	
	/**
	 * embedded database: url='jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false'
	 * 
	 * @return
	 */
	@Bean(name = "h2EmbeddedDataSource")
    public DataSource h2EmbeddedDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
           		.addScript("db/sql/create-db-things-h2.sql")
        		.addScript("db/sql/insert-data-things.sql")
                .build();
    }

	/**
	 * embedded database: url='jdbc:derby:memory:testdb;create=true'
	 * 
	 * @return
	 */
	@Bean(name = "derbyEmbeddedDataSource")
    public DataSource derbyEmbeddedDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.DERBY)
           		.addScript("db/sql/create-db-things-derby.sql")
        		.addScript("db/sql/insert-data-things.sql")
                .build();
    }	

	@Bean(name = "mysqlDataSource")
//	@Primary
	public DataSource mysqlDataSource() throws NamingException {
	    return DataSourceBuilder.create()
		      .url("jdbc:mysql://localhost:3306/spring_batch?useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8_general_ci&useSSL=false")
		      .driverClassName("com.mysql.jdbc.Driver")
		      .username("javauser")
		      .password("javadude")	 	    		
		.build();
	}
	
	@Bean(name = "h2DataSource")
	@Primary
	// @ConfigurationProperties(prefix="spring.datasource.h2")
	public DataSource h2DataSource() {
	    return DataSourceBuilder.create()
                .url("jdbc:h2:mem:thing:H2;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("")	 	    		
	    		.build();
	}	
	
//	@Bean
//	// @Bean(name="batchDataSource")
//	@ConfigurationProperties(prefix="spring.datasource.secondary")
//	public DataSource h2DataSource() {
//	    return DataSourceBuilder.create().build();
//	}
	    

//	@Bean
//	// @Bean(name="batchDataSource")
//	public DataSource h2DataSource() {
//	    return DataSourceBuilder.create()
//                .url("jdbc:h2:mem:customer:H2")
//                .driverClassName("org.h2.Driver")
//                .username("sa")
//                .password("")	    		
//	          .build();
//	}	
	 
//		@Bean
//		public DataSource dataSource(){	   
//		   return DataSourceBuilder.create()
//					.url(env.getProperty("db.url"))
//					.driverClassName(env.getProperty("db.driver"))
//					.username(env.getProperty("db.username"))
//					.password(env.getProperty("db.password"))
//					.build();		   
//		}
		
//    @Bean
//    public DataSource datasource() {
//        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
//        ds.setDriverClassName(databaseDriverClassName);
//        ds.setUrl(datasourceUrl);
//        ds.setUsername(databaseUsername);
//        ds.setPassword(databasePassword);
// 
//        return ds;
//    }	
}
