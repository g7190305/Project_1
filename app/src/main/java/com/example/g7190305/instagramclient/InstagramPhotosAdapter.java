package com.example.g7190305.instagramclient;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by g7190305 on 2015/7/26.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    // What our item looks lik
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        InstagramPhoto photo = getItem(position);
        // String userName;
        String userHtml;

        if ( convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        ImageView ivUserImage = (ImageView) convertView.findViewById(R.id.ivUserImage);

        tvCaption.setText(photo.getCaption());
        tvUsername.setText(photo.getUsername());
        // Clear out the imageview
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.getImageUrl()).into(ivPhoto);
        ivUserImage.setImageResource(0);
        Picasso.with(getContext()).load(photo.getUserImage()).resize(30,30).into(ivUserImage);

        // userHtml = String.format("<image src=\"%s\" /> <span>%s</span>", photo.getUserImage(), photo.getUsername());
        // tvUsername.setText(Html.fromHtml(userHtml));
        return convertView;
    }
}

