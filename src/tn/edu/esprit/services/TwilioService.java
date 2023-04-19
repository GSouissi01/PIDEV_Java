/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;

/**
 *
 * @author SOUISSI
 */
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioService {
    private static final String ACCOUNT_SID = "AC3248010f0b287ad30a9ebc74aed2cd74";
    private static final String AUTH_TOKEN = "33951ab6c85d65403621282b5232b269";
    private static final String TWILIO_PHONE_NUMBER = "+16205518112";

    public static void sendSms(String toPhoneNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(TWILIO_PHONE_NUMBER), message).create();
    }
}