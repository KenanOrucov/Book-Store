package com.example.ingress.service.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtToken {
    private String idToken;

    public JwtToken(String idToken) {
        this.idToken = idToken;
    }

    @JsonProperty("id_token")
    String getIdToken(){return idToken;}

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
