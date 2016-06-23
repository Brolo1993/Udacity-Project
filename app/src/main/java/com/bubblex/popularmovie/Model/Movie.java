package com.bubblex.popularmovie.Model;

/**
 * Created by Amr ElBrolosy on 29/11/15.
 */
public class Movie {
    private String Title;
    private String ImgUrl;
    private String AvgRate;
    private String Overview;
    private String ReleaseDate;
    private boolean isFav = false;
    private int pos;
    private String ID;

    public Movie() {
        isFav = false;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public boolean isFav() {
        return isFav;
    }

    public void setIsFav(boolean isFav) {
        this.isFav = isFav;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getAvgRate() {
        return AvgRate;
    }

    public int getPos() {
        return pos;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setAvgRate(String avgRate) {
        AvgRate = avgRate;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }
}
