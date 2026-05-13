package br.uepg.mensalistas.dto;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PagamentoJogadorRequest {

    @NotNull(message = "Ano e obrigatorio.")
    @Min(value = 1900, message = "Ano deve ser maior ou igual a 1900.")
    @Max(value = 2100, message = "Ano deve ser menor ou igual a 2100.")
    private Integer ano;

    @NotNull(message = "Mes e obrigatorio.")
    @Min(value = 1, message = "Mes deve estar entre 1 e 12.")
    @Max(value = 12, message = "Mes deve estar entre 1 e 12.")
    private Integer mes;

    @NotNull(message = "Valor e obrigatorio.")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero.")
    @Digits(integer = 8, fraction = 2, message = "Valor deve ter no maximo 8 digitos inteiros e 2 decimais.")
    private BigDecimal valor;

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
