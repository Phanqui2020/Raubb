package cofeas.dev.raubb.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cofeas.dev.raubb.Adapter.LoaiSanPhamAdapter;
import cofeas.dev.raubb.Adapter.SanPhamAdapter;
import cofeas.dev.raubb.Model.LoaiSanPham;
import cofeas.dev.raubb.Model.SanPham;
import cofeas.dev.raubb.Model.GioHang;

import cofeas.dev.raubb.R;
import cofeas.dev.raubb.ultil.uri;

public class MainActivity extends AppCompatActivity {

    Toolbar tbMain;
    DrawerLayout dlMain;
    ViewFlipper vfDiscount;
    RecyclerView rvNewPro;
    NavigationView nvMain;
    ListView lvMain;
    ArrayList<SanPham> dsSanPham;
    SanPhamAdapter spAdapter;
    SearchView svSearch;
    ArrayList<LoaiSanPham> arrLsp;
    LoaiSanPhamAdapter lspAdapter;
    int id = 0;
    String tenlsp = "";
    String hinhanhlsp = "";
    public static ArrayList<GioHang> arrCart;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addControls();
        addEvents();
        actionBar();
        getSP();
        getLSP();
        lvItemClick();



    }

    private void lvItemClick() {

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(getApplicationContext(), cofeas.dev.raubb.Activity.GioHang.class);
                        startActivity(intent0);
                        dlMain.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, RauLa.class);
                        intent1.putExtra("idloaisanpham",arrLsp.get(position).getIdloaisanpham());
                        startActivity(intent1);
                        dlMain.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, RauCu.class);
                        intent2.putExtra("idloaisanpham",arrLsp.get(position).getIdloaisanpham());
                        startActivity(intent2);
                        dlMain.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, RauMam.class);
                        intent3.putExtra("idloaisanpham",arrLsp.get(position).getIdloaisanpham());
                        startActivity(intent3);
                        dlMain.closeDrawer(GravityCompat.START);
                        break;
                }

            }
        });
    }

    private void getLSP() {
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
                            arrLsp.add(new LoaiSanPham(id,tenlsp,hinhanhlsp));
                            //update bản vẽ

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    lspAdapter.notifyDataSetChanged();
                    arrLsp.add(4, new LoaiSanPham(0,"Feedback","https://www.mithikode.com/images/icons/about_us.png"));
                    arrLsp.add(5, new LoaiSanPham(0,"About us","http://1.bp.blogspot.com/-6ZFNNv5tPXQ/Weext4PgRQI/AAAAAAAAtC8/S8V0w4vuNRUJUvK5UGnwn-n4qFiAP8V-wCK4BGAYYCw/s1600/checkmark.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    //đọc dữ liệu json nhanh hơn, không cần đọc về rồi khai báo json
    private void getSP() {
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jr = new JsonArrayRequest(uri.sp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    int IDLoaiSanPham= 0;
                    int IDSanPham = 0;
                    String TenSanPham = "";
                    String ThongTinSanPham = "";
                    Integer Gia = 0;
                    String DonVi = "";
                    String HinhAnh = "";
                    int IDKhuyenMai = 0;
                    for (int i = 0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            IDSanPham = jsonObject.getInt("idsp");
                            IDLoaiSanPham = jsonObject.getInt("idlsp");
                            TenSanPham = jsonObject.getString("tensp");
                            ThongTinSanPham = jsonObject.getString("tt");
                            Gia = jsonObject.getInt("gia");
                            DonVi = jsonObject.getString("dv");
                            HinhAnh = jsonObject.getString("hinh");
                            IDKhuyenMai = jsonObject.getInt("idkm");
                            dsSanPham.add(new SanPham(IDSanPham,IDLoaiSanPham,TenSanPham,ThongTinSanPham,Gia,DonVi,HinhAnh,IDKhuyenMai));
                            spAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        rq.add(jr);
    }

    private void actionViewFlipper() {
        ArrayList<Integer> Khuyenmai = new ArrayList<>();
        Khuyenmai.add(R.drawable.a);
        Khuyenmai.add(R.drawable.b);
        Khuyenmai.add(R.drawable.c);
        for (int i =0; i < Khuyenmai.size(); i++){
            ImageView img = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(Khuyenmai.get(i)).into(img);
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            vfDiscount.addView(img);
        }
        vfDiscount.setFlipInterval(5000);
        vfDiscount.setAutoStart(true);
        Animation anim_siR = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation anim_soR = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        vfDiscount.setInAnimation(anim_siR);
        vfDiscount.setOutAnimation(anim_soR);


    }

    private void actionBar() {
        setSupportActionBar(tbMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbMain.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        tbMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlMain.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menucart:
                Intent intent = new Intent(getApplicationContext(), cofeas.dev.raubb.Activity.GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        tbMain = (Toolbar) findViewById(R.id.tbMain);
        dlMain = (DrawerLayout) findViewById(R.id.dlMain);
        lvMain = findViewById(R.id.lvMain);
        nvMain = (NavigationView) findViewById(R.id.nvMain);
        rvNewPro = (RecyclerView) findViewById(R.id.rvNewPro);
        //MaterialSearchView searchView = (MaterialSearchView) findViewById(R.id.svSearch);

        dsSanPham = new ArrayList<>();
        spAdapter = new SanPhamAdapter(MainActivity.this,dsSanPham);
        rvNewPro.setHasFixedSize(true);
        rvNewPro.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        rvNewPro.setAdapter(spAdapter);

        arrLsp = new ArrayList<>();
        arrLsp.add(0, new LoaiSanPham(0,"Giỏ hàng","http://theparadigmshift.com.au/wp-content/uploads/2014/06/icon_trolley.png"));
        lspAdapter = new LoaiSanPhamAdapter(arrLsp,MainActivity.this);
        lvMain.setAdapter(lspAdapter);

        //nếu mảng có dữ liệu thì ko cần tạo dư liệu mới nữa
        if(arrCart!=null){

        }else { //nếu chưa thì cấp phát bộ nhớ
            arrCart = new ArrayList<>();
        }
    }

    private void addEvents() {

    }
}