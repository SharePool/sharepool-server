package com.sharepool.sharepoolanalytics.domain;

import java.time.LocalDate;

public class AnalyticsEntry {

    private LocalDate creationDate;
    private Double kmSum;
    private Double litersGasSaved;

    public AnalyticsEntry() {
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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
