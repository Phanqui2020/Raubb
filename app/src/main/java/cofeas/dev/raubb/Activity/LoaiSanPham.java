package cofeas.dev.raubb.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cofeas.dev.raubb.Adapter.LoaiSanPhamAdapter;
import cofeas.dev.raubb.R;
import cofeas.dev.raubb.ultil.uri;

/**
 * Created by falcon on 19/11/2017.
 */


public class LoaiSanPham extends AppCompatActivity {
    
    TextView txtLsp;
    ImageView imgLsp;
    ListView lvLsp;
    ArrayList<LoaiSanPham> arrLsp;
    LoaiSanPhamAdapter lspAdapter;
    int id = 0;
    String tenlsp = "";
    String hinhanhlsp = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaisanpham);
        
        addControls();
        GetLSP();
    }

    private void GetLSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //đọc mảng
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(uri.lsp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //nếu có dữ liệu
                if(response!=null){
                    //đọc từng object con
                    for (int i = 0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id  = jsonObject.getInt("id");
                            tenlsp = jsonObject.getString("tenlsp");
                            hinhanhlsp = jsonObject.getString("hinhlsp");
                           // arrLsp.add(new LoaiSanPham(id,tenlsp,hinhanhlsp));
                            //update bản vẽ
                            lspAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //   arrLsp.add(4, new LoaiSanPham(0,"Feedback","https://www.mithikode.com/images/icons/about_us.png"));
                    // arrLsp.add(5, new LoaiSanPham(0,"About us","http://1.bp.blogspot.com/-6ZFNNv5tPXQ/Weext4PgRQI/AAAAAAAAtC8/S8V0w4vuNRUJUvK5UGnwn-n4qFiAP8V-wCK4BGAYYCw/s1600/checkmark.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void addControls() {
        
        txtLsp = findViewById(R.id.txtLsp);
        imgLsp = findViewById(R.id.imgLsp);
        lvLsp = findViewById(R.id.lvLsp);

        arrLsp = new ArrayList<>();
       // arrLsp.add(0, new LoaiSanPham(0,"Feedback","https://www.mithikode.com/images/icons/about_us.png"));

       // lspAdapter = new LoaiSanPhamAdapter(arrLsp,getApplicationContext());
        lvLsp.setAdapter(lspAdapter);
    }
}
