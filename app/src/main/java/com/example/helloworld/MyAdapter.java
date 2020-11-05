package com.example.helloworld;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerViewHolder> {
    private ArrayList<RecycleItem> mExampleList;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView1;
        private TextView mTextView2;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageFilm);
            mTextView1 = itemView.findViewById(R.id.nameFilm);
            mTextView2 = itemView.findViewById(R.id.descFilm);
        }

        public ImageView getImageFilm(){
            return mImageView;
        }

        public TextView getNameFilm(){
            return mTextView1;
        }

        public TextView getDescFilm(){
            return mTextView2;
        }
    }

    public MyAdapter(ArrayList<RecycleItem> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.recycle_item;
    }

    @NonNull
    @Override
    public MyAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        RecycleItem currentItem = this.mExampleList.get(position);
        holder.getImageFilm().setImageResource(currentItem.getImageResource());
        holder.getNameFilm().setText(currentItem.getText1());
        holder.getDescFilm().setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return this.mExampleList.size();
    }
}
