package com.bubblex.popularmovie.Adpter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.bubblex.popularmovie.Model.Trailer;
import com.bubblex.popularmovie.R;

import java.util.List;

/**
 * Created by Amr ElBrolosy on 28/12/15.
 */
public class ReviewAdpter extends RecyclerView.Adapter<ReviewAdpter.PersonViewHolder> {
    List<Trailer> Movies;
    Context context;
    Dialog dialog;


    public ReviewAdpter(List<Trailer> Movies, Context context) {
        this.Movies = Movies;
        this.context = context;

    }


    public class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView uathor, content;

        PersonViewHolder(View itemView) {
            super(itemView);
            uathor = (TextView) itemView.findViewById(R.id.uthor);
            content = (TextView) itemView.findViewById(R.id.content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog = new Dialog(context, R.style.AppTheme);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_dialoglayout);
                    TextView textView = (TextView) dialog.findViewById(R.id.full_text);
                    textView.setText(Movies.get(getPosition()).getKey());
                    dialog.show();
                }
            });
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_adpter, viewGroup, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.uathor.setText(Movies.get(i).getName());
        personViewHolder.content.setText(Movies.get(i).getKey());
    }

    @Override
    public int getItemCount() {
        return Movies.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(Trailer trailer, int pos) {
        Movies.add(pos, trailer);
        notifyItemChanged(pos);
        notifyDataSetChanged();

    }

}
