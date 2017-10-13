package me.imunize.imunizeme.models;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sr. Décio Montanhani on 25/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vacina implements Serializable {

    private int id;
    @JsonProperty("nome_vacina")
    private String nome;
    private int dose;
    @JsonProperty("dose_anterior_id")
    private int doseAnterior;
    @JsonProperty("periodo_dose_anterior")
    private int periodoDoseAnterior;
    private boolean reforco;
    @JsonProperty("idade_inicio")
    private int idadeInicio;
    @JsonProperty("doencas_evitadas")
    private String doencasEvitadas;
    private String observacoes;
    @JsonProperty("cv_id")
    private int idCarteirinha;
    @JsonProperty("data_tomada")
    private Date data;
    @JsonProperty("vacinas_id")
    private int idVacinaTomada;
    private int tomou;

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

    public int getDoseAnterior() {
        return doseAnterior;
    }

    public void setDoseAnterior(int doseAnterior) {
        this.doseAnterior = doseAnterior;
    }

    public int getPeriodoDoseAnterior() {
        return periodoDoseAnterior;
    }

    public void setPeriodoDoseAnterior(int periodoDoseAnterior) {
        this.periodoDoseAnterior = periodoDoseAnterior;
    }

    public boolean isReforco() {
        return reforco;
    }

    public void setReforco(boolean reforco) {
        this.reforco = reforco;
    }

    public int getIdadeInicio() {
        return idadeInicio;
    }

    public void setIdadeInicio(int idadeInicio) {
        this.idadeInicio = idadeInicio;
    }

    public String getDoencasEvitadas() {
        return doencasEvitadas;
    }

    public void setDoencasEvitadas(String doencasEvitadas) {
        this.doencasEvitadas = doencasEvitadas;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public int getIdCarteirinha() {
        return idCarteirinha;
    }

    public void setIdCarteirinha(int idCarteirinha) {
        this.idCarteirinha = idCarteirinha;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdVacinaTomada() {
        return idVacinaTomada;
    }

    public void setIdVacinaTomada(int idVacinaTomada) {
        this.idVacinaTomada = idVacinaTomada;
    }

    /*@Override
    public String toString() {
        return nome + ", " + idVacinaTomada + ", " + idCarteirinha + ", Data tomada: " + data;
    }
    */

    @Override
    public String toString() {

        String nomeCortado;

        if(nome.contains("Vacina Oral de")){
            nomeCortado = nome.replace("Vacina Oral de", "");
        }else if(nome.contains("Vacina Oral ")){
            nomeCortado = nome.replace("Vacina Oral ", "");
        }else if(nome.contains("Vacina ")){
            nomeCortado = nome.replace("Vacina ", "");
        }else{
            nomeCortado = nome;
        }



        String retorno;
        if(reforco){
            retorno = nomeCortado + ". " + dose + "ª Dose REFORCO";
        }else{
            retorno = nomeCortado + ". " + dose + "ª Dose";
        }

        return retorno;

    }
}
