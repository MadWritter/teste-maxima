package com.servidor.api.models.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO que recebe os dados parcialmente para atualizar
 *
 * @author Jean Maciel
 *
 * @param codigo obrigatório conter
 * @param nome do cliente para atualizar
 * @param cpf 11 dígitos
 * @param idade entre 1 e 99
 */
public record DadosAtualizacaoParcial(
        Long codigo,
        @Size(min = 3, max = 100, message = "Nome deve conter entre 3 e 100 caracteres")
        String nome,
        @Size(min = 11, max = 11, message = "CPF deve conter somente números (11 dígitos)")
        String cpf,
        @Min(value = 1, message = "Idade não pode estar abaixo de 1")
        @Max(value = 99, message = "Idade não pode estar acima de 99")
        Integer idade
) {
}
