/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entites;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author SOUISSI
 */
public class Facebook
{
  
        public tn.edu.esprit.entites.User authentificationfb()
        {
       String domain  ="http%3A%2F%2Flocalhost%2F";
        String appId="1373179750172872";
        
        String authUrl="https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+appId+"&redirect_uri="+domain+"&scope=email,public_profile";
                
                
                
         System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
         
         WebDriver driver =new ChromeDriver();
         driver.get(authUrl);
         String accessToken="EAATg5lM4yMgBAGeY4eqTuxT0So8UkwaDLYTD2EeZCIeujr0wYAsojmbo49FsqTeGufu9tE6sh8HEzrP2qhfgebdqAj49pb6nlZC0RxLOMsZBbDZATinJZBYFBCJmukjyZA7iyYwo0s7f5uRKABOqEWE76woD8wCPJPvAE85AMT7JHSqOPkfupuLqeNytkWL05NrinJGJ6QtfTrqw1NNqbmbZAkdCZB1Yid4xRZCmwQKW66maTE9fIVR1P";
         
         while (true){
             if(!driver.getCurrentUrl().contains("facebook.com")){
                 String url=driver.getCurrentUrl();
                 //accessToken=url.replaceAll(".*#access_token=(.+)&.*", "$1");
                 driver.quit();
                 
                FacebookClient fbClient= new DefaultFacebookClient(accessToken, Version.UNVERSIONED);
                User me= fbClient.fetchObject("me",User.class);
               String email ="wissem22111@gmail.com";
        //String email=me.getEmail();
        //System.out.println(email+"aaaferef");
        
        String name = me.getName();
        System.err.println(name);
        String password = "";
         tn.edu.esprit.entites.User u = new tn.edu.esprit.entites.User(name, password,email);  
          return u;      
             }
           
            
             
         }
        }
}