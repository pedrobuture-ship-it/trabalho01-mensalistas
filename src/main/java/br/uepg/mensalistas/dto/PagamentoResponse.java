package br.uepg.mensalistas.dto;

import java.math.BigDecimal;

public class PagamentoResponse {

    private Integer codPagamento;
    private Integer ano;
    private Integer mes;
    private BigDecimal valor;
    private Integer codJogador;
    private String nomeJogador;

    public PagamentoResponse(
        Integer codPagamento,
        Integer ano,
        Integer mes,
        BigDecimal valor,
        Integer codJogador,
        String nomeJogador
    ) {
        this.codPagamento = codPagamento;
        this.ano = ano;
        this.mes = mes;
        this.valor = valor;
        this.codJogador = codJogador;
        this.nomeJogador = nomeJogador;
    }

    public Integer getCodPagamento() {
        return codPagamento;
    }

    public Integer getAno() {
        return ano;
    }

    public Integer getMes() {
        return mes;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getCodJogador() {
        return codJogador;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }
}
