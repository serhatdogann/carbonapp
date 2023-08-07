package com.uygulamam.carbon;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.uygulamam.carbonapp.R;

public class UserFragment extends Fragment {
    private boolean isSignOutRequested = false;
    private GoogleSignInOptions gso; // Declare the gso variable here.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure Google Sign-In options.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // UserFragment layout dosyasını inflate ediyoruz
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // btn1, btn2 ve btn3 butonlarını buluyoruz
        View btn1 = view.findViewById(R.id.btn1);
        View btn2 = view.findViewById(R.id.btn2);
        View btn3 = view.findViewById(R.id.btn3);

        // btn1 butonuna tıklama olayı ekliyoruz
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ProfileFragment'ı oluşturuyoruz
                ProfileFragment profileFragment = new ProfileFragment();

                // Fragment yüklemek için FragmentTransaction oluşturuyoruz
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();

                // FrameLayout içine ProfileFragment'ı ekliyoruz
                fragmentTransaction.replace(R.id.fragment_container2, profileFragment);

                // Geri tuşuna basıldığında geri dönüş için Fragment'ı geri yığına ekliyoruz
                fragmentTransaction.addToBackStack(null);

                // Transaction'ı onaylıyoruz
                fragmentTransaction.commit();
            }
        });

        // btn2 butonuna tıklama olayı ekliyoruz
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PasswordChangeFragment'ı oluşturuyoruz
                PasswordChangeFragment passwordChangeFragment = new PasswordChangeFragment();

                // Fragment yüklemek için FragmentTransaction oluşturuyoruz
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();

                // FrameLayout içine PasswordChangeFragment'ı ekliyoruz
                fragmentTransaction.replace(R.id.fragment_container2, passwordChangeFragment);

                // Geri tuşuna basıldığında geri dönüş için Fragment'ı geri yığına ekliyoruz
                fragmentTransaction.addToBackStack(null);

                // Transaction'ı onaylıyoruz
                fragmentTransaction.commit();
            }
        });

        // btn3 butonuna tıklama olayı ekliyoruz
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSignOutRequested) {
                    // Sign out from Firebase Authentication
                    FirebaseAuth.getInstance().signOut();

                    // Sign out from Google Sign-In (Gmail)
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
                    googleSignInClient.signOut().addOnCompleteListener(task -> {
                        // After signing out, you can redirect the user to the login screen or any other desired action.
                        // For example, if you want to navigate the user back to the GirisFragment:
                        GirisFragment girisFragment = new GirisFragment();
                        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container2, girisFragment);
                        fragmentTransaction.commit();
                    });
                } else {
                    // Handle the other actions that should occur when btn3 is clicked, but not sign out.
                    // For example, show a dialog, display a message, etc.
                    // You can implement this behavior as needed.
                    // Here, we just toggle the isSignOutRequested flag to true when btn3 is clicked for the first time.
                    isSignOutRequested = true;
                }
            }
        });

        return view;
    }
}
