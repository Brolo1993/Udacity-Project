package com.bubblex.popularmovie.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bubblex.popularmovie.Adpter.DataBaseHelper;
import com.bubblex.popularmovie.Adpter.MovieAdpter;
import com.bubblex.popularmovie.Model.Movie;
import com.bubblex.popularmovie.R;
import com.bubblex.popularmovie.app.appcontrol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr ElBrolosy on 04/12/15.
 */
public class HomeFragment extends Fragment {
    public static RecyclerView MovieRV;
    DataBaseHelper helper;
    GridLayoutManager gridLayoutManager;
    public static MovieAdpter movieAdpter;
    public static List<String> FavMovies = new ArrayList<>();
    public static List<Movie> MoviesList = new ArrayList<>();
    public static Context context;
    String URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=0de86d2d856ff06e1fbf1e83861ac32a";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        helper = new DataBaseHelper(getActivity());
        FavMovies = helper.GetAllMoviesName();
        IntialMovies(URL);
        MovieRV = (RecyclerView) getActivity().findViewById(R.id.movie_rv);
        MovieRV.hasFixedSize();
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        MovieRV.setLayoutManager(gridLayoutManager);
    }

    public static void IntialMovies(String URL) {
        MoviesList = new ArrayList<>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("g", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setTitle(jsonObject.getString("title"));
                        if (FavMovies.contains(movie.getTitle())) {
                            movie.setIsFav(true);
                        }
                        movie.setID(jsonObject.getString("id"));
                        movie.setAvgRate(jsonObject.getString("vote_average"));
                        movie.setOverview(jsonObject.getString("overview"));
                        movie.setReleaseDate(jsonObject.getString("release_date"));
                        movie.setImgUrl(" http://image.tmdb.org/t/p/w185" + jsonObject.getString("poster_path"));
                        MoviesList.add(movie);
                    }
                    movieAdpter = new MovieAdpter(MoviesList, context);
                    MovieRV.setAdapter(movieAdpter);

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