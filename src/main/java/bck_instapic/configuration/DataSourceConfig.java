package bck_instapic.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "bck_instapic.mapper.primary", sqlSessionFactoryRef = "primarySqlSessionFactory")
public class DataSourceConfig {

    // Injecting properties from application.yml
    @Value("${spring.datasource.primary.jdbc-url}")
    private String jdbcUrl;

    @Value("${spring.datasource.primary.username}")
    private String username;

    @Value("${spring.datasource.primary.password}")
    private String password;

    @Value("${spring.datasource.primary.driver-class-name}")
    private String driverClassName;

    @Value("${spring.flyway.locations}")
    private String flywayPrimaryLocation;

    // HikariCP Configuration Bean
    @Bean(name = "primaryDataSource")
    @Primary
    public DataSource primaryDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        // Optional: Set additional HikariCP settings here
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }

    // SqlSessionFactory Bean for MyBatis
    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        // Set MyBatis mapper locations
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/primary/*.xml"));
        // Set type aliases
        //factoryBean.setTypeAliasesPackage("bck_instapic.model");
        // Set Type Handlers
        factoryBean.setTypeHandlersPackage("bck_instapic.configuration.typehandler");
        return factoryBean.getObject();
    }

    @Bean(name = "primaryFlyway")
    @Primary
    public Flyway primaryFlyway(@Qualifier("primaryDataSource") DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(flywayPrimaryLocation)
                .load();
        flyway.migrate();
        return flyway;
    }
}