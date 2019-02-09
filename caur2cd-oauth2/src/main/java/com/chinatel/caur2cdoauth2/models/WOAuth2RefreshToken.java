package com.chinatel.caur2cdoauth2.models;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;

public class WOAuth2RefreshToken implements OAuth2RefreshToken {

    private Long id;

    private String refreshToken;

    @Override
    public String getValue() {
        return null;
    }
}
