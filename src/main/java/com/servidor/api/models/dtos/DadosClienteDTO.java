package com.servidor.api.models.dtos;

import com.servidor.api.controllers.ClienteController;
import com.servidor.api.models.entities.Cliente;
import jakarta.validation.constraints.*;

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
        @NotBlank(message = "Nome não pode estar em branco")
        @Size(min = 3, max = 100, message = "Nome deve conter entre 3 e 100 caracteres")
        String nome,
        @NotBlank(message = "CPF não pode estar em branco")
        @Size(min = 11, max = 11, message = "CPF deve conter somente números (11 dígitos)")
        String cpf,
        @NotNull(message = "Idade não pode ser nulo")
        @Min(value = 1, message = "Idade não pode estar abaixo de 1")
        @Max(value = 99, message = "Idade não pode estar acima de 99")
        Integer idade
) {

    public DadosClienteDTO(Cliente cliente) {
        this(cliente.getCodigo(), cliente.getNome(), cliente.getCpf(), cliente.getIdade());
    }
}
