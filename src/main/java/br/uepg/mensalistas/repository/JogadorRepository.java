package br.uepg.mensalistas.repository;

import br.uepg.mensalistas.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorRepository extends JpaRepository<Jogador, Integer> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndCodJogadorNot(String email, Integer codJogador);
}
