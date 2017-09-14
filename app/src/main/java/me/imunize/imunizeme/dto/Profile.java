package me.imunize.imunizeme.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Sr. Décio Montanhani on 02/09/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {

    @JsonIgnore
    private Long id;
    private String name;
    private String email;
    @JsonProperty("birth_date")
    private String aniversario;
    @JsonProperty("user_id")
    private long userId;

    public Profile() {
    }

    public Profile(String name, String email, String aniversario, long userId) {
        this.name = name;
        this.email = email;
        this.aniversario = aniversario;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
