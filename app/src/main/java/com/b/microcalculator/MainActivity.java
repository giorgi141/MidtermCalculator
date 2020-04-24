package com.b.microcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //ფაილში შენახვა ვცადე :დ მოკლედ ბოლოს მივხვდი რომ ლისთვიევში ჯობდა მარტივად გაკეთება
    EditText mInputp,mInputm;
    TextView mViewCount,mViewSaved;
    Button Submit,Show;
    int first,second,sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputm = findViewById(R.id.Inputm);
        mInputp = findViewById(R.id.Inputp);
        mViewCount = findViewById(R.id.ViewCount);
        mViewSaved = findViewById(R.id.ViewSaved);
        Submit = findViewById(R.id.Submit);
        Show = findViewById(R.id.BViewSaved);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final FileOutputStream[] fileOutputStream = {null};


        String[] plants = new String[]{
                "+",
                "-",
                "/",
                "*",
        };
        ArrayAdapter <String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plants
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = Integer.parseInt(mInputp.getText().toString());
                second = Integer.parseInt(mInputm.getText().toString());
                try {
                    fileOutputStream[0] = openFileOutput("saved.txt", Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                switch (spinner.getSelectedItem().toString()){
                    case "+":
                        sum = first+second;
                        break;
                    case "-":
                        sum = first-second;
                        break;
                    case "*":
                        sum = first*second;
                        break;
                    case "/":
                        sum = first/second;
                        break;
                }
                mViewCount.setText(String.valueOf(sum));
                String Save = first+spinner.getSelectedItem().toString()+second+"="+sum;
                try {
                    fileOutputStream[0].write(Save.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fileOutputStream[0].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saved = null;
                try {
                    FileInputStream fis = openFileInput("saved.txt");
                    byte[] dateArray  = new     byte[fis.available()];
                    while (fis.read(dateArray) != -1){
                        saved = new String(dateArray);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mViewSaved.setText(saved);
            }
        });

    }




}
