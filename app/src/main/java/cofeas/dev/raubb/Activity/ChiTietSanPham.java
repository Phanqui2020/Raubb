package cofeas.dev.raubb.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import cofeas.dev.raubb.Model.SanPham;
import cofeas.dev.raubb.Model.GioHang;
import cofeas.dev.raubb.R;

/**
 * Created by falcon on 18/11/2017.
 */

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarctsp;
    ImageView imgctsp;
    TextView txtTenctsp,txtGiactsp,txtmota;
    Spinner spinner;
    Button btnmuahang;

    int id = 0;
    String Tenchitiet= "";
    int Giachitiet = 0;
    String Hinhanhchitiet= "";
    String thongtinsanpham="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);

        addControls();
        addEvents();
        actionBar();
        GetChiTietsp();
        CatchEventSpinner();


    }


    private void CatchEventSpinner() {
        Integer [] sl = new Integer[]{1,2,3,4,5};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,sl);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetChiTietsp() {
        //
        SanPham sp = (SanPham) getIntent().getSerializableExtra("tt");
        //

        id = sp.getIDSanPham();
        Tenchitiet = sp.getTenSanPham();
        Giachitiet = sp.getGia();
        Hinhanhchitiet = sp.getHinhAnh();
        thongtinsanpham = sp.getThongTinSanPham();

        txtTenctsp.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiactsp.setText(decimalFormat.format(Giachitiet) + " vnđ");
        txtmota.setText(thongtinsanpham);
        Picasso.with(getApplicationContext()).load(Hinhanhchitiet).into(imgctsp);



       /* txtTenctsp.setText(getIntent().getStringExtra("tensp"));
        Log.d("tensp",txtTenctsp + "");
        DecimalFormat decimalFormat = new DecimalFormat("###.###.###");
        txtGiactsp.setText(decimalFormat.format(getIntent().getStringExtra("giasp")) + "vnđ");
        txtmota.setText(getIntent().getStringExtra("mota"));
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("hinhanh")).into(imgctsp);*/
    }

    // back về màn hình trước
    private void actionBar() {
        setSupportActionBar(toolbarctsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarctsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void addEvents() {
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Muahang();
            }
        });
    }

    private void Muahang() {
        //mảng >0
        if(MainActivity.arrCart.size()>0){
            int sl = Integer.parseInt(spinner.getSelectedItem().toString());
            boolean bl = false;
            for(int i =0; i<MainActivity.arrCart.size();i++){
                if(MainActivity.arrCart.get(i).getIdsp() == id){
                    MainActivity.arrCart.get(i).setSoluongsp(MainActivity.arrCart.get(i).getSoluongsp()+sl);
                    if(MainActivity.arrCart.get(i).getSoluongsp()>=10){
                        MainActivity.arrCart.get(i).setSoluongsp(10);
                    }
                    MainActivity.arrCart.get(i).setGiasp(Giachitiet * MainActivity.arrCart.get(i).getSoluongsp());
                    bl =true;
                }
            }
            if (bl==false){
                int solluong = Integer.parseInt(spinner.getSelectedItem().toString());
                long Giamoi = solluong * Giachitiet;
                MainActivity.arrCart.add(new GioHang(id,Tenchitiet,Giamoi,Hinhanhchitiet,solluong));

            }

        }else {
            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
            long Giamoi = soluong * Giachitiet;
            MainActivity.arrCart.add(new GioHang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
        }
        Intent intent = new Intent(getApplicationContext(), cofeas.dev.raubb.Activity.GioHang.class);
        startActivity(intent);
    }

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
        toolbarctsp = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarctsp);
        imgctsp = (ImageView) findViewById(R.id.imgctsp);
        txtTenctsp = (TextView) findViewById(R.id.txtTenctsp);
        txtGiactsp = (TextView) findViewById(R.id.txtGiactsp);
        txtmota = (TextView) findViewById(R.id.txtmota);
        spinner = (Spinner) findViewById(R.id.spinctsp);
        btnmuahang = (Button) findViewById(R.id.btnmuahang);

        //txtTenctsp.setText("abcasmclkams");

     /*   if(getIntent().getSerializableExtra("tt") != null){
            SanPham sanPham = (SanPham) getIntent().getSerializableExtra("tt");

            Toast.makeText(this, sanPham.getGia(), Toast.LENGTH_LONG).show();
            Picasso.with(this).load(uri.cnsanpham + sanPham.getHinhAnh()).into(imgctsp);

            txtTenctsp.setText(getIntent().getStringExtra("tt"));
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txtGiactsp.setText(decimalFormat.format(sanPham.getGia()) + "vnđ");
            txtmota.setText(sanPham.getThongTinSanPham());

        }*/
    }
}
