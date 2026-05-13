package br.uepg.mensalistas.repository;

import br.uepg.mensalistas.model.Pagamento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

    @EntityGraph(attributePaths = "jogador")
    Optional<Pagamento> findByCodPagamento(Integer codPagamento);

    @Query("select p from Pagamento p join fetch p.jogador j "
        + "where (:codJogador is null or j.codJogador = :codJogador) "
        + "and (:ano is null or p.ano = :ano) "
        + "and (:mes is null or p.mes = :mes) "
        + "order by p.ano desc, p.mes desc, j.nome asc")
    List<Pagamento> filtrar(
        @Param("codJogador") Integer codJogador,
        @Param("ano") Short ano,
        @Param("mes") Byte mes
    );

    boolean existsByJogadorCodJogadorAndAnoAndMes(Integer codJogador, Short ano, Byte mes);

    boolean existsByJogadorCodJogadorAndAnoAndMesAndCodPagamentoNot(
        Integer codJogador,
        Short ano,
        Byte mes,
        Integer codPagamento
    );
}
