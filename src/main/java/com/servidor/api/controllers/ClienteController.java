package com.servidor.api.controllers;

import com.servidor.api.models.dtos.DadosCadastroCliente;
import com.servidor.api.models.dtos.DadosClienteDTO;
import com.servidor.api.models.services.ClienteService;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.Optional;

/**
 * Os recursos do cliente ficam nesse controller,
 * devem ser acessados com localhost:8080/api/cliente
 *
 * @author Jean Maciel
 */
@Path("cliente")
public class ClienteController {

    @EJB
    private ClienteService clienteService;

    @Context
    private UriInfo uriInfo;

    /**
     * Cadastra um cliente no banco
     * @param cliente o DTO que é recebido na requisição
     * @return um JSON com código e nome do cliente cadastrado
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criarCliente(@Valid DadosCadastroCliente cliente) {
        // salva o cliente no banco
        Optional<DadosClienteDTO> dadosResponse = clienteService.salvarCliente(cliente);
        if (dadosResponse.isPresent()) {

            // constrói a URI
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(dadosResponse.get().codigo().toString())
                    .build();
            /* devolve um JSON com o código e nome, e o cabeçalho location com
             * onde o recurso pode ser acessado
             */
            return Response.created(uri).entity(dadosResponse.get()).build();
        } else {
            return Response.serverError().build();
        }
    }
}
