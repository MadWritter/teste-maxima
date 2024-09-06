package com.servidor.api.models.daos;

import com.servidor.api.models.config.Database;
import com.servidor.api.models.entities.Cliente;
import jakarta.persistence.EntityManager;

/**
 * DAO que encapsula as transações da entidade Cliente
 *
 * @author Jean Maciel
 * @see Cliente
 */
public class ClienteDAO {

    /**
     * Salva o cliente no banco
     * @param cliente entidade criada pelo serviço
     * @return o mesmo {@link Cliente} mas com o id retornado pelo banco.
     */
    public Cliente salvarCliente(Cliente cliente) {
        try(EntityManager em = Database.getClienteManager()) {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente; // aqui ele já tem o código retornado, pela integridade referencial
        }
    }
}
