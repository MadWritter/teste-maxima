package com.servidor.api.models.config;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

/**
 * Essa classe fica responsável por configurar
 * deploy.
 *
 * @author Jean Maciel
 */
@WebListener
public class DeployConfigure implements ServletContextListener {

    /**
     * Datasouce JNDI que será buscado no wildfly,
     * especificamente o banco h2 default será usado
     */
    @Resource(lookup = "java:jboss/datasources/ExampleDS")
    private DataSource dataSource;

    /**
     * Executa no momento no deploy, gerenciado pelo wildfly,
     * o objetivo é migrar o database para o server.
     * @param sce evento que será passado para o servidor
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // se o Resource não encontrar o recurso via JNDI:
        if (dataSource == null) {
            throw new IllegalStateException("O datasource fornecido via JNDI está indisponível");
        }

        // caso contrário, segue para a migração com o datasource:
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
    }
}
