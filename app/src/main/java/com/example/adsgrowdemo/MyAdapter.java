package com.example.adsgrowdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<FBPage> fbPages;
    private Context context;
    private OnPageClickListener mOnPageClickListener;

    public MyAdapter(Context context,ArrayList<FBPage> fbPages, OnPageClickListener onPageClickListener) {
        this.context=context;
        this.fbPages = fbPages;
        this.mOnPageClickListener = onPageClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyecler_item, parent, false);
        return new MyViewHolder(view, mOnPageClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load("https://graph.facebook.com/" + fbPages.get(position).getId() + "/picture?type=large").into(holder.imageView);
        holder.textView.setText(fbPages.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return fbPages.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        OnPageClickListener onPageClickListener;

        public MyViewHolder(@NonNull View itemView, OnPageClickListener mOnPageClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.page_pic);
            textView = itemView.findViewById(R.id.page_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPageClickListener.onPageClick(getAdapterPosition());
        }
    }

    public interface OnPageClickListener {
        void onPageClick(int position);
    }

}
