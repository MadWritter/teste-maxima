package com.servidor.api.models.dtos;

import com.servidor.api.models.entities.Cliente
/**
 * Dados de devolução na requisição do cadastro do {@link Cliente}
 * @see Cliente
 * @author Jean Maciel
 * @param codigo que foi gerado pelo banco
 * @param nome do cliente cadastrado
 */
public record DadosClienteDTO(
        Long codigo,
        String nome
) {
}
