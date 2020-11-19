package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MahasiswaActivity extends AppCompatActivity {
    private EditText txtNama;
    private EditText txtNim;
    private EditText txtPhone;
    private Button btnSimpan;
    private Button btnHapus;
    private Button btnKembali;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("success", "Testing");
        setContentView(R.layout.activity_mahasiswa);
        String getState = getIntent().getStringExtra("STATE");
        txtNama = findViewById(R.id.namaMhs);
        txtNim = findViewById(R.id.noMhs);
        txtPhone = findViewById(R.id.phoneMhs);
        btnHapus = findViewById(R.id.hapusButton);
        btnSimpan = findViewById(R.id.simpanButton);
        btnKembali = findViewById(R.id.backButton);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sanity check
                if (!txtNim.getText().toString().isEmpty() && !txtNama.getText().toString().isEmpty()) {
                    tambahMahasiswa();
                } else {
                    Toast.makeText(getApplicationContext(), "No dan Nama Mhs tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDataMahasiswa();
            }
        });
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               kembali();
            }
        });
        if(getState.equals("Edit")){
            getDataMahasiswa();
        }
    }

    private void deleteDataMahasiswa() {
        String getDoc = getIntent().getStringExtra("DOC");
        db.collection("mahasiswa").document(getDoc)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        txtNama.setText("");
                        txtNim.setText("");
                        txtPhone.setText("");
                        Toast.makeText(getApplicationContext(), "Mahasiswa berhasil dihapus",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error deleting document: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void tambahMahasiswa() {
        Mahasiswa mhs = new Mahasiswa(txtNim.getText().toString(),
                txtNama.getText().toString(),
                txtPhone.getText().toString());
        String namaDoc = "mhs"+txtNim.getText().toString();
        db.collection("mahasiswa").document(namaDoc).set(mhs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Mahasiswa berhasil didaftarkan", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "ERROR" + e.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }

    private void getDataMahasiswa() {
        String getDoc = getIntent().getStringExtra("DOC");
        db.collection("mahasiswa").document(getDoc)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //Mahasiswa mhs = document.toObject(Mahasiswa.class);
                                String nama = (String) document.getData().get("nama");
                                String nim = (String) document.getData().get("nim");
                                String nohp = (String) document.getData().get("phone");
                                Mahasiswa mhs = new Mahasiswa(nim, nama, nohp);
                                txtNim.setText(mhs.getNim());
                                txtNama.setText(mhs.getNama());
                                txtPhone.setText(mhs.getPhone());
                            } else {
                                Toast.makeText(getApplicationContext(), "Document tidak ditemukan", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.w("DataMahasiswa", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void kembali(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
