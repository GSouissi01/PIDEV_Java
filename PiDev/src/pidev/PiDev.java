///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package pidev;
//
//import entity.Client;
//import entity.Credit;
//import java.security.NoSuchAlgorithmException;
//import java.sql.SQLException;
//import service.ClientService;
//import service.CreditService;
//import java.sql.Date;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//
///**
// *
// * @author nourdine
// */
//public class PiDev {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, ParseException {
// ////// Inject ClientService & CreditService
//        ClientService clientService = new ClientService();
//        CreditService creditService = new CreditService();
//        
//        
//        //// Instance Client
//        Client client1 = new Client(2,"Molka","Ben smida","molka.bensmida@esprit.tn","98456123");
//        Client client2 = new Client("Mohamed","Ben Mohamed","Mohamed.Mohamed@esprit.tn","98123478");
//        Client client3 = new Client("mahmoud","Ben mahmoud","mahmoud.mahmoud@esprit.tn","22456789");
//        Client client4 = new Client("mahdi","ben youssef","mahdi.youssef@esprit.tn","50456789");
//
//        ///// Instance Credit 
//         SimpleDateFormat format = new SimpleDateFormat( "MM/dd/yyyy" );
//        java.util.Date myDate = format.parse( "10/10/2009" );
//       Date datee = new Date( myDate.getTime() );
//       
//        Credit credit1 = new Credit(1,3,43,datee);
//       Credit credit2 = new Credit(3,1045,datee);
//       Credit credit3 = new Credit(3,78,datee);
//       Credit credit4 = new Credit(3,700,datee);
//
//
//        //////////// Add Client
//       // clientService.ajouterClient(client2);
//        
//        ////////// Delete Client
//       // clientService.SupprimerClient(client1);
//
//        /////////// Get All Clients
//       // System.out.println(clientService.AfficherClient());
//
//        /////////// Modifier Cliennt
//
//       //  clientService.update(3,client4);
//     
// ///////////// Add Credit
// //creditService.ajouterCredit(credit1);
//    
// ///////////// Supprimer Credit
// //creditService.SupprimerCredit(credit1);
// 
// ///////////// Get All Credit
////System.out.println(creditService.AfficherCredit());
//
//////////////// Modifier Credit
// //creditService.update(4,credit3);
//
//    
//    }
//    
//}
