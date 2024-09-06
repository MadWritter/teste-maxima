package com.servidor.api.models.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Essa classe fica responsável por migrar o banco de dados no
 * deploy.
 *
 * @author Jean Maciel
 */
@WebListener
public class MigrationConfigure implements ServletContextListener {

    /**
     * Executa no momento no deploy, gerenciado pelo wildfly
     * @param sce evento que será passado para o servidor
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        InitialContext context = null;
        DataSource datasource = null;

        try {
            // inicia um contexto e busca pelo recurso JNDI no servidor
            // especificamente buscando pelo datasource do H2
            context = new InitialContext();
            datasource = (DataSource) context.lookup("java:jboss/datasources/ExampleDS");

            // migrando
            Flyway flyway = Flyway.configure().dataSource(datasource).load();
            flyway.migrate();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }
}
