package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static android.content.Context.WINDOW_SERVICE;

public class FragmentCamera extends Fragment {
    private TextView orientation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        Button btnKamera = view.findViewById(R.id.btnKamera);
        Button btnLokasi = view.findViewById(R.id.btnLocation);
        orientation = view.findViewById(R.id.orientationTxt);
        btnKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CameraActivityTwo.class);
                startActivity(intent);
            }
        });
        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), LocationActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Display display = ((WindowManager) requireContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
            orientation.setText("Orientation: Landscape");
            Log.d("Success", "onResume: Landscape");
        }else{
            orientation.setText("Orientation: Potrait");
            Log.d("Success", "onResume: Potrait");
        }
    }
}
