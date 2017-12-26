package cofeas.dev.raubb.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cofeas.dev.raubb.R;
import cofeas.dev.raubb.ultil.uri;

public class Signup extends AppCompatActivity {

    EditText edtten,edtsdt,edtpass,edtEmail;
    Button btndky,btntrove;
    Toolbar tbdangky;
    TextView txtmsg;
    //String email = edtEmail.getEditableText().toString().trim();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addControls();
        addEvents();
        Confirm();
    }

    private void addEvents() {
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });
        btndky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });


//        edtEmail.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (email.matches(emailPattern) && s.length() > 0)
//                {
//                    txtmsg.setText("valid email");
//                    btndky.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    txtmsg.setText("invalid email");
//                    btndky.setVisibility(View.INVISIBLE);
//                }
//
//            }
//        });
    }

    private void Confirm() {
        final String ten = edtten.getText().toString().trim();
        final String pass = edtpass.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        final String sdt = edtsdt.getText().toString().trim();
        if(ten.length()>0 && sdt.length()>0 && pass.length()>0){
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, uri.register, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jObj = null;
                    try {
                        jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        if (!error){
                            Toast.makeText(getApplicationContext(),"Đăng ký thành công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), Signin.class);
                            startActivity(intent);
                            finish();

                        }else {
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("matkhau",pass);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    Toast.makeText(getApplicationContext(),"Hãy nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }
            }







private void addControls() {
        edtpass = findViewById(R.id.edtpass);
        edtten = findViewById(R.id.edtten);
        edtEmail = findViewById(R.id.edtEmail);
        btndky = findViewById(R.id.btndky);
        edtsdt = findViewById(R.id.edtsdt);
        btntrove = findViewById(R.id.btntrove);
       // txtmsg = findViewById(R.id.txtmsg);
        tbdangky = findViewById(R.id.tbdangky);
    }
}
