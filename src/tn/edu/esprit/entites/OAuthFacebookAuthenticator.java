/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entites;

/**
 *
 * @author SOUISSI
 */

public class OAuthFacebookAuthenticator extends OAuthAuthenticator{

    private String FACEBOOK_fieldsString = "name,email";

    public OAuthFacebookAuthenticator(String clientID, String redirectUri, String clientSecret, String apiFields) {
        super(clientID, redirectUri, clientSecret);
        FACEBOOK_fieldsString = apiFields;
    }

    @Override
    String getWebUrl() {
        return "https://www.facebook.com/dialog/oauth?client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri();
    }

    @Override
    String getApiTokenUrl() {
        return "https://graph.facebook.com/me?fields=" + FACEBOOK_fieldsString + "&access_token=" + getAccessToken();
    }

    @Override
    String getApiAccessUrl() {
        return "https://graph.facebook.com/oauth/access_token";
    }

    @Override
    String getApiAccessParams() {
        return  "client_id=" + getClientID() + "&redirect_uri=" + getRedirectUri() + "&client_secret=" + getClientSecret() + "&code=" + getAccessCode();
    }


}