package com.example.booking.controller;

import com.example.booking.entity.BookingStatistic;
import com.example.booking.entity.UserStatistic;
import com.example.booking.service.StatisticsService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Value("${app.statistics.fileNameUserStat}")
    private String fileNameUserStat;

    @Value("${app.statistics.fileNameBookingStat}")
    private String fileNameBookingStat;

    @GetMapping("/get-users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportUsers(HttpServletResponse rs) throws IOException,
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException {

        rs.setContentType("text/csv");
        rs.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileNameUserStat + "");

        StatefulBeanToCsv<UserStatistic> writer =
                new StatefulBeanToCsvBuilder<UserStatistic>(rs.getWriter())
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(true)
                        .build();

        writer.write(statisticsService.getUserStat());
    }

    @GetMapping("/get-bookings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportBookings(HttpServletResponse rs) throws IOException,
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException {

        rs.setContentType("text/csv");
        rs.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileNameBookingStat + "");

        StatefulBeanToCsv<BookingStatistic> writer =
                new StatefulBeanToCsvBuilder<BookingStatistic>(rs.getWriter())
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(true)
                        .build();

        writer.write(statisticsService.getBookingStat());

    }

}
