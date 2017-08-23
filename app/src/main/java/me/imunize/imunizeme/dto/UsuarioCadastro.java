package me.imunize.imunizeme.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sr. Décio Montanhani on 21/08/2017.
 */

public class UsuarioCadastro {

    @SerializedName("cpf_cnpj")
    private String cpf;
    private String password;

    public UsuarioCadastro(String cpf, String password) {
        this.cpf = cpf;
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
