package com.samin.again.DTO;

import java.time.LocalDateTime;

public class DonationDTO {
    private Long txnId; // User-provided unique transaction ID
    private double amount;
    private LocalDateTime time;
    private Long fundraisingEventId;
    private Long userId;
   

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

    public Long getFundraisingEventId() {
        return fundraisingEventId;
    }

    public void setFundraisingEventId(Long fundraisingEventId) {
        this.fundraisingEventId = fundraisingEventId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
