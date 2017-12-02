package cofeas.dev.raubb.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ThongTinKhachHang extends AppCompatActivity {

    EditText edtTenKH,edtDiachi,edtSDT;
    Button btnXacnhan,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);

        addControls();
        addEvents();
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
        final String ten = edtTenKH.getText().toString().trim();
        final String sdt = edtSDT.getText().toString().trim();
        final String dc = edtDiachi.getText().toString().trim();
        if(ten.length()>0 && sdt.length()>0 && dc.length()>0){
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
//                                if(response.equals("1")){
                                    MainActivity.arrCart.clear();
                                    Toast.makeText(getApplicationContext(),"mua hàng thành công",Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),"Mời bạn đợi trong lúc chúng tôi gọi lại xác nhận!",Toast.LENGTH_LONG).show();
//
//                                }else {
//                                    Toast.makeText(getApplicationContext(),"dữ liệu giỏ hàng lỗi",Toast.LENGTH_LONG).show();
//                                }
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
                    hashMap.put("sodienthoai",sdt);
                    hashMap.put("diachi",dc);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(getApplicationContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_LONG);
        }
    }

    private void addControls() {
        edtTenKH = findViewById(R.id.edtTenKH);
        edtDiachi = findViewById(R.id.edtDiachi);
        edtSDT = findViewById(R.id.edtSDT);
        btnBack = findViewById(R.id.btnBack);
        btnXacnhan = findViewById(R.id.btnXacnhan);
    }
}
