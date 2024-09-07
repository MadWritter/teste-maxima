package com.servidor.api.controllers;

import com.servidor.api.models.dtos.DadosAtualizacaoParcial;
import com.servidor.api.models.dtos.DadosCadastroCliente;
import com.servidor.api.models.dtos.DadosClienteDTO;
import com.servidor.api.models.services.ClienteService;
import jakarta.ejb.EJB;
import jakarta.persistence.PersistenceException;
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

    /**
     * Atualiza todos os dados do Cliente no banco
     * @param dados enviados no corpo da requisição, deve conter o <code>codigo</code>
     * @return um Json com os dados atualizados, ou 404 not found caso o <code>codigo</code>
     * não tenha um Cliente correspondente.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarClienteCompletamente(@Valid DadosClienteDTO dados) {
        DadosClienteDTO dadosClienteAtualizado;
        try {
            dadosClienteAtualizado = clienteService.atualizarCliente(dados);
        } catch (PersistenceException e) {
            return Response.serverError().build(); // pro caso do erro de persistência
        }

        if (dadosClienteAtualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // pra caso o código não exista no banco
        }

        return Response.ok(dadosClienteAtualizado).build(); // caso ocorra a atualização completa
    }

    /**
     * Atualização com dados parciais
     * @param dados os dados parciais que serão enviados, obrigatório conter o <code>codigo</code>
     * do Cliente e pelo menos um atributo para ser alterado
     * @return um Json com os dados atualizados
     */
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizarClienteParcialmente(@Valid DadosAtualizacaoParcial dados) {
        DadosClienteDTO dadosAtualizados;
        try {
            dadosAtualizados = clienteService.atualizarCliente(dados);

            if (dadosAtualizados == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok(dadosAtualizados).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (PersistenceException e) {
            return Response.serverError().build();
        }
    }

    /**
     * Faz a exclusão lógica de um Cliente
     * @param codigo do cliente para excluir
     * @return 200 (ok) caso consiga excluir e 404 (not found) caso o código não tenha correspondente
     * ativo no banco
     */
    @DELETE
    @Path("/{codigo}")
    public Response excluirCliente(@PathParam("codigo") Long codigo) {
        try {
            if (clienteService.excluirCliente(codigo)) {
                return Response.ok().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch(PersistenceException e) {
            return Response.serverError().build();
        }
    }
}
