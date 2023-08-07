package com.uygulamam.carbon;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.uygulamam.carbonapp.databinding.FragmentUyeOlBinding;

public class UyeOlFragment extends Fragment {

    private FragmentUyeOlBinding binding;
    private FirebaseAuth auth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentUyeOlBinding.inflate(inflater, container, false);

        auth=FirebaseAuth.getInstance();


        // Butona tıklama işlemini yönetmek için OnClickListener ekleyin
        binding.btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_ekle();
            }
        });



        return binding.getRoot();
    }

    // Tıklama işlemini ayrı bir metod olarak düzenleyin
    private void btn_ekle() {
        String email = binding.emailText.getText().toString();
        String password = binding.PassowrdText.getText().toString();
        String isim = binding.txtIsim.getText().toString();
        String soyisim = binding.txtSoyisim.getText().toString();

        if (email.equals("") || password.equals("") || isim.equals("") || soyisim.equals("")) {
            Toast.makeText(getActivity(), "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    // Kullanıcı başarıyla oluşturulduğunda verileri veritabanına ekleyin
                    String userID = auth.getCurrentUser().getUid();
                    Kullanici kullanici = new Kullanici(isim, soyisim);


                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
