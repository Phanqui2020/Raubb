package cofeas.dev.raubb.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cofeas.dev.raubb.Adapter.RauLaAdapter;
import cofeas.dev.raubb.Adapter.RauCuAdapter;
import cofeas.dev.raubb.Model.SanPham;
import cofeas.dev.raubb.R;
import cofeas.dev.raubb.ultil.uri;

public class RauCu extends AppCompatActivity {

    android.support.v7.widget.Toolbar tbRaucu;
    ListView lvRaucu;
    ArrayList<SanPham> arrRaucu;
    RauCuAdapter raucuAdapter;
    int idrc =0;
    int page =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raucu);

        addControls();
        GetIdLoaiSP();
        actionToolbar();
        getData(page);
        lvItemClick();



    }

    private void lvItemClick() {
        lvRaucu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("tt",arrRaucu.get(position));
                startActivity(intent);
            }
        });

    }

    private void getData(int Page) {

        //tạo phương thức gửi lên server
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //đường dẫn
        String line = uri.raula + String.valueOf(Page);
        //gửi lên server và đọc dữ liệu dưới dạng json, trả về ở listener
        StringRequest stringRequest = new StringRequest(Request.Method.POST, line, new Response.Listener<String>() {
            @Override
            //chuỗi thông qua biến response
            public void onResponse(String response) {
                //khởi tạo các biến
                int IDLoaiSanPham= 0;
                int IDSanPham = 0;
                String TenSanPham = "";
                String ThongTinSanPham = "";
                Integer Gia = 0;
                String DonVi = "";
                String HinhAnh = "";
                int IDKhuyenMai = 0;
                //nếu có dữ liệu
                if(response != null){
                    try {
                        // đọc từ array rồi đọc lần lượt các object
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0;i<response.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            IDSanPham = jsonObject.getInt("idsp");
                            IDLoaiSanPham = jsonObject.getInt("idlsp");
                            TenSanPham = jsonObject.getString("tensp");
                            ThongTinSanPham = jsonObject.getString("tt");
                            Gia = jsonObject.getInt("gia");
                            DonVi = jsonObject.getString("dv");
                            HinhAnh = jsonObject.getString("hinh");
                            IDKhuyenMai = jsonObject.getInt("idkm");
                            // đưa dữ liệu vảo mảng
                            arrRaucu.add(new SanPham(IDSanPham, IDLoaiSanPham, TenSanPham, ThongTinSanPham, Gia, DonVi, HinhAnh, IDKhuyenMai));
                            //cập nhật lại bản vẽ
                            raucuAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            //post lên
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //<String,String> s1 key, s2 là giá trị truyền lên
                HashMap<String,String> param = new HashMap<String,String>();
                //key trùng với file php
                param.put("idloaisanpham",String.valueOf(idrc));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolbar() {
        setSupportActionBar(tbRaucu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbRaucu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdLoaiSP() {
        idrc = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("IDLoaisnaPham", idrc + "");
    }

    private void addControls() {
        tbRaucu = findViewById(R.id.tbRaucu);
        lvRaucu = findViewById(R.id.lvRaucu);
        arrRaucu = new ArrayList<>();
        raucuAdapter = new RauCuAdapter(getApplicationContext(),arrRaucu);
        lvRaucu.setAdapter(raucuAdapter);
    }
}
