package com.example.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * This class is responsible to configure Spring Data JPA, will create data
 * source, and entity manager for Spring Data JPA managed entities.
 * 
 * @author Rajeev Pandey
 * 
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource(value = "file:${env.property.file}", ignoreResourceNotFound = true) })
@EnableJpaRepositories({ "com.example" })
@EnableTransactionManagement
public class JpaConfig {

    private static String[] packagesToScan = { "com.example" };

    @Bean
    public DataSource dataSource() throws Exception {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource
                .setJdbcUrl("jdbc:mysql://beta-db.proptiger-ws.com:3306/crm?zeroDateTimeBehavior=convertToNull");
        comboPooledDataSource.setDriverClass("com.mysql.jdbc.Driver");
        comboPooledDataSource.setUser("root");
        comboPooledDataSource.setPassword("betaroot1234");
        return comboPooledDataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws Exception {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(createJPAAdapter());
        factory.setDataSource(dataSource());
        factory.setPersistenceUnitName("coreUnit");
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPackagesToScan(packagesToScan);
        factory.setJpaProperties(createJPAProperties());

        factory.afterPropertiesSet();
        return factory.getObject();
    }


    private HibernateJpaVendorAdapter createJPAAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        return vendorAdapter;
    }

    private Properties createJPAProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws Exception {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

}
