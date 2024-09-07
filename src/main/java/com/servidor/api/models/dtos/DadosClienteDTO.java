package com.servidor.api.models.dtos;

import com.servidor.api.controllers.ClienteController;
import com.servidor.api.models.entities.Cliente;

/**
 * Dados de devolução na requisição do cadastro do {@link Cliente}
 * @see ClienteController;
 * @author Jean Maciel
 * @param codigo que foi gerado pelo banco
 * @param nome do cliente cadastrado
 * @param cpf cadastrado pelo cliente
 * @param idade que foi informada
 */
public record DadosClienteDTO(
        Long codigo,
        String nome,
        String cpf,
        Integer idade
) {
}
