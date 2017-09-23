package com.tokyo.api.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.dbcfg")
public class DatabaseConfiguration extends HikariConfig {

    @Bean(name = "dsDBCfg")
    public DataSource dataSource() {
        this.setInitializationFailFast(true);
        return new HikariDataSource(this);
    }

    @Bean(name = "jdbcDBCfg")
    public NamedParameterJdbcTemplate jdbcParamTemplate(DataSource dsDBCfg) {
        return new NamedParameterJdbcTemplate(dsDBCfg);
    }

    @Bean(name = "jdbcDBCfg2")
    public JdbcTemplate jdbcTemplate(DataSource dsDBCfg) {
        JdbcTemplate template = new JdbcTemplate(dsDBCfg);
        Connection c = null;
        try {
            c = template.getDataSource().getConnection();
            c.setClientInfo("ApplicationName", "tokyo_api");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return template;
    }

}