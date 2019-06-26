package com.sharepool.sharepoolanalytics.domain;

public class AnalyticsEntry {

    private Long creationTimestamp;
    private Double kmSum;
    private Double litersGasSaved;

    public AnalyticsEntry() {
    }

    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
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
