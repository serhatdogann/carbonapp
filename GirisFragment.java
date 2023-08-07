package com.uygulamam.carbon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.uygulamam.carbonapp.R;

import androidx.appcompat.widget.Toolbar;

public class GirisFragment extends Fragment {

    private Button girisYap;
    private Button UyeOl;
    private Button googleGiris;

    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_giris, container, false);

        auth = FirebaseAuth.getInstance();

        girisYap = view.findViewById(R.id.girisYap);
        UyeOl = view.findViewById(R.id.UyeOl);
        googleGiris = view.findViewById(R.id.googleGiris);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GirisYapFragment girisYapFragment = new GirisYapFragment();
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container2, girisYapFragment);
                fragmentTransaction.hide(GirisFragment.this);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        UyeOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UyeOlFragment uyeOlFragment = new UyeOlFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container2, uyeOlFragment);
                transaction.hide(GirisFragment.this);
                transaction.commit();
            }
        });

        googleGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

        // Google Giriş Seçeneklerini Ayarla
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        return view;
    }

    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Google Giriş Sonucunu İşle
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Girişi Başarılı
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Girişi Başarısız
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Firebase Girişi Başarılı
                            FirebaseUser user = auth.getCurrentUser();
                            // Burada Firebase kullanıcısını doğrulayabilir ve diğer işlemleri yapabilirsiniz.
                            // Örneğin, giriş yapıldıktan sonra nereye yönlendirecekseniz burada yapabilirsiniz.
                            // MainActivity'ye yönlendirelim:
                            Intent mainIntent = new Intent(requireContext(), MainActivity.class);
                            startActivity(mainIntent);
                            requireActivity().finish();
                        } else {
                            // Firebase Girişi Başarısız
                        }
                    }
                });
    }
}
