package com.bubblex.popularmovie.Fragments;

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
public class Favourite extends Fragment {
    public static RecyclerView MovieRV;
    GridLayoutManager gridLayoutManager;
    public static MovieAdpter movieAdpter;
    List<Movie> MoviesList = new ArrayList<>();
    DataBaseHelper helper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favourite_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        helper = new DataBaseHelper(getActivity());
        MoviesList = helper.GetAllMovies();
        MovieRV = (RecyclerView) getActivity().findViewById(R.id.movie_rv_fav);
        MovieRV.hasFixedSize();
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        MovieRV.setLayoutManager(gridLayoutManager);
        movieAdpter = new MovieAdpter(MoviesList, getActivity());
        MovieRV.setAdapter(movieAdpter);
    }

}