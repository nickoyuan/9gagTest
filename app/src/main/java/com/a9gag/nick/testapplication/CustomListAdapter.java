package com.a9gag.nick.testapplication;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class CustomListAdapter extends BaseAdapter {

    Context context;
    ArrayList<GagsData> gagsData = null;

    private static LayoutInflater inflater=null;

    // Constructor
    public CustomListAdapter(
        Activity activity,
        ArrayList<GagsData> gagsData
    ) {

        context=activity;
        this.gagsData = gagsData;
        //for inflating layout resources in this context.
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addListItemToAdapter(List<GagsData> list) {
        //Add list to current array list of data
        gagsData.addAll(list);
        //Notify UI
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return gagsData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    //A ViewHolder object stores each of the component views inside the tag field of the Layout
    public class Holder
    {
        TextView pageTitle;
        ImageView mainImageView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.page_list_view, null);
        setHolderViewReference(holder, rowView);
        setHolderViewTxt(position, holder);
        return rowView;
    }

    public void setHolderViewTxt(int position, Holder holder) {
        holder.pageTitle.setText(gagsData.get(position).getPayloadTitle());
        if(gagsData.get(position).getPayloadType().equals("Photo")) {
            setImageViewParams(position, holder.mainImageView);
            loadGlideImage(position, holder.mainImageView);
        }
    }

    public void setHolderViewReference(Holder holder, View rowView) {
        holder.pageTitle = (TextView) rowView.findViewById(R.id.gagTitle);
        holder.mainImageView = (ImageView) rowView.findViewById(R.id.mainImageView);
    }


    private void loadGlideImage(int position, ImageView mainImageView) {
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_rotate_left_black_24dp)
            .fitCenter()
            .priority(Priority.HIGH)
            .error(R.drawable.ic_rotate_left_black_24dp);
        Glide.with(context)
            .load(gagsData.get(position).getPayloadUrl())
            .apply(options)
            .into(mainImageView);
    }

    private void setImageViewParams(int position, ImageView mainImageView) {
        try{
            mainImageView.getLayoutParams().height = Integer.parseInt(gagsData.get(position).getUrlHeight());
        } catch (NullPointerException | NumberFormatException e){
            mainImageView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            Log.d(TAG, e.getMessage());
        }
        try{
            mainImageView.getLayoutParams().width = Integer.parseInt(gagsData.get(position).getUrlWidth());
        } catch (NullPointerException | NumberFormatException e){
            mainImageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            e.printStackTrace();
        }
    }

}