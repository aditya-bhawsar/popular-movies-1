package com.aditya.popularmovies1;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String OriginalTitle, Overview, Poster, ReleaseDate;
    private int Id ;

    private double Popularity, VoteAverage;

    public Movie(String originalTitle, String overview, String poster, String releaseDate, int id, double popularity, double voteAverage) {
        OriginalTitle = originalTitle;
        Overview = overview;
        Poster = poster;
        ReleaseDate = releaseDate;
        Id = id;
        Popularity = popularity;
        VoteAverage = voteAverage;
    }

    protected Movie(Parcel in) {
        OriginalTitle = in.readString();
        Overview = in.readString();
        Poster = in.readString();
        ReleaseDate = in.readString();
        Id = in.readInt();
        Popularity = in.readDouble();
        VoteAverage = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public double getPopularity() {
        return Popularity;
    }

    public void setPopularity(double popularity) {
        Popularity = popularity;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        OriginalTitle = originalTitle;
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

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getVoteAverage() {
        return VoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        VoteAverage = voteAverage;
    }

    public String getOverview() {
        return Overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(OriginalTitle);
        dest.writeString(Overview);
        dest.writeString(Poster);
        dest.writeString(ReleaseDate);
        dest.writeInt(Id);
        dest.writeDouble(Popularity);
        dest.writeDouble(VoteAverage);
    }
}