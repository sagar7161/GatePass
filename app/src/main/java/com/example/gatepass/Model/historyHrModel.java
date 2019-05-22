package com.example.gatepass.Model;

public class historyHrModel  {

    private String date,id,name,reason,status,timestamp,empImage,leaveId;


    public historyHrModel(){}

    public historyHrModel( String id, String name, String reason, String status, String timestamp,String empImage,String leaveId) {
        this.id = id;
        this.name = name;
        this.reason = reason;
        this.status = status;
        this.timestamp = timestamp;
        this.empImage = empImage;
        this.leaveId = leaveId;
    }

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }

    public String getEmpImage() {
        return empImage;
    }

    public void setEmpImage(String empImage) {
        this.empImage = empImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
