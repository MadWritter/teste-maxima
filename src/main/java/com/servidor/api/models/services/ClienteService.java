package com.servidor.api.models.services;

import com.servidor.api.controllers.ClienteController;
import com.servidor.api.models.daos.ClienteDAO;
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
        Cliente cliente = new Cliente(dados);
        ClienteDAO clienteDAO = new ClienteDAO();

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
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
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
        try {

            ClienteDAO clienteDAO = new ClienteDAO();
            List<Cliente> clientesAtivos = clienteDAO.obterClientesAtivos();

            return clientesAtivos.stream().map(DadosClienteDTO::new).toList();

        } catch(EntityNotFoundException e) {
            return List.of();
        }
    }
}
