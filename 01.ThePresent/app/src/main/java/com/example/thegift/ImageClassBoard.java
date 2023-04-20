package com.example.thegift;

import com.google.gson.annotations.SerializedName;

public class ImageClassBoard {
    //board_insert
    @SerializedName("image") //image
    private String image;    //Image
    @SerializedName("title")
    private String title;
    @SerializedName("writer")
    private String writer;
    @SerializedName("pw")
    private String pw;
    @SerializedName("star")
    private Float star;
    @SerializedName("review")
    private String review;
    @SerializedName("today")
    private String today;

    @SerializedName("response")
    private String response;

    public String getResponse(){
        return response;
    }
}
