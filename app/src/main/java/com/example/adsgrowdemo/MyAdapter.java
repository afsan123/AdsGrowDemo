package com.example.adsgrowdemo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<FBPage> fbPages;
    private Context context;


    public MyAdapter(Context context,ArrayList<FBPage> fbPages) {
        this.context=context;
        this.fbPages = fbPages;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyecler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load("https://graph.facebook.com/" + fbPages.get(position).getId() + "/picture?type=large").into(holder.imageView);
        holder.textView.setText(fbPages.get(position).getName());
        holder.rycitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PageLinkActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fbPages.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textView;
        LinearLayout rycitem;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.page_pic);
            textView = itemView.findViewById(R.id.page_name);
            rycitem = itemView.findViewById(R.id.rycitem);
        }
    }
}
