package com.example.clinicalapplication.utils;

import com.example.clinicalapplication.models.Appointment;
import com.example.clinicalapplication.models.Appointment.Status;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ReportGenerator {

    public static void printReportToConsole(List<Appointment> appointments) {
        System.out.println("=== Clinic Report ===");
        System.out.println("Appointments by Physiotherapist:\n");

        for (Appointment a : appointments) {
            System.out.printf("Physio: %-20s | Treatment: %-25s | Patient: %-20s | Time: %-20s | Status: %-10s%n",
                    a.getPhysio().getName(),
                    a.getTreatment().getName(),
                    a.getPatient().getName(),
                    a.getTreatment().getTimeSlot(),
                    a.getStatus());
        }

        System.out.println("\nPhysiotherapists by attended appointment count:\n");

        Map<String, Long> physioCounts = appointments.stream()
                .filter(a -> a.getStatus() == Status.ATTENDED)
                .collect(Collectors.groupingBy(
                        a -> a.getPhysio().getName(),
                        Collectors.counting()
                ));

        physioCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " attended"));
    }

    public static void exportReportToFile(List<Appointment> appointments, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("=== Clinic Report ===\n");
            writer.write("Appointments by Physiotherapist:\n\n");

            for (Appointment a : appointments) {
                writer.write(String.format("Physio: %-20s | Treatment: %-25s | Patient: %-20s | Time: %-20s | Status: %-10s%n",
                        a.getPhysio().getName(),
                        a.getTreatment().getName(),
                        a.getPatient().getName(),
                        a.getTreatment().getTimeSlot(),
                        a.getStatus()));
            }

            writer.write("\nPhysiotherapists by attended appointment count:\n\n");

            Map<String, Long> physioCounts = appointments.stream()
                    .filter(a -> a.getStatus() == Status.ATTENDED)
                    .collect(Collectors.groupingBy(
                            a -> a.getPhysio().getName(),
                            Collectors.counting()
                    ));

            physioCounts.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(e -> {
                        try {
                            writer.write(e.getKey() + ": " + e.getValue() + " attended\n");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

            System.out.println("Report exported to " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing report: " + e.getMessage());
        }
    }
}
