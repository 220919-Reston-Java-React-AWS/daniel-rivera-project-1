package com.revature.model;

import java.util.Objects;

public class AppReimbursementTicket {
    private int id;
    private double amount;
    private String description;
    private int ticketStatus;
    private int employeeId;
    private int managerId;

    public AppReimbursementTicket(){
    }

    public AppReimbursementTicket(int id, double amount, String description, int ticketStatus, int employeeId, int managerId) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.ticketStatus = ticketStatus;
        this.employeeId = employeeId;
        this.managerId = managerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(int ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "AppReimbursementTicket{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", ticketStatus=" + ticketStatus +
                ", employeeId=" + employeeId +
                ", managerId=" + managerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppReimbursementTicket that = (AppReimbursementTicket) o;
        return id == that.id && Double.compare(that.amount, amount) == 0 && ticketStatus == that.ticketStatus && employeeId == that.employeeId && managerId == that.managerId && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, description, ticketStatus, employeeId, managerId);
    }
}
