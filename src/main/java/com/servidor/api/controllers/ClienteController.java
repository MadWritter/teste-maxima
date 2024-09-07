package com.servidor.api.controllers;

import com.servidor.api.models.dtos.DadosCadastroCliente;
import com.servidor.api.models.dtos.DadosClienteDTO;
import com.servidor.api.models.services.ClienteService;
import jakarta.ejb.EJB;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;
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

            return Response.created(uri).entity(dadosResponse.get()).build();
        } else {
            return Response.serverError().build();
        }
    }

    /**
     * Solicita um Json com os dados do cliente a partir de um código
     * @param codigo do cliente
     * @return um Json com os dados do cliente e código 200, ou retorna
     * 404 caso o <code>codigo</code> fornecido na url não tenha correspondente
     */
    @GET
    @Path("/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterClientePorCodigo(@PathParam("codigo") Long codigo) {
        DadosClienteDTO dados = clienteService.obterClientePorCodigo(codigo);

        if (dados == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(dados).build();
    }

    /**
     * Solicita um Json com os dados de todos os clientes ativos
     * @return um Json com os clientes, código 200 (ok), ou 404 (not found)
     * caso não tenham clientes ativos no banco.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterClientes() {
        List<DadosClienteDTO> clientesAtivos = clienteService.obterClientesAtivos();
        if (clientesAtivos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(clientesAtivos).build();
    }
}
