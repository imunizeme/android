package me.imunize.imunizeme.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sr. Décio Montanhani on 25/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacinasDTO implements Serializable {

    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("vacinas_id")
    private int vacinaId;
    @JsonProperty("data_tomada")
    private String dataTomada;

    public VacinasDTO() {
    }

    public VacinasDTO(int userId, int vacinaId, String dataTomada) {
        this.userId = userId;
        this.vacinaId = vacinaId;
        this.dataTomada = dataTomada;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVacinaId() {
        return vacinaId;
    }

    public void setVacinaId(int vacinaId) {
        this.vacinaId = vacinaId;
    }

    public String getDataTomada() {
        return dataTomada;
    }

    public void setDataTomada(String dataTomada) {
        this.dataTomada = dataTomada;
    }
}
