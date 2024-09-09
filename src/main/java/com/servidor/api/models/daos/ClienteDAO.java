package com.servidor.api.models.daos;

import com.servidor.api.models.config.Database;
import com.servidor.api.models.dtos.DadosAtualizacaoParcial;
import com.servidor.api.models.dtos.DadosClienteDTO;
import com.servidor.api.models.entities.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

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
        EntityManager em = Database.getClienteManager();
        try(em) {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente; // aqui ele já tem o código retornado, pela integridade referencial
        } catch(PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Devolve um {@link Cliente} ativo a partir de um <code>código</code>
     * @param codigo que idenfica o cliente
     * @return um Cliente a partir do <code>código</code> informado
     * @throws EntityNotFoundException caso não tenha um Cliente com o <code>código</code>
     * correspondente
     */
    public Cliente obterClientePorCodigo(Long codigo) {
        try(EntityManager em = Database.getClienteManager()) {
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

    /**
     * Atualiza todos os dados de um Cliente existente
     * @param codigo do cliente
     * @param dados enviados pelo serviço
     * @return um Cliente atualizado
     * @throws EntityNotFoundException caso o codigo informado não corresponda
     * a um Cliente existente no banco
     * @throws PersistenceException caso haja erro na atualização.
     */
    public Cliente atualizarCliente(Long codigo, DadosClienteDTO dados) {
        Cliente cliente = obterClientePorCodigo(codigo);
        EntityManager em = Database.getClienteManager();

        try(em){
            em.getTransaction().begin();
            cliente.setNome(dados.nome());
            cliente.setCpf(dados.cpf());
            cliente.setIdade(dados.idade());
            em.flush();
            em.getTransaction().commit();

            return cliente;
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Atualiza parcialmente os dados do Cliente
     * @param dados que devem ser atualizados
     * @return o Cliente com os dados atualizados
     * @throws EntityNotFoundException caso o código informado no DTO não tenha correspondente no banco
     * @throws PersistenceException caso não tenha efetuado a atualização com sucesso
     */
    public Cliente atualizacaoParcialCliente(Long codigo, DadosAtualizacaoParcial dados) {
        Cliente cliente = obterClientePorCodigo(codigo);
        EntityManager em = Database.getClienteManager();

        try(em){
            em.getTransaction().begin();
            if (dados.nome() != null) {
                cliente.setNome(dados.nome());
            }
            if (dados.cpf() != null) {
                cliente.setCpf(dados.cpf());
            }
            if (dados.idade() != null) {
                cliente.setIdade(dados.idade());
            }
            em.flush();
            em.getTransaction().commit();

            return cliente;
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    /**
     * Faz a exclusão lógica do Cliente no banco
     * @param codigo do cliente que deseja excluir
     * @throws EntityNotFoundException caso o código não tenha um correspondente no banco
     * @throws PersistenceException caso não consiga fazer a exclusão com sucesso.
     */
    public void excluirCliente(Long codigo) {
        Cliente cliente = obterClientePorCodigo(codigo);
        EntityManager em = Database.getClienteManager();

        try(em) {
            em.getTransaction().begin();
            cliente.excluirCliente();
            em.flush();
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}
