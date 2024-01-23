package com.ariyanjakaria.spacetour;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class bookActivity extends AppCompatActivity {
    ImageView book;
    EditText date, pperson, mmember, nnumber;
    Button pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        book = findViewById(R.id.bookkkk);
        date = findViewById(R.id.date);
        pay = findViewById(R.id.payment);
        pperson = findViewById(R.id.person);
        mmember = findViewById(R.id.member);
        nnumber = findViewById(R.id.number);


        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds()
                        , MaterialDatePicker.todayInUtcMilliseconds())).build();

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        date.setText(materialDatePicker.getHeaderText());
                    }
                });
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Datte = date.getText().toString();
                String Persone = pperson.getText().toString();
                String Member = mmember.getText().toString();
                String Number= nnumber.getText().toString();

                Map<String,Object> map=new HashMap<>();
                map.put("Date",Datte);
                map.put("person",Persone);
                map.put("member",Member);
                map.put("number",Number);

                FirebaseFirestore.getInstance().collection("Booking")
                        .document()
                        .set(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(bookActivity.this, "OK", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                Intent intent=new Intent(bookActivity.this, paymentActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
