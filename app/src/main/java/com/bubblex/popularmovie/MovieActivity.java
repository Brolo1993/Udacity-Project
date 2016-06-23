package com.bubblex.popularmovie;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bubblex.popularmovie.Adpter.DataBaseHelper;
import com.bubblex.popularmovie.Adpter.ReviewAdpter;
import com.bubblex.popularmovie.Adpter.TrailerAdpter;
import com.bubblex.popularmovie.Fragments.Favourite;
import com.bubblex.popularmovie.Model.Movie;
import com.bubblex.popularmovie.Model.Trailer;
import com.bubblex.popularmovie.app.appcontrol;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {
    public static int pos;
    TextView Rate, Overview, Date;
    ImageView Poster;
    ImageButton FavButton;
    DataBaseHelper helper;
    public static Movie movie = new Movie();
    RecyclerView TrailerView, ReviewView;
    TrailerAdpter trailerAdpter;
    ReviewAdpter reviewAdpter;
    List<Trailer> trailers = new ArrayList<>();
    List<Trailer> reviews = new ArrayList<>();
    String TrailerURL1 = "http://api.themoviedb.org/3/movie/";
    String TrailerURL2 = "/videos?api_key=0de86d2d856ff06e1fbf1e83861ac32a";
    String TrailerURL3 = "/reviews?api_key=0de86d2d856ff06e1fbf1e83861ac32a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView movieTitle = (TextView) findViewById(R.id.movie_title);
        movieTitle.setText(movie.getTitle());
        toolbar.setTitle(movie.getTitle());
        helper = new DataBaseHelper(this);
        Poster = (ImageView) findViewById(R.id.backdrop);
        Rate = (TextView) findViewById(R.id.movie_rate);
        Overview = (TextView) findViewById(R.id.movie_overview);
        Date = (TextView) findViewById(R.id.movie_date);
        FavButton = (ImageButton) findViewById(R.id.btn_fav);
        TrailerView = (RecyclerView) findViewById(R.id.trailer_rv);
        ReviewView = (RecyclerView) findViewById(R.id.review_rv);
        if (helper.ISExistt(movie.getTitle())) {
            FavButton.setImageResource(R.drawable.yellowc);

        } else {
            FavButton.setImageResource(R.drawable.yellow);
        }
        setSupportActionBar(toolbar);


        FavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (helper.ISExistt(movie.getTitle())) {
                    FavButton.setImageResource(R.drawable.yellow);
                    helper.deleterow(movie.getTitle());
                    ArrayList<Movie> movies = helper.GetAllMovies();
                    Favourite.movieAdpter.Modify(movies);
                } else {
                    FavButton.setImageResource(R.drawable.yellowc);
                    helper.insertMovie(movie.getID(),movie.getTitle(), movie.getOverview(), movie.getImgUrl(), movie.getAvgRate(), movie.getReleaseDate(), "" + pos);
                    ArrayList<Movie> movies = helper.GetAllMovies();
                    Favourite.movieAdpter.Modify(movies);
                }
            }
        });


        Rate.setText("Rate : " + movie.getAvgRate() + " / 10");
        Date.setText("Date Release : " + movie.getReleaseDate());
        Overview.setText("Over View : " + movie.getOverview());
        Picasso.with(this).load(movie.getImgUrl()).into(Poster);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        LinearLayoutManager llmr = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        TrailerView.setLayoutManager(llm);
        ReviewView.setLayoutManager(llmr);
        TrailerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(this).size(3).build());
        ReviewView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).size(1).build());
        trailerAdpter = new TrailerAdpter(trailers, this);
        TrailerView.setAdapter(trailerAdpter);
        reviewAdpter = new ReviewAdpter(reviews, MovieActivity.this);
        ReviewView.setAdapter(reviewAdpter);
        IntializeTrailer(TrailerURL1 + movie.getID() + TrailerURL2);
        IntializeReview(TrailerURL1 + movie.getID() + TrailerURL3);


    }

    private void IntializeReview(String trailerURL) {
        reviews = new ArrayList<>();

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                trailerURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("g", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Trailer trailer = new Trailer();
                        trailer.setName(jsonObject.getString("author"));
                        trailer.setKey(jsonObject.getString("content"));
                        reviewAdpter.insert(trailer, reviewAdpter.getItemCount());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener()

        {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("g", "Error: " + error.getMessage());
//               wheel.setVisibility(View.GONE);


            }
        });
        appcontrol.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void IntializeTrailer(final String trailerURL) {
        trailers = new ArrayList<>();

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                trailerURL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("g", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Trailer trailer = new Trailer();
                        trailer.setKey(jsonObject.getString("key"));
                        trailer.setName(jsonObject.getString("name"));
                        trailers.add(trailer);
                    }
                    trailerAdpter = new TrailerAdpter(trailers, getApplicationContext());
                    TrailerView.setAdapter(trailerAdpter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener()

        {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("g", "Error: " + error.getMessage());
//               wheel.setVisibility(View.GONE);


            }
        });
        appcontrol.getInstance().addToRequestQueue(jsonObjReq);
    }
}
