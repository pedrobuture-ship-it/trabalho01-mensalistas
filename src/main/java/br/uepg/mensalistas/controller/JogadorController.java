package br.uepg.mensalistas.controller;

import br.uepg.mensalistas.dto.JogadorRequest;
import br.uepg.mensalistas.dto.JogadorResponse;
import br.uepg.mensalistas.dto.PagamentoJogadorRequest;
import br.uepg.mensalistas.dto.PagamentoResponse;
import br.uepg.mensalistas.service.JogadorService;
import br.uepg.mensalistas.service.PagamentoService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/jogadores")
public class JogadorController {

    private final JogadorService jogadorService;
    private final PagamentoService pagamentoService;

    public JogadorController(JogadorService jogadorService, PagamentoService pagamentoService) {
        this.jogadorService = jogadorService;
        this.pagamentoService = pagamentoService;
    }

    @GetMapping
    public List<JogadorResponse> listar() {
        return jogadorService.listar();
    }

    @GetMapping("/{codJogador}")
    public JogadorResponse buscar(@PathVariable Integer codJogador) {
        return jogadorService.buscar(codJogador);
    }

    @PostMapping
    public ResponseEntity<JogadorResponse> criar(@Valid @RequestBody JogadorRequest request) {
        JogadorResponse response = jogadorService.criar(request);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{codJogador}")
            .buildAndExpand(response.getCodJogador())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{codJogador}")
    public JogadorResponse atualizar(
        @PathVariable Integer codJogador,
        @Valid @RequestBody JogadorRequest request
    ) {
        return jogadorService.atualizar(codJogador, request);
    }

    @DeleteMapping("/{codJogador}")
    public ResponseEntity<Void> remover(@PathVariable Integer codJogador) {
        jogadorService.remover(codJogador);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codJogador}/pagamentos")
    public List<PagamentoResponse> listarPagamentos(
        @PathVariable Integer codJogador,
        @RequestParam(required = false) Integer ano,
        @RequestParam(required = false) Integer mes
    ) {
        return pagamentoService.listar(codJogador, ano, mes);
    }

    @PostMapping("/{codJogador}/pagamentos")
    public ResponseEntity<PagamentoResponse> criarPagamento(
        @PathVariable Integer codJogador,
        @Valid @RequestBody PagamentoJogadorRequest request
    ) {
        PagamentoResponse response = pagamentoService.criarParaJogador(codJogador, request);
        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .path("/api/pagamentos/{codPagamento}")
            .buildAndExpand(response.getCodPagamento())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }
}
