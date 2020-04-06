package tech.jamesnjovu.supple.Nav;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import tech.jamesnjovu.supple.MoMo.TopUp.TopUp;
import tech.jamesnjovu.supple.MoMo.Transfer.Transfer;
import tech.jamesnjovu.supple.R;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private ImageButton transferIB, addMoneyIB, exchangeIB, requestIB,notificationIB, historyIB,dashboardIB, currencyIB;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    TextView balance,user, greet, currence;

    Double bal;

    String fullName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        balance=(TextView)view.findViewById(R.id.TVHomeBalance);
        user=(TextView)view.findViewById(R.id.TVUser);

        transferIB = view.findViewById(R.id.IBTransfer);
        greet=view.findViewById(R.id.Hi);
        currence=view.findViewById(R.id.K);
        addMoneyIB = view.findViewById(R.id.IBAddMoney);
        exchangeIB = view.findViewById(R.id.IBExchange);
        requestIB = view.findViewById(R.id.IBRequest);
        notificationIB = view.findViewById(R.id.IBPay);
        historyIB = view.findViewById(R.id.IBSearch);
        dashboardIB = view.findViewById(R.id.IBnotications);
        currencyIB = view.findViewById(R.id.IBmore);
        Log.d(TAG, "onCreateView: started.");

        transferIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Transfer.class));
                Toast.makeText(getActivity(), "Transfer Activated", Toast.LENGTH_SHORT).show();
            }
        });
        addMoneyIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TopUp.class));
                Toast.makeText(getActivity(), "Top Up Activated", Toast.LENGTH_SHORT).show();
            }
        });
        exchangeIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Exchange Page Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        requestIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Request Page Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        notificationIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Pay Page Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        historyIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Search Page Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        currencyIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "More Page Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        dashboardIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Notifications Page Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DocumentReference docRef = fStore.collection("users").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        bal = document.getDouble("balance");
                        fullName = document.getString("firstName")+" "+document.getString("lastName");
                        balance.setText(bal.toString());
                        currence.setText("K");
                        user.setText(fullName);
                        greet.setText("Hi");
                    }else {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
