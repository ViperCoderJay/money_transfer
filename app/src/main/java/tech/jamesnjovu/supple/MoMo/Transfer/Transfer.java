package tech.jamesnjovu.supple.MoMo.Transfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import tech.jamesnjovu.supple.MainActivity;
import tech.jamesnjovu.supple.MoMo.TopUp.TopUp;
import tech.jamesnjovu.supple.R;

public class Transfer extends AppCompatActivity {

    Spinner SMobile, SBank;
    String SNames[] = {"Choose Mobile Money Network...","Airtel Money", "MTN MoMo", "Zamtel Money"};
    String SBanks[] = {"Choose Bank..."};
    ArrayAdapter<String> arrayAdapterM, arrayAdapterB;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        SMobile = (Spinner) findViewById(R.id.MoMoSpinner);
        SBank = (Spinner) findViewById(R.id.BankSpinner);
        arrayAdapterM = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SNames);
        arrayAdapterB = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SBanks);

        back = findViewById(R.id.TUCback);
        SMobile.setAdapter(arrayAdapterM);
        SBank.setAdapter(arrayAdapterB);

        SMobile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        Toast.makeText(getApplicationContext(), "Airtel Money User Transfer", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "MTN MoMo User Transfer", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Zamtel Money User Transfer", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
