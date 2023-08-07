package com.uygulamam.carbon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.uygulamam.carbonapp.R;
import com.uygulamam.carbonapp.databinding.FragmentGirisYapBinding;

public class GirisYapFragment extends Fragment {

    private FragmentGirisYapBinding binding;

    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGirisYapBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();

        binding.btnGirisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGirisYap();
            }
        });

        return binding.getRoot();
    }

    private void btnGirisYap() {
        String email = binding.emailText2.getText().toString();
        String password = binding.PassowrdText2.getText().toString();

        if (email.equals("") || password.equals("")) {
            Toast.makeText(getActivity(), "Tüm Alanların Doldurulması Zorunludur", Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    // Giriş başarılı olduğunda HomeFragment'e geçiş yap
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container2, homeFragment);
                    transaction.commit();
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Giriş başarısız olduğunda hata mesajı ver
                    Toast.makeText(getActivity(), "Yanlış Kullanıcı Adı Veya Şifre", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}