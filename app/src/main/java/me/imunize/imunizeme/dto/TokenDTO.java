package me.imunize.imunizeme.dto;

/**
 * Created by Sr. Décio Montanhani on 24/09/2017.
 */

public class TokenDTO {
    String client_id;

    public TokenDTO(String refreshedToken) {
        client_id = refreshedToken;
    }
}
