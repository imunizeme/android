package me.imunize.imunizeme.dto;


import android.location.Location;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sr. Décio Montanhani on 24/09/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Clinicas {
    @JsonProperty("nome_clinica")
    private String nome;
    private boolean patrocinado;
    private int zipcode;
    private String street;
    @JsonProperty("street_num")
    private int numero;
    private String complement;
    private String local;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public double getLat(){


        String[] split = local.split(",");
        double lat = Double.parseDouble(split[0].substring(1));
        Log.i("Lat: ", String.valueOf(lat));

        return lat;
    }

    public double getLong(){

        String[] split = local.split(",");
        double lng = Double.parseDouble(split[1].substring(0, split[1].length()-1));
        Log.i("Long: ", String.valueOf(lng));

        return lng;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isPatrocinado() {
        return patrocinado;
    }

    public void setPatrocinado(boolean patrocinado) {
        this.patrocinado = patrocinado;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    @Override
    public String toString() {
        return street + ", " + getNumero() + " - CEP: " + zipcode;
    }
}
