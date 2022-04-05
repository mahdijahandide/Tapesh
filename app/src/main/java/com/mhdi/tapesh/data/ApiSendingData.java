package com.mhdi.tapesh.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiSendingData {

   // public static String BASE_URL="http://192.168.1.185/tapesh/public/api/register/";
  //  public static String BASE_URL="http://192.168.1.185/update_info.php";
   // public static String BASE_URL="http://192.168.1.2/update_info.php";
    public static String BASE_URL="http://192.168.1.55/update_info.php";





























   /* private Context context;

    public ApiSendingData(Context context) {
        this.context = context;
    }

    public void signUp(JSONObject requestJsonObject, final OnSignupComplate onSignupComplate){

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, "http://192.168.1.185/tapesh/public/api/register/",
                requestJsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean success= response.getBoolean("success");
                    onSignupComplate.onSignUp(success);
Log.e("sucsess","OKKKKKKKKK");
                } catch (JSONException e) {
                    onSignupComplate.onSignUp(true);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onSignupComplate.onSignUp(false);
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }

    public interface OnSignupComplate{
        void onSignUp(boolean success);
    }*/

}
