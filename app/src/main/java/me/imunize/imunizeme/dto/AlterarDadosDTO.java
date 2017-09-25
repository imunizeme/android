package me.imunize.imunizeme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sr. Décio Montanhani on 23/09/2017.
 */

public class AlterarDadosDTO {

    private String name;
    private String email;
    @JsonProperty("birth_date")
    private String aniversario;

    public AlterarDadosDTO() {
    }

    public AlterarDadosDTO(String name, String email, String aniversario) {
        this.name = name;
        this.email = email;
        this.aniversario = aniversario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAniversario() {
        return aniversario;
    }

    public void setAniversario(String aniversario) {
        this.aniversario = aniversario;
    }
}
