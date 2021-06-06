package com.example.thegift;

import com.google.gson.annotations.SerializedName;

public class ImageClass {
    //on_insert
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
    @SerializedName("price")
    private String price;
    @SerializedName("site")
    private String site;
    @SerializedName("sns")
    private String sns;

    @SerializedName("response")
    private String response;

    public String getResponse(){
        return response;
    }
}
