package me.imunize.imunizeme.dto;

/**
 * Created by Sr. Décio Montanhani on 23/09/2017.
 */

public class Sexo {

    private String sexo;
    private String sexo_completo;

    public Sexo(String sexo, String sexo_completo) {
        this.sexo = sexo;
        this.sexo_completo = sexo_completo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSexo_completo() {
        return sexo_completo;
    }

    public void setSexo_completo(String sexo_completo) {
        this.sexo_completo = sexo_completo;
    }

    @Override
    public String toString() {
        return sexo_completo;
    }
}
