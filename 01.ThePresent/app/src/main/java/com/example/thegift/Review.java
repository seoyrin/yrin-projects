package com.example.thegift;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
/*
Parcelable 타입의 객체만 Intent 을 통해 Activity 간 데이터를 넘길 수 있음(gson으로 객체 생성)
 */
public class Review implements Parcelable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("imgurl")
    private String imgurl;
    @SerializedName("title")
    private String title;
    @SerializedName("writer")
    private String writer;
    @SerializedName("star")
    private Float star;
    @SerializedName("review")
    private String review;

    public Review() {}

    public Review(Parcel in) {
        readFromParcel(in);
    }

    public Review(Integer id, String imgurl, String title, String writer, Float star, String review){
        this.id = id;
        this.imgurl = imgurl;
        this.title = title;
        this.writer = writer;
        this.star = star;
        this.review = review;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.imgurl);
        dest.writeString(this.title);
        dest.writeString(this.writer);
        dest.writeFloat(this.star);
        dest.writeString(this.review);
    }

    private void readFromParcel(Parcel in){
        this.id = in.readInt();
        this.imgurl = in.readString();
        this.title = in.readString();
        this.writer = in.readString();
        this.star = in.readFloat();
        this.review = in.readString();
    }

    public static final Creator CREATOR = new Creator() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }

    public String getImgurl(){
        return imgurl;
    }
    public void setImgurl(String imgurl){
        this.imgurl=imgurl;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    public String getWriter(){
        return writer;
    }
    public void setWriter(String writer){
        this.writer=writer;
    }

    public Float getStar(){
        return star;
    }
    public void setStar(Float star){
        this.star=star;
    }

    public String getReview(){
        return review;
    }
    public void setReview(String review){
        this.review=review;
    }
}
