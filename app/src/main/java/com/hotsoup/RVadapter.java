package com.hotsoup;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVadapter extends RecyclerView.Adapter<RVadapter.myViewHolder> { // Adapter for the recyclerviews no other purpose
    myViewHolder holder;
    ArrayList<String> data;
    Context context;
    private static RecyclerViewClickInterface itemlistener;
    public RVadapter(Context ct, ArrayList<String> titles, RecyclerViewClickInterface recyclerViewClickInterface){
        context = ct;
        data = titles;
        this.itemlistener = recyclerViewClickInterface;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row, parent, false);
        return new myViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.textview.setText(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = holder.textview.getText().toString();
                itemlistener.recyclerViewListClicked(food);
                System.out.println("Tehty");
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView textview;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.foodTitles);
        }}}







