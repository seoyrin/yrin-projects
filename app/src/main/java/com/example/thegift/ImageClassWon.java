package com.example.thegift;

import com.google.gson.annotations.SerializedName;

public class ImageClassWon {
    //won_insert
    //db칼럼 명
    @SerializedName("image") //image
    private String image;    //Image
    @SerializedName("name")
    private String name;
    @SerializedName("category")
    private String category;
    @SerializedName("price")
    private String price;
    @SerializedName("link")
    private String link;

    @SerializedName("response")
    private String response;

    public String getResponse(){
        return response;
    }
}
