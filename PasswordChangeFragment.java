package com.uygulamam.carbon;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uygulamam.carbonapp.R;

public class PasswordChangeFragment extends Fragment {

    private EditText mevcutSifre;
    private EditText yeniSifre;
    private EditText yeniSifreT;
    private Button sifreGuncelle;

    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_password_change, container, false);

        mevcutSifre = view.findViewById(R.id.mevcutSifre);
        yeniSifre = view.findViewById(R.id.yeniSifre);
        yeniSifreT = view.findViewById(R.id.yeniSifreT);
        sifreGuncelle = view.findViewById(R.id.sifreGuncelle);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        sifreGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = mevcutSifre.getText().toString().trim();
                String newPassword = yeniSifre.getText().toString().trim();
                String newPasswordRepeat = yeniSifreT.getText().toString().trim();

                if (!newPassword.equals(newPasswordRepeat)) {
                    Toast.makeText(getActivity(), "Yeni şifreler uyuşmuyor.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mevcut oturumu doğrula
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Şifre doğrulandı, şimdi şifreyi güncelle
                            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Şifre başarıyla güncellendi.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Şifre güncelleme hatası: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Mevcut şifre doğrulanamadı.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}
