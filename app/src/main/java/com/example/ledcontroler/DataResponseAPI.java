package com.example.ledcontroler;

import com.google.gson.annotations.SerializedName;

public class DataResponseAPI {

    @SerializedName("gpio")
    private String gpio;
    @SerializedName("status")
    private String status;

    public DataResponseAPI(String gpio, String status) {
        this.gpio = gpio;
        this.status = status;
    }

}
