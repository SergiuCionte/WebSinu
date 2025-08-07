package com.PSapplication.PSapplication.Generator;

import com.PSapplication.PSapplication.DTOs.UserDetailsDTO;
import com.PSapplication.PSapplication.Entities.NotaExamen;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.File;
import java.io.IOException;

public class PdfFileGenerator implements FileGenerator {
    @Override
    public File generateFile(UserDetailsDTO user) {
        File pdfFile = null;
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            Standard14Fonts.FontName font_name_3v = Standard14Fonts.getMappedFontName("HELVETICA_BOLD");
            PDFont pdfFont = new PDType1Font(font_name_3v.HELVETICA_BOLD);
            contentStream.setFont(pdfFont, 12);
            contentStream.newLineAtOffset(100, 700);

            // Write user information to the document
            contentStream.showText(user.getUsername());
            contentStream.newLineAtOffset(0, -20);
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText(user.getEmail());
            contentStream.newLineAtOffset(0, -20);

            for (NotaExamen nota : user.getNotaExamenList()) {
                contentStream.showText("- " + nota.getCalificativ_examen().toString());
                contentStream.newLineAtOffset(0, -20);
            }

            contentStream.endText();
            contentStream.close();

            // Save the document to a temporary file
            pdfFile = File.createTempFile("user_info", ".pdf");
            document.save(pdfFile);
            System.out.println("PDF file generated successfully.");
        } catch (IOException e) {
            System.err.println("Error generating PDF file: " + e.getMessage());
        }
        return pdfFile;
    }
}
