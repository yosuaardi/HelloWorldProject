package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentBottom extends Fragment {
    private Button buttonTambah;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Mahasiswa> mhsList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        getDataMahasiswa();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.mhs_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mAdapter = new MahasiswaAdapter(mhsList);
        //mRecyclerView.setAdapter(mAdapter);
        buttonTambah = (Button) getActivity().findViewById(R.id.btnTambah);
        buttonTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MahasiswaActivity.class);
                intent.putExtra("STATE", "Tambah");
                startActivity(intent);
            }
        });
    }

    private void getDataMahasiswa() {
        db.collection("mahasiswa")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DataMahasiswa", document.getId() + " => " + document.getData());
                                String nama = (String) document.getData().get("nama");
                                String nim = (String) document.getData().get("nim");
                                String nohp = (String) document.getData().get("phone");
                                Mahasiswa mhs = new Mahasiswa(nim, nama, nohp);
                                mhsList.add(mhs);
                                Log.d("DataMahasiswa", String.valueOf(mhsList.size()));
                            }
                            mAdapter = new MahasiswaAdapter(mhsList);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            Log.w("DataMahasiswa", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}