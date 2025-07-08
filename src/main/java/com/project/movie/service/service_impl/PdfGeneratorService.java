package com.project.movie.service.service_impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.project.movie.exceptions.custom_exceptions.UserNotFound;
import com.project.movie.model.Booking;
import com.project.movie.model.Movies;
import com.project.movie.repository.BookingRepo;
import com.project.movie.repository.MoviesRepo;
import com.project.movie.repository.SeatsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService {

    private final BookingRepo bookingRepo;
    private final MoviesRepo moviesRepo;
    private final SeatsRepo seatsRepo;

    private byte[] generateQrCode(String text) throws IOException, WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE,200,200);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"PNG",byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void exportPdf(OutputStream outputStream , Long userId , Long movieId) throws IOException, WriterException {

        Booking booking = bookingRepo.findByUserIdAndMovieId(userId,movieId);
        Movies movies = moviesRepo.findById(movieId).orElseThrow(()-> new UserNotFound("Movie not found"));

        ImageData imageData = ImageDataFactory.create(movies.getPosterImage());
        Image poster = new Image(imageData).scaleToFit(100,100);

        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        PageSize pageSize = new PageSize(300,300);
        Document document = new Document(pdfDocument , pageSize);

        Cell moviePoster = new Cell();
        moviePoster.setBorder(Border.NO_BORDER);
        moviePoster.setVerticalAlignment(VerticalAlignment.MIDDLE);
        moviePoster.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        moviePoster.setTextAlignment(TextAlignment.RIGHT);
        moviePoster.add(poster);

        Table moviePosterAndDetails = new Table(UnitValue.createPercentArray(new float[]{5,5}));
        moviePosterAndDetails.setWidth(UnitValue.createPercentValue(100));
        moviePosterAndDetails.setBorder(Border.NO_BORDER);
        moviePosterAndDetails.addCell(moviePoster);

        Paragraph movieDetails = new Paragraph();
        movieDetails.add(movies.getTitle()+"\n");
        movieDetails.add(movies.getGenre()+"\n");
        movieDetails.add(booking.getShowTime()+"\n");
        movieDetails.add(String.valueOf(booking.getDate())+"\n");

        Cell movieDetailCell = new Cell();
        movieDetailCell.setBorder(Border.NO_BORDER);
        movieDetailCell.setTextAlignment(TextAlignment.RIGHT);
        movieDetailCell.setHorizontalAlignment(HorizontalAlignment.LEFT);
        movieDetailCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        movieDetailCell.add(movieDetails);
        moviePosterAndDetails.addCell(movieDetailCell);
        document.add(moviePosterAndDetails);
        document.add(new Paragraph("\n"));

        Paragraph ticketCount = new Paragraph();
        ticketCount.add("No of Tickets\n");
        ticketCount.add(String.valueOf(booking.getSeatId().size()));

        Paragraph seatNumber = new Paragraph();
        String[] seatNumbers = seatsRepo.findSeatsById(booking.getSeatId())
                .stream()
                .map(String::trim)
                .toArray(String[]::new);
        seatNumber.add("SCREEN-1\n");
        for (String seat : seatNumbers){
            seatNumber.add(seat);
        }

        Cell noOfTicketsCell = new Cell();
        noOfTicketsCell.setBorder(Border.NO_BORDER);
        noOfTicketsCell.setTextAlignment(TextAlignment.LEFT);
        noOfTicketsCell.setHorizontalAlignment(HorizontalAlignment.LEFT);
        noOfTicketsCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        noOfTicketsCell.add(ticketCount);

        Cell seatNumberCell = new Cell();
        seatNumberCell.setBorder(Border.NO_BORDER);
        seatNumberCell.setTextAlignment(TextAlignment.LEFT);
        seatNumberCell.setHorizontalAlignment(HorizontalAlignment.LEFT);
        seatNumberCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        seatNumberCell.add(seatNumber);

        Table seatDetailTable = new Table(UnitValue.createPercentArray(new float[]{5,5}));
        seatDetailTable.setWidth(UnitValue.createPercentValue(100));
        seatDetailTable.setBorder(Border.NO_BORDER);
        seatDetailTable.addCell(noOfTicketsCell);
        seatDetailTable.addCell(seatNumberCell);
        document.add(seatDetailTable);

        String qrData = String.format("Booing Id:%s , Movie:%s",booking.getBookingId(),movies.getTitle());
        byte[] qrImageBytes = generateQrCode(qrData);

        Image qrImage = new Image(ImageDataFactory.create(qrImageBytes));
        qrImage.setAutoScale(true);
        document.add(qrImage);

        document.close();
    }
}
