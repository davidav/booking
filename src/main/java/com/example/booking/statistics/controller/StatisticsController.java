package com.example.booking.statistics.controller;

import com.example.booking.statistics.model.AllStatistics;
import com.example.booking.statistics.model.BookingStatistic;
import com.example.booking.statistics.model.UserStatistic;
import com.example.booking.statistics.service.StatisticsServiceImpl;
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
    private final StatisticsServiceImpl statisticsService;

    @Value("${app.statistics.fileNameUserStat}")
    private String fileNameUserStat;

    @Value("${app.statistics.fileNameBookingStat}")
    private String fileNameBookingStat;

    @Value("${app.statistics.fileNameAllStat}")
    private String fileNameAllStat;

    @GetMapping("/get-users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportUsers(HttpServletResponse response) throws IOException,
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException {

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileNameUserStat + "");

        StatefulBeanToCsv<UserStatistic> writer =
                new StatefulBeanToCsvBuilder<UserStatistic>(response.getWriter())
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(true)
                        .build();

        writer.write(statisticsService.getUserStat());
    }

    @GetMapping("/get-bookings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportBookings(HttpServletResponse response) throws IOException,
            CsvRequiredFieldEmptyException,
            CsvDataTypeMismatchException {

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileNameBookingStat + "");

        StatefulBeanToCsv<BookingStatistic> writer =
                new StatefulBeanToCsvBuilder<BookingStatistic>(response.getWriter())
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(true)
                        .build();

        writer.write(statisticsService.getBookingStat());

    }

        @GetMapping("/get-all")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public void exportAll(HttpServletResponse response) throws IOException,
                CsvRequiredFieldEmptyException,
                CsvDataTypeMismatchException {

            response.setContentType("text/csv");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileNameAllStat + "");

            StatefulBeanToCsv<AllStatistics> writer =
                    new StatefulBeanToCsvBuilder<AllStatistics>(response.getWriter())
                            .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                            .withOrderedResults(true)
                            .build();

            writer.write(statisticsService.getAllStat());
        }

}
