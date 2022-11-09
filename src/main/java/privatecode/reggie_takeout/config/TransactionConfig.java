package privatecode.reggie_takeout.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TransactionConfig {

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource datasource) {

        return new DataSourceTransactionManager(datasource);
    }

}
