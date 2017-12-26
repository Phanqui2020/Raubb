package cofeas.dev.raubb.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cofeas.dev.raubb.R;
import cofeas.dev.raubb.ultil.uri;


public class Donhang extends AppCompatActivity {

    EditText edtDiachi;
    Button btnXacnhan,btnBack;
    TextView txttotal,txttenkhachhang,txtiduser;
    long tongtien = 0;
    Toolbar tbdonhang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhang);

        addControls();
        addEvents();
        actionToolbar();
        Confirm();
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });
    }


    private void Confirm() {
        final String ten = txttenkhachhang.getText().toString().trim();
        final String id = txtiduser.getText().toString().trim();
        final String dc = edtDiachi.getText().toString().trim();
        final String total = txttotal.getText().toString().trim();
        if(ten.length()>0 && id.length()>0 && dc.length()>0){
            final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, uri.kh, new Response.Listener<String>() {
                @Override
                public void onResponse(final String iddonhang) {
                    Log.d("iddonhang",iddonhang);
                    if(Integer.parseInt(iddonhang) > 0){
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest request = new StringRequest(Request.Method.POST, uri.ctkh, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                    MainActivity.arrCart.clear();
                                    Toast.makeText(getApplicationContext(),"mua hàng thành công",Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),"vui lòng đợi chúng tôi sẽ gọi lại xác nhận đơn hàng!",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                JSONArray jsonArray = new JSONArray();
                                for(int i = 0;i < MainActivity.arrCart.size();i++){
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("iddonhang",iddonhang);
                                        jsonObject.put("idsanpham",MainActivity.arrCart.get(i).getIdsp());
                                        jsonObject.put("tensanpham",MainActivity.arrCart.get(i).getTensp());
                                        jsonObject.put("giasanpham",MainActivity.arrCart.get(i).getGiasp());
                                        jsonObject.put("soluong",MainActivity.arrCart.get(i).getSoluongsp());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(jsonObject);
                                }
                                HashMap<String,String> hashMap = new HashMap<String,String>();
                                hashMap.put("dt",jsonArray.toString());
                                return hashMap;
                            }
                        };
                        queue.add(request);
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
                    hashMap.put("iduser",id);
                    hashMap.put("diachi",dc);
                    hashMap.put("tongtien",total);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(getApplicationContext(),"Hãy nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
        }
    }

    private void actionToolbar() {
        setSupportActionBar(tbdonhang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbdonhang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(getApplicationContext(), Signin.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        tbdonhang = findViewById(R.id.tbdonhang);
        edtDiachi = findViewById(R.id.edtDiachi);
        btnBack = findViewById(R.id.btnBack);
        btnXacnhan = findViewById(R.id.btnXacnhan);
        txttenkhachhang = findViewById(R.id.txttenkhachhang);
        txttenkhachhang.setText(SharedPrefManager.getInstance(Donhang.this).getUsername());
        txtiduser = findViewById(R.id.txtiduser);
        txtiduser.setText(String.valueOf(SharedPrefManager.getInstance(Donhang.this).getIduser()));
        txttotal = findViewById(R.id.txttotal);
        for(int i = 0; i<MainActivity.arrCart.size();i++){
            tongtien += MainActivity.arrCart.get(i).getGiasp();
        }
        txttotal.setText(tongtien + "");
    }
}
