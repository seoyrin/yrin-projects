package com.example.thegift;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    //on_insert
    @FormUrlEncoded
    @POST("on_insert.php")
    Call<ImageClass> uploadImage(@Field("name") String name,
                                 @Field("image") String image,
                                 @Field("category") String category,
                                 @Field("tag") String tag,
                                 @Field("introduce") String introduce,
                                 @Field("price") String price,
                                 @Field("site") String site,
                                 @Field("sns") String sns);

    //off_insert
    @FormUrlEncoded
    @POST("off_insert.php")
    Call<ImageClassOff> uploadImageOff(@Field("name") String name,
                                 @Field("image") String image,
                                 @Field("category") String category,
                                 @Field("tag") String tag,
                                 @Field("introduce") String introduce,
                                 @Field("telnumber") String telnumber,
                                 @Field("address") String address,
                                 @Field("opentime") String opentime,
                                 @Field("snspage") String snspage,
                                 @Field("link") String link);

    //won_insert
    @FormUrlEncoded
    @POST("won_insert.php")
    Call<ImageClassWon> uploadImageWon(@Field("name") String name,
                                       @Field("image") String image,
                                       @Field("category") String category,
                                       @Field("price") String price,
                                       @Field("link") String link);

    //board_insert
    @FormUrlEncoded
    @POST("board_insert.php")
    Call<ImageClassBoard> uploadImageBoard(@Field("title") String title,
                                       @Field("image") String image,
                                       @Field("writer") String writer,
                                       @Field("pw") String pw,
                                       @Field("star") Float star,
                                       @Field("review") String review,
                                       @Field("today") String today);

}
