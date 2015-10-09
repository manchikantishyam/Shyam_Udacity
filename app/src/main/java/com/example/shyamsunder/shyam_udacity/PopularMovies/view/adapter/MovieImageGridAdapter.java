package com.example.shyamsunder.shyam_udacity.PopularMovies.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.shyamsunder.shyam_udacity.PopularMovies.datamodel.MovieDetailObject;
import com.example.shyamsunder.shyam_udacity.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shyam on 8/10/15.
 */
public class MovieImageGridAdapter extends ArrayAdapter<MovieDetailObject> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<MovieDetailObject> mGridData = new ArrayList<>();

    public MovieImageGridAdapter(Context mContext, int layoutResourceId, ArrayList<MovieDetailObject> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }


    /**
     * Updates grid data and refresh grid items.
     * @param mGridData
     */
    public void setGridData(ArrayList<MovieDetailObject> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mGridData.size();
    }

    @Override
    public MovieDetailObject getItem(int position) {
        // TODO Auto-generated method stub
        return mGridData.get(position);
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
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        MovieDetailObject item = mGridData.get(position);
        Picasso.with(mContext).load(item.getBackdrop_URL()).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
