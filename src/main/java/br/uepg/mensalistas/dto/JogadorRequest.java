package br.uepg.mensalistas.dto;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class JogadorRequest {

    @NotBlank(message = "Nome e obrigatorio.")
    @Size(max = 60, message = "Nome deve ter no maximo 60 caracteres.")
    private String nome;

    @NotBlank(message = "Email e obrigatorio.")
    @Email(message = "Email deve ser valido.")
    @Size(max = 60, message = "Email deve ter no maximo 60 caracteres.")
    private String email;

    @NotNull(message = "Data de nascimento e obrigatoria.")
    @Past(message = "Data de nascimento deve estar no passado.")
    private LocalDate dataNasc;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }
}
