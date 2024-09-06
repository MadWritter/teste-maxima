package com.servidor.api.models.dtos;

import jakarta.validation.constraints.*;
import com.servidor.api.models.entities.Cliente;

/**
 * DAO que representa os dados recebidos pelo JSON na requisição
 * do cadastro do {@link Cliente}
 * @see Cliente
 * @author Jean Maciel
 * @param nome do cliente
 * @param cpf 11 dígitos obrigatórios (em String)
 * @param idade de 1 a 99
 *
 */
public record DadosCadastroCliente(
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
}
