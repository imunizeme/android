package me.imunize.imunizeme.models;

import java.io.Serializable;

/**
 * Created by Sr. Décio Montanhani on 13/09/2017.
 */

public class Vacina implements Serializable {

    private int id;
    private String nome;
    private int dose;
    private String data;
    private int tomou;

    public Vacina() {
    }

    public Vacina(String nome, int dose, String data, int tomou) {
        this.nome = nome;
        this.dose = dose;
        this.data = data;
        this.tomou = tomou;
    }

    public int getTomou() {
        return tomou;
    }

    public void setTomou(int tomou) {
        this.tomou = tomou;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
