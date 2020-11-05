package com.example.helloworld;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class FragmentTop extends Fragment{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        ArrayList<RecycleItem> exampleList = new ArrayList<>();
        exampleList.add(new RecycleItem(R.drawable.infinity, "Avengers: Endgame", "Anggota Avengers yang masih hidup dan sekutu mereka berusaha untuk membalikkan kerusakan yang disebabkan oleh Thanos dalam Infinity War."));
        exampleList.add(new RecycleItem(R.drawable.endg, "Avengers: Infinity War", "Avengers dan Guardians of the Galaxy berupaya mencegah Thanos dari mengumpulkan enam Batu Infinity yang sangat kuat sebagai bagian dari upayanya untuk membunuh setengah dari seluruh kehidupan di alam semesta."));
        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new MyAdapter(exampleList));
        return view;
    }
}