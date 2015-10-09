package com.example.shyamsunder.shyam_udacity.PopularMovies.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shyamsunder.shyam_udacity.PopularMovies.datamodel.MovieTrailerObject;
import com.example.shyamsunder.shyam_udacity.R;

import java.util.ArrayList;

/**
 * Created by Shyam on 10/4/15.
 */
public class MovieDetailTrailerAdapter extends ArrayAdapter<MovieTrailerObject> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<MovieTrailerObject> MovieTrailerObjects = new ArrayList<>();

    public MovieDetailTrailerAdapter(Context context, int resource, ArrayList<MovieTrailerObject> MovieTrailerObjects) {
        super(context, resource, MovieTrailerObjects);
        this.MovieTrailerObjects = MovieTrailerObjects;
        layoutResourceId=resource;
        mContext=context;
    }
    /**
     * Updates grid data and refresh grid items.
     * @param MovieTrailerObjects
     */
    public void setGridData(ArrayList<MovieTrailerObject> MovieTrailerObjects) {
        this.MovieTrailerObjects = MovieTrailerObjects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return MovieTrailerObjects.size();
    }

    @Override
    public MovieTrailerObject getItem(int position) {
        // TODO Auto-generated method stub
        return MovieTrailerObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) row.findViewById(R.id.trailerName);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        MovieTrailerObject item = MovieTrailerObjects.get(position);
        holder.textView.setText(item.getName());
        return row;
    }

    static class ViewHolder {
        TextView textView;
    }
}
