package com.shareppol.sharepoolanalytics.domain;

import java.time.LocalDate;

public class AnalyticsEntry {

    private LocalDate date;
    private Double kmSum;
    private Double litersGasSaved;

    public AnalyticsEntry() {
    }

    public AnalyticsEntry(LocalDate date, Double kmSum, Double litersGasSaved) {
        this.date = date;
        this.kmSum = kmSum;
        this.litersGasSaved = litersGasSaved;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getKmSum() {
        return kmSum;
    }

    public void setKmSum(Double kmSum) {
        this.kmSum = kmSum;
    }

    public Double getLitersGasSaved() {
        return litersGasSaved;
    }

    public void setLitersGasSaved(Double litersGasSaved) {
        this.litersGasSaved = litersGasSaved;
    }
}
