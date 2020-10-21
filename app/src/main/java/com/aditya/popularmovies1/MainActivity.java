package com.aditya.popularmovies1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.ClickListener {

    List<Movie> movieList;
    MovieListAdapter adapter;
    RecyclerView rvMovie;
    boolean popularityTrigger = true;

    String POPULAR = "popular";
    String P_Show = "Popularity";
    String T_Show = "Top Rated";
    String TOP_RATED = "top_rated";

    RequestData requestData;

    TextView mSortedByTv;
    LinearLayout mDataLay, mErrorLay, mLoadingLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            popularityTrigger = savedInstanceState.getBoolean("trigger",true);
        }

        mDataLay = findViewById(R.id.data_lay);
        mErrorLay = findViewById(R.id.error_lay);
        mLoadingLay = findViewById(R.id.loading_lay);
        mSortedByTv = findViewById(R.id.sorted_by_tv);

        rvMovie = findViewById(R.id.rv_movie_list);

        int numberOfColumns;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){ numberOfColumns = 2; }
        else{ numberOfColumns = 3; }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns);
        adapter = new MovieListAdapter(movieList, this);

        rvMovie.setLayoutManager(gridLayoutManager);
        rvMovie.setAdapter(adapter);

        mErrorLay.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { request(); }});
    }

    @Override
    protected void onStart() {
        super.onStart();
        request();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("trigger",popularityTrigger);
        super.onSaveInstanceState(outState);
    }

    public void request(){

        if(!Network.isOnline(this)){
            networkError();
            return;
        }

        if(requestData!=null){requestData.cancel(true);}

        requestData = new RequestData();

        if(popularityTrigger) requestData.execute(Network.buildUrl(POPULAR));
        else requestData.execute(Network.buildUrl(TOP_RATED));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        popularityTrigger= (item.getItemId()==R.id.popularity_menu);
        request();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void posterCLicked(Movie mov) {
        Intent i =new Intent(MainActivity.this, DetailActivity.class);
        i.putExtra("movie", mov);
        startActivity(i);
    }

    public void showLoading(){
        mErrorLay.setVisibility(View.GONE);
        mDataLay.setVisibility(View.GONE);
        mLoadingLay.setVisibility(View.VISIBLE);
    }

    public void showData(String s){
        movieList = JsonUtil.parseMovies(s);
        if(popularityTrigger){mSortedByTv.setText(P_Show);}else{mSortedByTv.setText(T_Show);}
        adapter.refresh(movieList);

        mErrorLay.setVisibility(View.GONE);
        mDataLay.setVisibility(View.VISIBLE);
        mLoadingLay.setVisibility(View.GONE);
    }

    public void networkError(){
        mErrorLay.setVisibility(View.VISIBLE);
        mDataLay.setVisibility(View.GONE);
        mLoadingLay.setVisibility(View.GONE);
    }

    @SuppressLint("StaticFieldLeak")
    class RequestData extends AsyncTask<URL , Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];

            try {
                return Network.getResponseFromHttpUrl(url);
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){ showData(s); }
            else{ networkError(); }
        }
    }
}