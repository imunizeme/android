package me.imunize.imunizeme.models;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Sr. Décio Montanhani on 09/08/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario implements Serializable{

    @JsonProperty("user_id")
    private Long id;
    private String name;
    private String email;
    @JsonProperty("cpf_cnpj")
    private String cpfCnpj;
    private String password;
    @JsonProperty("birth_date")
    private String aniversario;


    public Usuario(String name, String email, String cpfCnpj, String password) {
        this.name = name;
        this.email = email;
        this.cpfCnpj = cpfCnpj;
        this.password = password;
        this.aniversario = aniversario;
    }

    public Usuario() {
    }

    public Usuario(String cpfCnpj, String password) {
        this.cpfCnpj = cpfCnpj;
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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashPassword(){
        return sha1(password);
    }

    @NonNull
    private String sha1(String input)
    {
        MessageDigest mDigest = null;
        StringBuffer sb = null;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public String getAniversario() {
        return aniversario;
    }

    public void setAniversario(String aniversario) {
        this.aniversario = aniversario;
    }
}
