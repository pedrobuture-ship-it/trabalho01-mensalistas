package br.uepg.mensalistas.dto;

import javax.validation.constraints.NotNull;

public class PagamentoRequest extends PagamentoJogadorRequest {

    @NotNull(message = "Codigo do jogador e obrigatorio.")
    private Integer codJogador;

    public Integer getCodJogador() {
        return codJogador;
    }

    public void setCodJogador(Integer codJogador) {
        this.codJogador = codJogador;
    }
}
