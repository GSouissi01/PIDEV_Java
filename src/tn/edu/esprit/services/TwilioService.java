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
    private static final String ACCOUNT_SID = "AC662128165fe5b50c681ffca24135e5e9";
    private static final String AUTH_TOKEN = "76c0085956bd49d436d5f1f35a9cc294";
    private static final String TWILIO_PHONE_NUMBER = "+16203494139";

    public static void sendSms(String toPhoneNumber, String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(TWILIO_PHONE_NUMBER), message).create();
    }
}