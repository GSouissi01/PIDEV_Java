/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import tn.edu.esprit.entities.Produit;
import tn.edu.esprit.services.ServiceProduct;

/**
 *
 * @author azizbramli
 */

public class PDFGenerator {
    
  public void generatePdf(String filename, List<Produit> produits) throws FileNotFoundException, DocumentException, BadElementException, IOException, InterruptedException, SQLException {
    Document document = new Document();
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename + ".pdf"));
    document.open();
    
    // add your logo to the PDF document
    Image logo = Image.getInstance("/C:/xampp/htdocs/produit_final/produit/public/images/logo/123.png" );
    logo.scaleToFit(100, 100); // adjust the logo size as needed
    document.add(logo);

    document.add(new Paragraph("Date: " + LocalDateTime.now()));
    document.add(new Paragraph("Liste des produits"));
    
document.add(new Chunk(new LineSeparator()));
    for (Produit p : produits) {
        document.add(new Paragraph("Le produit: " + p.getLibelle()));
        document.add(new Paragraph("Stock: " + p.getStock()));
        document.add(new Paragraph("Prix: " + p.getPrix()));
        document.add(new Paragraph("Date d'expiration: " + p.getDateexpiration()));
        
        // add your product image to the PDF document
        Image productImage = Image.getInstance("C:\\xampp\\htdocs\\produit_final\\produit\\public\\images\\product\\" + p.getImageFile());
        productImage.scaleToFit(100, 100); // adjust the image size as needed
        document.add(productImage);
       
        document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------------- "));
    }
    document.add(new Paragraph("COMPTAMERVEILLE"));

    document.close();
    writer.close();
    
    if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().open(new File(filename + ".pdf"));
    }
}


}


