package tech.jamesnjovu.supple.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

import tech.jamesnjovu.supple.MainActivity;
import tech.jamesnjovu.supple.R;

public class Register extends AppCompatActivity {

    FirebaseAuth fAuth;
    PhoneAuthProvider.ForceResendingToken token;
    FirebaseFirestore fStore;

    EditText phoneNumber, codeEnter;
    TextView Num;
    Button btnNext;
    RelativeLayout firstRel, secondRel,LoaderRel,MainRel;
    CountryCodePicker codePicker;

    String verificationId, phoneNo;
    boolean verificationInProgress = false;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        loadingBar = new ProgressDialog(this);
        phoneNumber=findViewById(R.id.phone);
        codeEnter=findViewById(R.id.codeEnter);
        Num=findViewById(R.id.num);
        btnNext= findViewById(R.id.nextBtn);
        firstRel=(RelativeLayout)findViewById(R.id.phoneAuth);
        secondRel=(RelativeLayout)findViewById(R.id.SecRel);
        LoaderRel=(RelativeLayout)findViewById(R.id.LoaderRel);
        MainRel=(RelativeLayout)findViewById(R.id.MainRel);
        codePicker = (CountryCodePicker)findViewById(R.id.ccp);

        MainRel.setVisibility(View.VISIBLE);
        LoaderRel.setVisibility(View.GONE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificationInProgress) {
                    if (!phoneNumber.getText().toString().isEmpty() && phoneNumber.getText().toString().length() == 9) {
                        phoneNo = "+" + codePicker.getSelectedCountryCode() + phoneNumber.getText().toString();

                        loadingBar.setTitle("Phone Number Verification");
                        loadingBar.setMessage("Please wait, while we are authentication your phone number");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        requestOTP();

                    } else {
                        phoneNumber.setError("Phone Number is not Valid");
                    }
                }else {
                    String userOTP = codeEnter.getText().toString();
                    if(!userOTP.isEmpty() && userOTP.length()==6){
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,userOTP);
                        loadingBar.setTitle("Code Verification");
                        loadingBar.setMessage("Please wait, while we are validate your the code provided");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        verifyAuth(credential);
                    }else {
                        codeEnter.setError("Valid OTP is required");
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (fAuth.getCurrentUser() != null){
            MainRel.setVisibility(View.GONE);
            LoaderRel.setVisibility(View.VISIBLE);
            checkingUserProfile();
        }
    }
    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    checkingUserProfile();
                    Toast.makeText(Register.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                }else {
                    loadingBar.dismiss();
                    Toast.makeText(Register.this, "Authentication Failed: Code Did Not Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void requestOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNo, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                token = forceResendingToken;
                firstRel.setVisibility(View.GONE);
                loadingBar.dismiss();
                Num.setText("Code sent to "+phoneNo);
                btnNext.setText("Submit");
                secondRel.setVisibility(View.VISIBLE);
                verificationInProgress = true;
                Toast.makeText(Register.this, "Code Sent! ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(Register.this, "Code Expired: Request for new Code.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Register.this, "Can't Create Account "+e.getMessage(), Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                firstRel.setVisibility(View.VISIBLE);
                secondRel.setVisibility(View.GONE);
            }
        });

    }
    private void checkingUserProfile() {
        DocumentReference docRef = fStore.collection("users").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        loadingBar.dismiss();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }else {
                        loadingBar.dismiss();
                        startActivity(new Intent(getApplicationContext(), EnterDetails.class));
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        MainRel.setVisibility(View.VISIBLE);
        LoaderRel.setVisibility(View.GONE);
    }
}
