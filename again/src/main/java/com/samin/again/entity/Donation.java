package com.samin.again.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Donation {
    @Id
    private Long txnId; // User-provided transaction ID

    private double amount;

    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "fundraising_event_id")
    private FundraisingEvent fundraisingEvent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public FundraisingEvent getFundraisingEvent() {
        return fundraisingEvent;
    }

    public void setFundraisingEvent(FundraisingEvent fundraisingEvent) {
        this.fundraisingEvent = fundraisingEvent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
