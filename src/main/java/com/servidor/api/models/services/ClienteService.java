package com.servidor.api.models.services;

import com.servidor.api.controllers.ClienteController;
import com.servidor.api.models.daos.ClienteDAO;
import com.servidor.api.models.dtos.DadosAtualizacaoParcial;
import com.servidor.api.models.dtos.DadosCadastroCliente;
import com.servidor.api.models.dtos.DadosClienteDTO;
import com.servidor.api.models.entities.Cliente;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas interações do {@link Cliente}
 * @see ClienteController
 * @author Jean Maciel
 */
@Stateless
public class ClienteService {

    /**
     * Salva o um cliente no banco a partir de dados de cadastro do {@link ClienteController}
     * @param dados que vem do DTO do {@link ClienteController}
     * @return um {@link Optional} de {@link Cliente} caso persista, ou um {@link Optional}
     * vazio, caso o DAO não consiga persistir o {@link Cliente} no Database
     */
    public Optional<DadosClienteDTO> salvarCliente(DadosCadastroCliente dados) {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente(dados);
        try {
            Cliente clienteCriado = clienteDAO.salvarCliente(cliente);

            DadosClienteDTO dadosReponse =
                    new DadosClienteDTO(clienteCriado.getCodigo(), clienteCriado.getNome(), clienteCriado.getCpf(), clienteCriado.getIdade());

            return Optional.of(dadosReponse);
        } catch(PersistenceException e) {
            return Optional.empty();
        }
    }

    /**
     * Solicita ao database um {@link Cliente} com o <code>código</code> informado no controller
     * @param codigo que veio na url capturado pelo método GET no {@link ClienteController}
     * @return um DTO com os dados do {@link Cliente} ao <code>código</code> correspondente,
     * ou <code>null</code> caso não tenha um Cliente com esse <code>código</code>.
     */
    public DadosClienteDTO obterClientePorCodigo(Long codigo) {
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            Cliente cliente = clienteDAO.obterClientePorCodigo(codigo);
            return new DadosClienteDTO(cliente.getCodigo(), cliente.getNome(), cliente.getCpf(), cliente.getIdade());
        } catch(EntityNotFoundException e) {
            return null;
        }
    }

    /**
     * Busca no database uma lista com os Clientes ativos no banco
     * @return um {@link List} do tipo {@link DadosClienteDTO}, ou uma Lista vazia,
     * caso não tenha nenhum cliente no banco.
     */
    public List<DadosClienteDTO> obterClientesAtivos() {
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            List<Cliente> clientesAtivos = clienteDAO.obterClientesAtivos();

            return clientesAtivos.stream().map(DadosClienteDTO::new).toList();

        } catch(EntityNotFoundException e) {
            return List.of();
        }
    }

    /**
     * Atualiza completamente o cliente no banco
     * @param dados que vem do controller
     * @return um DTO atualizado com os novos dados do cliente, ou retorna null, caso
     * o código não corresponda a um Cliente, ou lança uma exceção caso o servidor dê erro
     * na persistência da atualização.
     * @throws PersistenceException caso haja erro na atualização no banco
     */
    public DadosClienteDTO atualizarCliente(Long codigo, DadosClienteDTO dados) throws PersistenceException {
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            Cliente clienteAtualizado = clienteDAO.atualizarCliente(codigo, dados);
            return new DadosClienteDTO(clienteAtualizado);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    /**
     * Atualiza parcialmente os dados do cliente no banco
     * @param dados que vem do controller
     * @return um DTO com os dados atualizados
     * @throws PersistenceException caso haja erro na atualização no banco.
     */
    public DadosClienteDTO atualizarCliente(Long codigo, DadosAtualizacaoParcial dados) throws PersistenceException {
        if (dados.nome() == null && dados.cpf() == null && dados.idade() == null) {
            throw new IllegalArgumentException("Todos os argumentos são nulos");
        }
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            Cliente clienteAtualizado = clienteDAO.atualizacaoParcialCliente(codigo, dados);
            return new DadosClienteDTO(clienteAtualizado);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    /**
     * Solicita a exclusão lógica no Database
     * @param codigo que vem do Controller
     * @return true caso consiga excluir, ou false caso não exista um Cliente com esse código
     * @throws PersistenceException caso haja erro na transação
     */
    public boolean excluirCliente(Long codigo) throws PersistenceException{
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            clienteDAO.excluirCliente(codigo);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
