package com.servidor.api.models.daos;

import com.servidor.api.models.config.Database;
import com.servidor.api.models.entities.Cliente;
import jakarta.persistence.*;

import java.util.List;

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
     * @throws PersistenceException caso não consiga realizar a o INSERT
     */
    public Cliente salvarCliente(Cliente cliente) {
        try(EntityManager em = Database.getClienteManager()) {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente; // aqui ele já tem o código retornado, pela integridade referencial
        }
    }

    /**
     * Devolve um {@link Cliente} a partir de um <code>código</code>
     * @param codigo que idenfica o cliente
     * @return um Cliente a partir do <code>código</code> informado
     * @throws EntityNotFoundException caso não tenha um Cliente com o <code>código</code>
     * correspondente
     */
    public Cliente obterClientePorCodigo(Long codigo) {
        try(EntityManager em = Database.getClienteManager()){

            String jpql = "SELECT c FROM Cliente c WHERE c.codigo=:codigo AND c.ativo=true";
            TypedQuery<Cliente> clienteConsultado = em.createQuery(jpql, Cliente.class);

            clienteConsultado.setParameter("codigo", codigo);

            if (clienteConsultado.getResultList().isEmpty()) {
                throw new EntityNotFoundException("Cliente com código " + codigo + " não foi encontrado");
            }

            return clienteConsultado.getSingleResult();
        }
    }

    /**
     * Buscar por uma {@link List} de {@link Cliente} que estejam <code>ativo(s)</code> no banco
     * @return um {@link List} de {@link Cliente}
     * @throws EntityNotFoundException caso nenhum cliente seja encontrado.
     */
    public List<Cliente> obterClientesAtivos() {
        try(EntityManager em = Database.getClienteManager()) {
            String jpql = "SELECT c FROM Cliente c WHERE c.ativo=true";
            TypedQuery<Cliente> listaClientesAtivos = em.createQuery(jpql, Cliente.class);

            if (listaClientesAtivos.getResultList().isEmpty()) {
                throw new EntityNotFoundException("Nenhum cliente ativo foi encontrado");
            }

            return listaClientesAtivos.getResultList();
        }
    }
}
