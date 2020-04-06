package tech.jamesnjovu.supple.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import tech.jamesnjovu.supple.MainActivity;
import tech.jamesnjovu.supple.R;

public class EnterDetails extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;

    EditText firstName, lastName, Email, Password, CPassword;
    Button saveBtn;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_details);

        firstName = (EditText) findViewById(R.id.FName);
        lastName = (EditText) findViewById(R.id.LName);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        CPassword = (EditText) findViewById(R.id.CPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();

        final DocumentReference docRef = fStore.collection("users").document(userId);

        saveBtn = (Button) findViewById(R.id.btnSave);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !Email.getText().toString().isEmpty() && !Password.getText().toString().isEmpty() && !CPassword.getText().toString().isEmpty()) {
                    if (Password.getText().toString().equals(CPassword.getText().toString())) {
                        String first = firstName.getText().toString();
                        String last = lastName.getText().toString();
                        String email = Email.getText().toString();
                        String password = Password.getText().toString();
                        double balance = 0.00;

                        Map<String, Object> user = new HashMap<>();
                        user.put("firstName", first);
                        user.put("lastName", last);
                        user.put("email", email);
                        user.put("password", password);
                        user.put("balance",balance);

                        docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(EnterDetails.this, "Data is not Saved", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(EnterDetails.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EnterDetails.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
