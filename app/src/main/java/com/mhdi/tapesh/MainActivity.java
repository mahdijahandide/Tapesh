package com.mhdi.tapesh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.mhdi.tapesh.data.ApiSendingData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PinView pinView;
    private Button next;
    private TextView topText, textU;
    private EditText firstName, userPhone, lastName;
    private ConstraintLayout second;
    private LinearLayout first;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);

        topText = findViewById(R.id.topText);
        pinView = findViewById(R.id.pinView);
        next = findViewById(R.id.button);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        userPhone = findViewById(R.id.userPhone);
        first = findViewById(R.id.first_step);
        second = findViewById(R.id.secondStep);
        textU = findViewById(R.id.textView_noti);
        first.setVisibility(View.VISIBLE);
        second.setVisibility(View.GONE);


        int lenght = 11;
        userPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(lenght)});

        next.setOnClickListener(MainActivity.this);
    }


    @Override
    public void onClick(View view) {


        if (TextUtils.isEmpty(firstName.getText())) {
            firstName.setError("نام خود را وارد کنید");
            firstName.requestFocus();
        }
        if (TextUtils.isEmpty((userPhone.getText()))) {
            userPhone.setError("تلفن همراه را وارد نید");
            userPhone.requestFocus();
        }
        if (TextUtils.isEmpty(lastName.getText())) {
            lastName.setError("نام خانوادگی را وارد کنید");
        }


        if (next.getText().equals("شروع")) {

            String name = firstName.getText().toString().trim();
            String lName = lastName.getText().toString().trim();
            String phone = userPhone.getText().toString().trim();


            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(lName)) {
                if (userPhone.getText().toString().length() < 11) {
                    userPhone.setError("شماره 11 رقم باید باشد");
                } else {
                    sendTODB();
                }


            } else {
                Toast.makeText(MainActivity.this, "اطلاعات خود را وارد کنید", Toast.LENGTH_SHORT).show();
            }
        } else if (next.getText().equals("تایید")) {
            String OTP = pinView.getText().toString();
            if (OTP.equals("1111")) {
                pinView.setLineColor(Color.GREEN);
                textU.setText("شماره تایید شد");
                textU.setTextColor(Color.GREEN);
                next.setText("ادامه");
                Intent myIntent = new Intent(this, BluetoothActivity.class);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(this, R.anim.fadein, R.anim.fadeout);
                this.startActivity(myIntent, options.toBundle());
            } else {
                pinView.setLineColor(Color.RED);
                textU.setText("رمز نامعتبر");
                textU.setTextColor(Color.RED);
            }
        }
    }

    public void sendTODB() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiSendingData.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(MainActivity.this, response + "ارسال شد", Toast.LENGTH_SHORT).show();

               /* next.setText("تایید");
                first.setVisibility(View.GONE);
                second.setVisibility(View.VISIBLE);
                Animation slideUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
                if (first.getVisibility() == View.GONE) {
                    second.setVisibility(View.VISIBLE);
                    second.startAnimation(slideUp);
                }
                topText.setText("کد چهاررقمی تایید را وارد کنید");*/
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error + "", Toast.LENGTH_LONG).show();

                next.setText("تایید");
                first.setVisibility(View.GONE);
                second.setVisibility(View.VISIBLE);
                Animation slideUp = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
                if (first.getVisibility() == View.GONE) {
                    second.setVisibility(View.VISIBLE);
                    second.startAnimation(slideUp);
                }
                topText.setText("کد چهاررقمی تایید را وارد کنید");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                String first_name = firstName.getText().toString();
                String last_name = lastName.getText().toString();
                String phone_number = userPhone.getText().toString();
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("phone_number", phone_number);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
