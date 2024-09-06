package com.servidor.api.models.services;

import com.servidor.api.controllers.ClienteController;
import com.servidor.api.models.daos.ClienteDAO;
import com.servidor.api.models.dtos.DadosCadastroCliente;
import com.servidor.api.models.dtos.DadosClienteDTO;
import com.servidor.api.models.entities.Cliente;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceException;

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
     * @return um {@link Optional} de {@link Cliente}, caso persista
     */
    public Optional<DadosClienteDTO> salvarCliente(DadosCadastroCliente dados) {
        Cliente cliente = new Cliente(dados);
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            Cliente clienteCriado = clienteDAO.salvarCliente(cliente);
            DadosClienteDTO dadosReponse = new DadosClienteDTO(clienteCriado.getCodigo(), clienteCriado.getNome());
            return Optional.of(dadosReponse);
        } catch(PersistenceException e) {
            return Optional.empty();
        }
    }
}
