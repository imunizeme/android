package me.imunize.imunizeme.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sr. Décio Montanhani on 09/08/2017.
 */

public class Usuario implements Serializable{

    private Long id;
    @SerializedName("name")
    private String name;
    private String email;
    private String cpf_cnpj;
    private String password;


    public Usuario(String name, String email, String cpf_cnpj, String password) {
        this.name = name;
        this.email = email;
        this.cpf_cnpj = cpf_cnpj;
        this.password = password;
    }

    public Usuario() {
    }

    public Usuario(String cpf_cnpj, String password) {
        this.cpf_cnpj = cpf_cnpj;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCpf_cnpj() {
        return cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
