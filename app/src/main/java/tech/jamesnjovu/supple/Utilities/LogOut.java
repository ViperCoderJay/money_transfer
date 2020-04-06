package tech.jamesnjovu.supple.Utilities;

import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import tech.jamesnjovu.supple.user.Register;

import static androidx.core.content.ContextCompat.startActivity;

public class LogOut {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    public LogOut() {
//        FirebaseAuth.getInstance().signOut();
//
//        startActivity(new Intent(getApplicationContext(), Register.class));
//        finish();
//        Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
    }

}
