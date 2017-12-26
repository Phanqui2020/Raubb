package cofeas.dev.raubb.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Signin extends AppCompatActivity {


    EditText edtsodienthoai, edtmatkhau;
    Button btndnhap,btnBack;
    TextView txtdky;
    public static final String sodt = "012";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        addControls();
        addEvents();
        Confirm();
    }

    public void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });
        btndnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });
        txtdky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0 = new Intent(getApplicationContext(),Signup.class);
                startActivity(intent0);
            }
        });
    }

    private void Confirm() {
        final String sdt = edtsodienthoai.getText().toString().trim();
        final String pass = edtmatkhau.getText().toString().trim();
        if(sdt.length()>0 && pass.length()>0){
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, uri.login, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   // Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                   // startActivity(intent);
                    JSONObject jObj = null;
                    try {
                        jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        if(!error){
                            SharedPrefManager.getInstance(getApplicationContext())
                                    .userLogin(
                                            jObj.getInt("iduser"),
                                            jObj.getString("tenkhachhang")
                                    );
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra(sodt,edtsodienthoai.getText().toString());
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
                    hashMap.put("sodienthoai",sdt);
                    hashMap.put("matkhau",pass);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(getApplicationContext(),"Hãy đăng nhập để thanh toán",Toast.LENGTH_LONG).show();
        }
    }
    private void addControls() {
        edtsodienthoai = findViewById(R.id.edtsodienthoai);
        edtmatkhau = findViewById(R.id.edtmatkhau);
        btndnhap = findViewById(R.id.btndnhap);
        btnBack = findViewById(R.id.btnmain);
        txtdky = findViewById(R.id.txtdky);
    }
}

