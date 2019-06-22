package com.shareppol.sharepoolanalytics.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class AnalyticsMessage implements Serializable {

    @Id
    private Long expenseId;
    private Long tourId;
    private Double kilometers;
    private Double sumGasConsumption;
    private LocalDateTime date;

    public AnalyticsMessage() {
    }

    public AnalyticsMessage(Long expenseId, Long tourId, Double kilometers, Double sumGasConsumption, LocalDateTime date) {
        this.expenseId = expenseId;
        this.tourId = tourId;
        this.kilometers = kilometers;
        this.sumGasConsumption = sumGasConsumption;
        this.date = date;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Double getKilometers() {
        return kilometers;
    }

    public void setKilometers(Double kilometers) {
        this.kilometers = kilometers;
    }

    public Double getSumGasConsumption() {
        return sumGasConsumption;
    }

    public void setSumGasConsumption(Double sumGasConsumption) {
        this.sumGasConsumption = sumGasConsumption;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AnalyticsMessage{" +
                "expenseId=" + expenseId +
                ", tourId=" + tourId +
                ", kilometers=" + kilometers +
                ", sumGasConsumption=" + sumGasConsumption +
                ", date=" + date +
                '}';
    }
}
