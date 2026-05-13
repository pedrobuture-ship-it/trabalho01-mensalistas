package br.uepg.mensalistas.dto;

import java.time.LocalDate;

public class JogadorResponse {

    private Integer codJogador;
    private String nome;
    private String email;
    private LocalDate dataNasc;

    public JogadorResponse(Integer codJogador, String nome, String email, LocalDate dataNasc) {
        this.codJogador = codJogador;
        this.nome = nome;
        this.email = email;
        this.dataNasc = dataNasc;
    }

    public Integer getCodJogador() {
        return codJogador;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }
}
