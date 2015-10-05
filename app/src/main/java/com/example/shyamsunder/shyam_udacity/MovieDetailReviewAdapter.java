package com.example.shyamsunder.shyam_udacity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shyamsunder.shyam_udacity.data.movieReviewObject;

import java.util.ArrayList;

/**
 * Created by Shyam on 10/5/15.
 */
public class MovieDetailReviewAdapter extends ArrayAdapter<movieReviewObject> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<movieReviewObject> movieReviewObjects = new ArrayList<>();

    public MovieDetailReviewAdapter(Context context, int resource, ArrayList<movieReviewObject> movieReviewObjects) {
        super(context, resource,movieReviewObjects);
        this.movieReviewObjects = movieReviewObjects;
        layoutResourceId=resource;
        mContext=context;
    }
    /**
     * Updates grid data and refresh grid items.
     * @param movieReviewObjects
     */
    public void setGridData(ArrayList<movieReviewObject> movieReviewObjects) {
        this.movieReviewObjects = movieReviewObjects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return movieReviewObjects.size();
    }

    @Override
    public movieReviewObject getItem(int position) {
        // TODO Auto-generated method stub
        return movieReviewObjects.get(position);
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
            holder.authorNameTextView = (TextView) row.findViewById(R.id.review_author_name);
            holder.reviewContentTextView = (TextView) row.findViewById(R.id.review_content);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        movieReviewObject item = movieReviewObjects.get(position);
        holder.authorNameTextView.setText(item.getAuthor());
        holder.reviewContentTextView.setText(item.getContent());
        return row;
    }

    static class ViewHolder {
        TextView authorNameTextView,reviewContentTextView;
    }
}
