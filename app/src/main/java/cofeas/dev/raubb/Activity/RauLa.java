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
import cofeas.dev.raubb.Model.SanPham;
import cofeas.dev.raubb.R;
import cofeas.dev.raubb.ultil.uri;

public class RauLa extends AppCompatActivity {

    android.support.v7.widget.Toolbar tbRaula;
    ListView lvRaula;
    ArrayList<SanPham> arrRaula;
    RauLaAdapter rauLaAdapter;
    int idrl =0;
    int page =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raul);

        addControls();
        GetIdLoaiSP();
        actionToolbar();
        getData(page);
        lvItemClick();
    }

    private void lvItemClick() {
        lvRaula.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("tt",arrRaula.get(position));
                startActivity(intent);
            }
        });

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
                            arrRaula.add(new SanPham(IDSanPham, IDLoaiSanPham, TenSanPham, ThongTinSanPham, Gia, DonVi, HinhAnh, IDKhuyenMai));
                            rauLaAdapter.notifyDataSetChanged();


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
                param.put("idloaisanpham",String.valueOf(idrl));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void actionToolbar() {
        setSupportActionBar(tbRaula);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbRaula.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdLoaiSP() {
        idrl = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("IDLoaisnaPham", idrl + "");
    }

    private void addControls() {
        tbRaula = findViewById(R.id.tbRaula);
        lvRaula = findViewById(R.id.lvRaula);
        arrRaula = new ArrayList<>();
        rauLaAdapter = new RauLaAdapter(getApplicationContext(),arrRaula);
        lvRaula.setAdapter(rauLaAdapter);
    }
}
