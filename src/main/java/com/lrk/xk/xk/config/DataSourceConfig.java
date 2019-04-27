package com.lrk.xk.xk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author lrk
 * @date 2019/4/27下午4:54
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.jpa.properties.hibernate.current_session_context_class}")
    public String current_session_context_class;

    //使用druid用此配置
    /*@Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource(){
        return new DruidDataSource();
    }*/

    @Autowired
    private DataSource dataSource;


    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        //dao和entity的公共包
        sessionFactoryBean.setPackagesToScan("com.lrk.xk.xk.model");
        Properties properties = new Properties();
        properties
            .setProperty("hibernate.current_session_context_class", current_session_context_class);
        sessionFactoryBean.setHibernateProperties(properties);
        return sessionFactoryBean;
    }
}
