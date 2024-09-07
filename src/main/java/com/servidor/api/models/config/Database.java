package com.servidor.api.models.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Interface responsável por encapsular as transações do Database
 *
 * @author Jean Maciel
 */
public interface Database {
    static EntityManager getClienteManager() {
        /*
            Sim, sei que há um resource leak aqui no objeto emf.
            E em cenários de produção, com certeza seria resolvido no ciclo
            de vida gerenciado pela classe DeployConfigure, fechando junto ao fechar a api.

            Mas vamos nos ater a simplicidade e legibilidade.
         */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cliente");
        return emf.createEntityManager();
    }
}
