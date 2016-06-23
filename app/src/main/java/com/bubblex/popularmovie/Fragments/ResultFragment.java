package com.bubblex.popularmovie.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.bubblex.popularmovie.Model.Movie;
import com.bubblex.popularmovie.Model.Trailer;
import com.bubblex.popularmovie.R;
import com.bubblex.popularmovie.app.appcontrol;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr ElBrolosy on 26/12/2015.
 */
public class ResultFragment extends Fragment {
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_movie, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        toolbar.setTitle(movie.getTitle());
        TextView movieTitle = (TextView) getActivity().findViewById(R.id.movie_title);
        movieTitle.setText(movie.getTitle());
        helper = new DataBaseHelper(getActivity());
        Poster = (ImageView) getActivity().findViewById(R.id.backdrop);
        Rate = (TextView) getActivity().findViewById(R.id.movie_rate);
        Overview = (TextView) getActivity().findViewById(R.id.movie_overview);
        Date = (TextView) getActivity().findViewById(R.id.movie_date);
        FavButton = (ImageButton) getActivity().findViewById(R.id.btn_fav);
        TrailerView = (RecyclerView) getActivity().findViewById(R.id.trailer_rv);
        ReviewView = (RecyclerView) getActivity().findViewById(R.id.review_rv);
        if (helper.ISExistt(movie.getTitle())) {
            FavButton.setImageResource(R.drawable.yellowc);

        } else {
            FavButton.setImageResource(R.drawable.yellow);
        }


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
        Picasso.with(getActivity()).load(movie.getImgUrl()).into(Poster);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        LinearLayoutManager llmr = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        TrailerView.setLayoutManager(llm);
        ReviewView.setLayoutManager(llmr);
        TrailerView.addItemDecoration(new VerticalDividerItemDecoration.Builder(getActivity()).size(3).build());
        ReviewView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).size(1).build());
        trailerAdpter = new TrailerAdpter(trailers, getActivity());
        TrailerView.setAdapter(trailerAdpter);
        reviewAdpter = new ReviewAdpter(reviews, getActivity());
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
                    trailerAdpter = new TrailerAdpter(trailers, getActivity());
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
