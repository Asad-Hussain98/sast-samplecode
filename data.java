import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    @Autowired
    SecretsManagerConfig secretsManagerConfig;
    @Autowired
    private ApplicationContext context;

    @Bean
    public DataSource getDataSource() {
        Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        JSONObject secrets =null;

        try {
            secrets = new JSONObject(secretsManagerConfig.getSecret());
        } catch (Exception e) {
            secrets = null;
        }


        try {
            dotenv = Dotenv.load();
        } catch (Exception e) {
            dotenv =null;
        }

        // Check to see if the .env file exists
        if(dotenv!=null){

            logger.info("Database Creds: from .env");
            
        } else 

        // Env file doesn't exist 
        if(host!=null && username!=null && port!=null && dbname!=null && password!=null){

            logger.info("Database Creds: from docker environment");

            System.out.println("Database Credentials: "+"jdbc:mariadb://"+host+":"+port+"/"+dbname);
            dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver");
            dataSourceBuilder.url("jdbc:mariadb://"+host+":"+port+"/"+dbname);
            dataSourceBuilder.username(username);
            dataSourceBuilder.password(password);
            
        } else
        if(!secrets.isEmpty()){
            dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver");
            dataSourceBuilder.password(secrets.getString("ALPHA_SCALE_DB_PASSWORD"));
            logger.info("Database Creds: from AWS Secrets Manager");
        } else {
            logger.error("Database Creds: not found please set database credentils in .env or AWS Secrets or in Docker Environemt if running in container ");
            System.exit(SpringApplication.exit(context));
        }
        return dataSourceBuilder.build();
    }
}
