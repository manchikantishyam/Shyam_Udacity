package com.example.shyamsunder.shyam_udacity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shyamsunder.shyam_udacity.data.movieTrailerObject;

import java.util.ArrayList;

/**
 * Created by Shyam on 10/4/15.
 */
public class MovieDetailTrailerAdapter extends ArrayAdapter<movieTrailerObject> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<movieTrailerObject> movieTrailerObjects = new ArrayList<>();

    public MovieDetailTrailerAdapter(Context context, int resource, ArrayList<movieTrailerObject> movieTrailerObjects) {
        super(context, resource,movieTrailerObjects);
        this.movieTrailerObjects = movieTrailerObjects;
        layoutResourceId=resource;
        mContext=context;
    }
    /**
     * Updates grid data and refresh grid items.
     * @param movieTrailerObjects
     */
    public void setGridData(ArrayList<movieTrailerObject> movieTrailerObjects) {
        this.movieTrailerObjects = movieTrailerObjects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return movieTrailerObjects.size();
    }

    @Override
    public movieTrailerObject getItem(int position) {
        // TODO Auto-generated method stub
        return movieTrailerObjects.get(position);
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

        movieTrailerObject item = movieTrailerObjects.get(position);
        holder.textView.setText(item.getName());
        return row;
    }

    static class ViewHolder {
        TextView textView;
    }
}
