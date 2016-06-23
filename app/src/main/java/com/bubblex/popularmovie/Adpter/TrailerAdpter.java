package com.bubblex.popularmovie.Adpter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bubblex.popularmovie.Model.Trailer;
import com.bubblex.popularmovie.R;

import java.util.List;

/**
 * Created by Amr ElBrolosy on 28/12/15.
 */
public class TrailerAdpter extends RecyclerView.Adapter<TrailerAdpter.PersonViewHolder> {
    List<Trailer> Movies;
    Context context;


    public TrailerAdpter(List<Trailer> Movies, Context context) {
        this.Movies = Movies;
        this.context = context;
    }


    public class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        PersonViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.movie_trailer_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + Movies.get(getPosition()).getKey()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_adpter, viewGroup, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.name.setText(Movies.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return Movies.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
