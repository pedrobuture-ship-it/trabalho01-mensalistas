package br.uepg.mensalistas.mapper;

import br.uepg.mensalistas.dto.PagamentoResponse;
import br.uepg.mensalistas.model.Pagamento;

public final class PagamentoMapper {

    private PagamentoMapper() {
    }

    public static PagamentoResponse toResponse(Pagamento pagamento) {
        return new PagamentoResponse(
            pagamento.getCodPagamento(),
            pagamento.getAno().intValue(),
            pagamento.getMes().intValue(),
            pagamento.getValor(),
            pagamento.getJogador().getCodJogador(),
            pagamento.getJogador().getNome()
        );
    }
}
