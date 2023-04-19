/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.tests;

import static tn.edu.esprit.services.TwilioService.sendSms;
import tn.edu.esprit.utils.Database;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.ProtocolVersion;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ssl.SSLContexts;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonMerge;
/**
 *
 * @author ghada
 */
public class MainClass {
    public static void main(String[] args){
        Database cnx=new Database();
        sendSms("+216 97397598","banned");
        
    }
}
