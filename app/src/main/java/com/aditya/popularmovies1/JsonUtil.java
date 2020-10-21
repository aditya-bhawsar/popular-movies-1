package com.aditya.popularmovies1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static List<Movie> parseMovies(String json){
        List<Movie> movieList = new ArrayList<>();

        try {
            JSONObject mainJson = new JSONObject(json);

            JSONArray arr = mainJson.getJSONArray("results");

            movieList.clear();

            for(int i =0; i<arr.length(); i++){

                JSONObject movJson = new JSONObject(arr.get(i).toString());

                int id = movJson.getInt("id");
                double VoteAverage = movJson.getDouble("vote_average");
                double Popularity = movJson.getDouble("popularity");
                String OverView = movJson.getString("overview");
                String Title = movJson.getString("original_title");
                String Poster = "http://image.tmdb.org/t/p/w185/"+movJson.getString("poster_path");
                String Date =movJson.getString("release_date");

                Movie mov = new Movie(Title,OverView,Poster,Date,id,Popularity,VoteAverage) ;

                movieList.add(mov);
            }

        }
        catch (JSONException e){e.printStackTrace();}

        return movieList;
    }

}
