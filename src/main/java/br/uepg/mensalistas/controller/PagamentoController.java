package br.uepg.mensalistas.controller;

import br.uepg.mensalistas.dto.PagamentoRequest;
import br.uepg.mensalistas.dto.PagamentoResponse;
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
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping
    public List<PagamentoResponse> listar(
        @RequestParam(required = false) Integer codJogador,
        @RequestParam(required = false) Integer ano,
        @RequestParam(required = false) Integer mes
    ) {
        return pagamentoService.listar(codJogador, ano, mes);
    }

    @GetMapping("/{codPagamento}")
    public PagamentoResponse buscar(@PathVariable Integer codPagamento) {
        return pagamentoService.buscar(codPagamento);
    }

    @PostMapping
    public ResponseEntity<PagamentoResponse> criar(@Valid @RequestBody PagamentoRequest request) {
        PagamentoResponse response = pagamentoService.criar(request);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{codPagamento}")
            .buildAndExpand(response.getCodPagamento())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{codPagamento}")
    public PagamentoResponse atualizar(
        @PathVariable Integer codPagamento,
        @Valid @RequestBody PagamentoRequest request
    ) {
        return pagamentoService.atualizar(codPagamento, request);
    }

    @DeleteMapping("/{codPagamento}")
    public ResponseEntity<Void> remover(@PathVariable Integer codPagamento) {
        pagamentoService.remover(codPagamento);
        return ResponseEntity.noContent().build();
    }
}
