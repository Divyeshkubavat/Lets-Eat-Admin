package com.example.letseatadmin.Models;

public class Delivery_history_Item {

    String Order_ID,Status;

    public Delivery_history_Item(String order_ID, String status) {
        Order_ID = order_ID;
        Status = status;
    }

    public String getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(String order_ID) {
        Order_ID = order_ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
