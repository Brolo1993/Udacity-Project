package com.bubblex.popularmovie.Adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bubblex.popularmovie.Fragments.ResultFragment;
import com.bubblex.popularmovie.HomeAcivity;
import com.bubblex.popularmovie.Model.Movie;
import com.bubblex.popularmovie.MovieActivity;
import com.bubblex.popularmovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr ElBrolosy on 29/11/15.
 */
public class MovieAdpter extends RecyclerView.Adapter<MovieAdpter.PersonViewHolder> {
    List<Movie> Movies;
    Context context;


    public MovieAdpter(List<Movie> Movies, Context context) {
        this.Movies = Movies;
        this.context = context;
    }


    public class PersonViewHolder extends RecyclerView.ViewHolder {
        ImageView MovieImg;

        PersonViewHolder(View itemView) {
            super(itemView);
            MovieImg = (ImageView) itemView.findViewById(R.id.movie_adpter_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context.getResources().getBoolean(R.bool.tab)) {
                        ResultFragment.movie = Movies.get(getPosition());
                        HomeAcivity.fragmentmovie = new ResultFragment();
                        HomeAcivity.fragmentManager.beginTransaction()
                                .replace(R.id.resultFragment, HomeAcivity.fragmentmovie).commit();

                    } else {
                        MovieActivity.movie = Movies.get(getPosition());
                        MovieActivity.pos = getPosition();
                        Intent intent = new Intent(context, MovieActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_adpter_layout, viewGroup, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {

        Picasso.with(context).load(Movies.get(i).getImgUrl()).placeholder(R.drawable.reload).into(personViewHolder.MovieImg);
    }

    @Override
    public int getItemCount() {
        return Movies.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(Movie movie, int pos) {
        Movies.add(pos, movie);
        notifyDataSetChanged();
        notifyItemChanged(pos);
    }

    public void Delete(int Pos) {
        Movies.remove(Pos);
        notifyItemRemoved(Pos);
    }

    public void Modify(ArrayList<Movie> movies) {
        this.Movies = movies;
        notifyDataSetChanged();
    }

}
