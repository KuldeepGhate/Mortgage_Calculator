package com.example.kuldeep.mortgage_calculator;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.*;
import static java.lang.Math.floor;
import static java.lang.Math.pow;


public class MainActivity extends Activity {

    private EditText amount;
    private SeekBar interest;
    private RadioGroup radioGroup;
    private RadioButton rb7;
    private RadioButton rb15;
    private RadioButton rb30;
    private CheckBox tax;
    private CheckBox insurance;
    private Button calculate;
    private TextView answer;
    private TextView rate;
    private float amt;
    private int years;
    private double taxes;
    private float progress_val;
    private double ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();


///////////////////////////////////////////SEEKBAR//////////////////////////////////////////////////

        interest.setProgress(50);
        progress_val = (float)5/1200;

        rate.setText("Rate: " + interest.getProgress()/10 + "/" + interest.getMax() / 10);
         interest.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_val = (float)(progress /10.0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                rate.setText("Rate: " + progress_val + "/" + (float)seekBar.getMax()/10);
                progress_val = progress_val/1200;

            }

        });

///////////////////////////////////////////RADIOGROUP///////////////////////////////////////////////
        /*Set default value*/
        rb30.setChecked(true);
        years=30*12;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.year7){
                    years = 7*12;
                }
                else if(checkedId == R.id.year15){
                    years = 15*12;
                }
                else if(checkedId == R.id.year30){
                    years = 30*12;
                }
            }
        });
////////////////////////////////////////////BUTTON////////////////////////////////////////////////////////

        calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            /*Check if amount is entered by the user*/
            public void onClick(View v) {
                try{
                    amt = (Float)Float.valueOf(amount.getText().toString());
                    calc(amt,progress_val,years);
                }
                catch (NumberFormatException e){
                    amount.setError("Please enter the amount");
                }
                finish();
               }
        });

    }

    /*A function which actually calculates the monthly pay*/

    public void calc(float amt,float progress_val,int years){
        double ans=0;

        if (tax.isChecked()){
            taxes = 0.001 * amt;
        }
        else if(!tax.isChecked()){
            taxes = 0;
        }
        if (progress_val!=0) {
            ans = amt * (progress_val * pow(1 + progress_val, years) / (pow(1 + progress_val, years) - 1))+taxes;
        }
        else if (progress_val==0) {
             ans = (amt/years) + taxes;
        }
        answer.setText("" + ans);
    }

    private void initializeVariables(){
        amount = (EditText) findViewById(R.id.amountEdit);
        interest = (SeekBar) findViewById(R.id.seekInterest);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        rb7 = (RadioButton) findViewById(R.id.year7);
        rb15 = (RadioButton) findViewById(R.id.year15);
        rb30 = (RadioButton) findViewById(R.id.year30);
        tax = (CheckBox) findViewById(R.id.checkBox1);
        calculate = (Button) findViewById(R.id.calculate);
        answer = (TextView) findViewById(R.id.final_pay);
        rate = (TextView) findViewById(R.id.showRate);
    }

}