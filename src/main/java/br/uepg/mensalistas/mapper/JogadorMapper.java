package br.uepg.mensalistas.mapper;

import br.uepg.mensalistas.dto.JogadorRequest;
import br.uepg.mensalistas.dto.JogadorResponse;
import br.uepg.mensalistas.model.Jogador;

public final class JogadorMapper {

    private JogadorMapper() {
    }

    public static Jogador toEntity(JogadorRequest request) {
        Jogador jogador = new Jogador();
        jogador.setNome(request.getNome());
        jogador.setEmail(request.getEmail());
        jogador.setDataNasc(request.getDataNasc());
        return jogador;
    }

    public static void copyToEntity(JogadorRequest request, Jogador jogador) {
        jogador.setNome(request.getNome());
        jogador.setEmail(request.getEmail());
        jogador.setDataNasc(request.getDataNasc());
    }

    public static JogadorResponse toResponse(Jogador jogador) {
        return new JogadorResponse(
            jogador.getCodJogador(),
            jogador.getNome(),
            jogador.getEmail(),
            jogador.getDataNasc()
        );
    }
}
