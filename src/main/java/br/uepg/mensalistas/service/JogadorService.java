package br.uepg.mensalistas.service;

import br.uepg.mensalistas.dto.JogadorRequest;
import br.uepg.mensalistas.dto.JogadorResponse;
import br.uepg.mensalistas.exception.BusinessException;
import br.uepg.mensalistas.exception.ResourceNotFoundException;
import br.uepg.mensalistas.mapper.JogadorMapper;
import br.uepg.mensalistas.model.Jogador;
import br.uepg.mensalistas.repository.JogadorRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;

    public JogadorService(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }

    @Transactional(readOnly = true)
    public List<JogadorResponse> listar() {
        return jogadorRepository.findAll()
            .stream()
            .map(JogadorMapper::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JogadorResponse buscar(Integer codJogador) {
        return JogadorMapper.toResponse(buscarEntidade(codJogador));
    }

    @Transactional
    public JogadorResponse criar(JogadorRequest request) {
        validarEmailDisponivel(request.getEmail(), null);
        Jogador jogador = JogadorMapper.toEntity(request);
        return JogadorMapper.toResponse(jogadorRepository.save(jogador));
    }

    @Transactional
    public JogadorResponse atualizar(Integer codJogador, JogadorRequest request) {
        Jogador jogador = buscarEntidade(codJogador);
        validarEmailDisponivel(request.getEmail(), codJogador);
        JogadorMapper.copyToEntity(request, jogador);
        return JogadorMapper.toResponse(jogadorRepository.save(jogador));
    }

    @Transactional
    public void remover(Integer codJogador) {
        Jogador jogador = buscarEntidade(codJogador);
        jogadorRepository.delete(jogador);
    }

    @Transactional(readOnly = true)
    public Jogador buscarEntidade(Integer codJogador) {
        return jogadorRepository.findById(codJogador)
            .orElseThrow(() -> new ResourceNotFoundException("Jogador nao encontrado."));
    }

    private void validarEmailDisponivel(String email, Integer codJogadorAtual) {
        boolean emailEmUso;

        if (codJogadorAtual == null) {
            emailEmUso = jogadorRepository.existsByEmailIgnoreCase(email);
        } else {
            emailEmUso = jogadorRepository.existsByEmailIgnoreCaseAndCodJogadorNot(email, codJogadorAtual);
        }

        if (emailEmUso) {
            throw new BusinessException("Ja existe um jogador cadastrado com este email.");
        }
    }
}
