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
import cofeas.dev.raubb.Adapter.RauMamAdapter;
import cofeas.dev.raubb.Model.SanPham;
import cofeas.dev.raubb.R;
import cofeas.dev.raubb.ultil.uri;

public class RauMam extends AppCompatActivity {
    android.support.v7.widget.Toolbar tbRaumam;
    ListView lvRaumam;
    ArrayList<SanPham> arrRaumam;
    RauMamAdapter rauMamAdapter;
    int idrm =0;
    int page =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raumam);

        addControls();
        GetIdLoaiSP();
        actionToolbar();
        getData(page);
        lvItemClick();
    }

    private void lvItemClick() {
        lvRaumam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("tt",arrRaumam.get(position));
                startActivity(intent);
            }
        });
        Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);

    }

    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String line = uri.raula + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, line, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int IDLoaiSanPham= 0;
                int IDSanPham = 0;
                String TenSanPham = "";
                String ThongTinSanPham = "";
                Integer Gia = 0;
                String DonVi = "";
                String HinhAnh = "";
                int IDKhuyenMai = 0;
                if(response != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            IDSanPham = jsonObject.getInt("idsp");
                            IDLoaiSanPham = jsonObject.getInt("idlsp");
                            TenSanPham = jsonObject.getString("tensp");
                            ThongTinSanPham = jsonObject.getString("tt");
                            Gia = jsonObject.getInt("gia");
                            DonVi = jsonObject.getString("dv");
                            HinhAnh = jsonObject.getString("hinh");
                            IDKhuyenMai = jsonObject.getInt("idkm");
                            arrRaumam.add(new SanPham(IDSanPham, IDLoaiSanPham, TenSanPham, ThongTinSanPham, Gia, DonVi, HinhAnh, IDKhuyenMai));
                            rauMamAdapter.notifyDataSetChanged();


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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idloaisanpham",String.valueOf(idrm));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void actionToolbar() {
        setSupportActionBar(tbRaumam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbRaumam.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdLoaiSP() {
        idrm = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("IDLoaisnaPham", idrm + "");
    }

    private void addControls() {
        tbRaumam = findViewById(R.id.tbRaumam);
        lvRaumam = findViewById(R.id.lvRaumam);
        arrRaumam = new ArrayList<>();
        rauMamAdapter = new RauMamAdapter(getApplicationContext(),arrRaumam);
        lvRaumam.setAdapter(rauMamAdapter);
    }
}
