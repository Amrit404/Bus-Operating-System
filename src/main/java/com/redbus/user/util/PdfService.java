package com.redbus.user.util;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.redbus.user.payload.BookingDetailsDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;


@Service
public class PdfService {

    public byte[] generatePdf(BookingDetailsDto bookingDetailsDto) {
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer)) {

            try (Document document = new Document(pdfDocument)) {
                document.add(new Paragraph("Booking Details"));
                document.add(new Paragraph("Booking ID: " + bookingDetailsDto.getBookingId()));
                document.add(new Paragraph("Bus Company: " + bookingDetailsDto.getBusCompany()));
                document.add(new Paragraph("From: " + bookingDetailsDto.getFrom()));
                document.add(new Paragraph("To: " + bookingDetailsDto.getTo()));
                document.add(new Paragraph("First Name: " + bookingDetailsDto.getFirstName()));
                document.add(new Paragraph("Last Name: " + bookingDetailsDto.getLastName()));
                document.add(new Paragraph("Email: " + bookingDetailsDto.getEmail()));
                document.add(new Paragraph("Mobile: " + bookingDetailsDto.getMobile()));
                document.add(new Paragraph("Price: " + bookingDetailsDto.getPrice()));
            }
            return outputStream.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }


}

