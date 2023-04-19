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
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

public class FacebookAppRequest {

  public static void main(String[] args) {

    String accessToken = "your_access_token_here";

    FacebookClient client = new DefaultFacebookClient(accessToken);
    FacebookType response = client.publish("me/apprequests", FacebookType.class,
      Parameter.with("message", "Test app request"),
      Parameter.with("data", "custom data"),
      Parameter.with("redirect_uri", "https://graph.facebook.com/v12.0/me/apprequests"));

    System.out.println("Request ID: " + response.getId());
  }
}



