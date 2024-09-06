package com.servidor.api.models.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Interface para tratar das conexões com o banco
 *
 * @author Jean Maciel
 */
public interface Database {

    /**
     * Cria um EntityManager para persistência do Cliente
     * @return um EntityManager para tratar das conexões do cliente
     */
    static EntityManager getClienteManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cliente");
        return emf.createEntityManager();
    }
}
