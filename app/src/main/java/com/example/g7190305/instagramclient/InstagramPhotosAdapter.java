package com.example.g7190305.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        TextView tvLikes =(TextView) convertView.findViewById(R.id.tvLikes);

        RoundedImageView rivUserImage = (RoundedImageView) convertView.findViewById(R.id.rivUserImage);

        float density = getContext().getResources().getDisplayMetrics().density;
        Drawable drawable = getContext().getResources().getDrawable(R.mipmap.ic_likes, null);
        int width = Math.round(15 * density);
        int height = Math.round(15 * density);
        drawable.setBounds(0, 0, width, height);
        tvLikes.setCompoundDrawables(drawable, null, null, null);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(1)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        tvCaption.setText(photo.getCaption());
        tvUsername.setText(photo.getUsername());
        tvLikes.setText(String.format("%d", photo.getLikesCount()));

        // int logstr = photo.getCreateTime();
        // Log.i("DEBUG", String.format("create time: %d", logstr));
        // tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(photo.getCreateTime() * 1000).toString());
        // tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(photo.getCreateTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS ));
        String timespan = DateUtils.getRelativeTimeSpanString(photo.getCreateTime()*1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS ).toString();
        tvTimeStamp.setText(timespan);
        // Clear out the imageview
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.getImageUrl()).into(ivPhoto);

        // tvUsername.setCompoundDrawables(rivUserImage.getDrawable(), null, null, null);

        rivUserImage.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.getUserImage())
                .resize(30, 0)
                .transform(transformation)
                .into(rivUserImage);

        // tvUsername.setCompoundDrawables(rivUserImage.getDrawable(), null, null, null);
        // userHtml = String.format("<image src=\"%s\" /> <span>%s</span>", photo.getUserImage(), photo.getUsername());
        // tvUsername.setText(Html.fromHtml(userHtml));
        return convertView;
    }
}

