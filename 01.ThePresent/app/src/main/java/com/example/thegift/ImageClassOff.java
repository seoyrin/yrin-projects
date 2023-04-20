package com.example.thegift;

import com.google.gson.annotations.SerializedName;

public class ImageClassOff {
    //off_insert
    //db칼럼 명
    @SerializedName("image") //image
    private String image;    //Image
    @SerializedName("name")
    private String name;
    @SerializedName("category")
    private String category;
    @SerializedName("tag")
    private String tag;
    @SerializedName("introduce")
    private String introduce;
    @SerializedName("telnumber")
    private String telnumber;
    @SerializedName("address")
    private String address;
    @SerializedName("opentime")
    private String opentime;
    @SerializedName("snspage")
    private String snspage;
    @SerializedName("link")
    private String link;

    @SerializedName("response")
    private String response;

    public String getResponse(){
        return response;
    }
}
