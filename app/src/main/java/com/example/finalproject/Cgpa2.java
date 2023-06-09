package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Cgpa2 extends AppCompatActivity {
    private EditText credit;
    private EditText grade;

    private Button add;
    private Button reset;

    private TextView cgpa;
    private TextView serial;
    private TextView creditTextView;
    private TextView gradeTextView;

    private int i = 0;
    private double result;
    private double sumGrade = 0;
    private double sumCredit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_cgpa2);
        credit = findViewById(R.id.creditEcitTextId);
        grade = findViewById(R.id.gradeEditTextId);

        add = findViewById(R.id.addButtonId);
        reset = findViewById(R.id.resetButtonId);

        cgpa = findViewById(R.id.cgpaTextViewId);
        serial = findViewById(R.id.serialTextViewId);
        creditTextView = findViewById(R.id.creditTextViewId);
        gradeTextView = findViewById(R.id.gradeTextViewId);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(Double.parseDouble(credit.getText().toString())<=4
                            && Double.parseDouble(credit.getText().toString())>=1) {
                        if (Double.parseDouble(grade.getText().toString()) <= 4
                                && Double.parseDouble(grade.getText().toString()) >= 0) {

                            sumGrade = sumGrade + (Double.parseDouble(grade.getText().toString()) * Double.parseDouble(credit.getText().toString()));
                            sumCredit = sumCredit + Double.parseDouble(credit.getText().toString());
                            result = sumGrade / sumCredit;
                            i++;
                            cgpa.setText(String.format("CGPA/GPA is: %.2f" , result));
                            serial.setText(serial.getText() + "\n" + i);
                            creditTextView.setText(creditTextView.getText() + "\n" + credit.getText().toString());
                            gradeTextView.setText(gradeTextView.getText() + "\n" + grade.getText().toString());
                            grade.getText().clear();
                            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Enter Correct Values", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else{
                        Toast.makeText(getApplicationContext(), "Enter Correct Value", Toast.LENGTH_SHORT).show();
                    }
                }

                catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Enter Value", Toast.LENGTH_SHORT).show();
                }


            }

        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                sumGrade = 0;
                sumCredit = 0;
                result = 0;
                credit.getText().clear();
                grade.getText().clear();
                cgpa.setText("CGPA/GPA is: ");
                serial.setText("Serial");
                gradeTextView.setText("Grade");
                creditTextView.setText("Credit");
                Toast.makeText(getApplicationContext() , "Reseted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}