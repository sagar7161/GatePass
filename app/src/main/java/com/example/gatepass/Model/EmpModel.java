package com.example.gatepass.Model;

public class EmpModel {
    private String reason,status,timestamp;

    public  EmpModel(){}

    public EmpModel(String reason, String status, String timestamp) {
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

