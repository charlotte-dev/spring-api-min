package com.charlotte.spring.api.min.configuration;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * MyBatisの接続設定.
 * 参考:以下でDocker上にDBを起動</br>
 * docker run --name sample-db -p 5432:5432 -e POSTGRES_USER=local -e POSTGRES_PASSWORD=localPass -d postgres
 */
@Configuration
@MapperScan("com.charlotte.spring.api.min.mapper")
@PropertySource({"classpath:database.properties"})
public class MyBatisConfiguration {


    @Value("${database.driver}")
    String driver;
    @Value("${database.url}")
    String url;
    @Value("${database.user}")
    String user;
    @Value("${database.password}")
    String password;

    @Bean
    DataSource pooledDataSource(){
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setPoolMaximumActiveConnections(3);
        dataSource.setDefaultAutoCommit(false);
        dataSource.setPoolPingEnabled(true);
        dataSource.setPoolPingConnectionsNotUsedFor(10*1000);
        dataSource.setPoolPingQuery("select 1;");
        return dataSource;
    }

    @Bean("transactionManager")
    DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        dataSourceTransactionManager.setRollbackOnCommitFailure(true);
        return dataSourceTransactionManager;
    }

    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer(DataSource dataSource) {
        return (org.apache.ibatis.session.Configuration configuration) -> {
            JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();

            Environment environment = new Environment("default", jdbcTransactionFactory, dataSource);
            configuration.setEnvironment(environment);
            configuration.setDatabaseId("default");
        };
    }
}
