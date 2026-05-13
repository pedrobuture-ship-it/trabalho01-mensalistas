package br.uepg.mensalistas.service;

import br.uepg.mensalistas.dto.PagamentoJogadorRequest;
import br.uepg.mensalistas.dto.PagamentoRequest;
import br.uepg.mensalistas.dto.PagamentoResponse;
import br.uepg.mensalistas.exception.BusinessException;
import br.uepg.mensalistas.exception.ResourceNotFoundException;
import br.uepg.mensalistas.mapper.PagamentoMapper;
import br.uepg.mensalistas.model.Jogador;
import br.uepg.mensalistas.model.Pagamento;
import br.uepg.mensalistas.repository.PagamentoRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final JogadorService jogadorService;

    public PagamentoService(PagamentoRepository pagamentoRepository, JogadorService jogadorService) {
        this.pagamentoRepository = pagamentoRepository;
        this.jogadorService = jogadorService;
    }

    @Transactional(readOnly = true)
    public List<PagamentoResponse> listar(Integer codJogador, Integer ano, Integer mes) {
        if (codJogador != null) {
            jogadorService.buscarEntidade(codJogador);
        }

        Short anoConvertido = converterAno(ano);
        Byte mesConvertido = converterMes(mes);

        return pagamentoRepository.filtrar(codJogador, anoConvertido, mesConvertido)
            .stream()
            .map(PagamentoMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagamentoResponse buscar(Integer codPagamento) {
        return PagamentoMapper.toResponse(buscarEntidade(codPagamento));
    }

    @Transactional
    public PagamentoResponse criar(PagamentoRequest request) {
        return criarParaJogador(request.getCodJogador(), request);
    }

    @Transactional
    public PagamentoResponse criarParaJogador(Integer codJogador, PagamentoJogadorRequest request) {
        Jogador jogador = jogadorService.buscarEntidade(codJogador);
        Short ano = converterAno(request.getAno());
        Byte mes = converterMes(request.getMes());

        validarPagamentoUnico(codJogador, ano, mes, null);

        Pagamento pagamento = new Pagamento();
        pagamento.setJogador(jogador);
        pagamento.setAno(ano);
        pagamento.setMes(mes);
        pagamento.setValor(request.getValor());

        return PagamentoMapper.toResponse(pagamentoRepository.save(pagamento));
    }

    @Transactional
    public PagamentoResponse atualizar(Integer codPagamento, PagamentoRequest request) {
        Pagamento pagamento = buscarEntidade(codPagamento);
        Jogador jogador = jogadorService.buscarEntidade(request.getCodJogador());
        Short ano = converterAno(request.getAno());
        Byte mes = converterMes(request.getMes());

        validarPagamentoUnico(request.getCodJogador(), ano, mes, codPagamento);

        pagamento.setJogador(jogador);
        pagamento.setAno(ano);
        pagamento.setMes(mes);
        pagamento.setValor(request.getValor());

        return PagamentoMapper.toResponse(pagamentoRepository.save(pagamento));
    }

    @Transactional
    public void remover(Integer codPagamento) {
        Pagamento pagamento = buscarEntidade(codPagamento);
        pagamentoRepository.delete(pagamento);
    }

    @Transactional(readOnly = true)
    public Pagamento buscarEntidade(Integer codPagamento) {
        return pagamentoRepository.findByCodPagamento(codPagamento)
            .orElseThrow(() -> new ResourceNotFoundException("Pagamento nao encontrado."));
    }

    private void validarPagamentoUnico(Integer codJogador, Short ano, Byte mes, Integer codPagamentoAtual) {
        boolean pagamentoJaExiste;

        if (codPagamentoAtual == null) {
            pagamentoJaExiste = pagamentoRepository.existsByJogadorCodJogadorAndAnoAndMes(codJogador, ano, mes);
        } else {
            pagamentoJaExiste = pagamentoRepository.existsByJogadorCodJogadorAndAnoAndMesAndCodPagamentoNot(
                codJogador,
                ano,
                mes,
                codPagamentoAtual
            );
        }

        if (pagamentoJaExiste) {
            throw new BusinessException("Este jogador ja possui pagamento cadastrado para o ano e mes informados.");
        }
    }

    private Short converterAno(Integer ano) {
        if (ano == null) {
            return null;
        }

        if (ano < 1900 || ano > 2100) {
            throw new BusinessException("Ano deve estar entre 1900 e 2100.");
        }

        return ano.shortValue();
    }

    private Byte converterMes(Integer mes) {
        if (mes == null) {
            return null;
        }

        if (mes < 1 || mes > 12) {
            throw new BusinessException("Mes deve estar entre 1 e 12.");
        }

        return mes.byteValue();
    }
}
