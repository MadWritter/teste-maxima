package com.servidor.api.models.entities;

import com.servidor.api.controllers.ClienteController;
import com.servidor.api.models.dtos.DadosCadastroCliente;
import jakarta.persistence.*;

/**
 * Entidade que representa o cliente
 * @see ClienteController
 * @author Jean Maciel
 */
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @Column(length = 100, nullable = false)
    private String nome;
    @Column(length = 11, nullable = false)
    private String cpf;
    @Column(length = 99, nullable = false)
    private Integer idade;
    @Column(nullable = false)
    private Boolean ativo;

    // Exigência da JPA
    public Cliente () {}

    /**
     * Construtor que recebe os dados de cadastro fornecido pelo DTO
     * @param dados que vem do {@link ClienteController} no cadastro
     */
    public Cliente(DadosCadastroCliente dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.idade = dados.idade();
        this.ativo = true;
    }

    public Long getCodigo(){
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void excluirCliente() {
        this.ativo = false;
    }
}
