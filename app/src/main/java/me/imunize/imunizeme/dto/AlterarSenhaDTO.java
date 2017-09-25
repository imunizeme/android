package me.imunize.imunizeme.dto;

/**
 * Created by Sr. Décio Montanhani on 23/09/2017.
 */


public class AlterarSenhaDTO {
    private String password;

    public AlterarSenhaDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
