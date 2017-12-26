package cofeas.dev.raubb.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import cofeas.dev.raubb.Adapter.GioHangAdapter;
import cofeas.dev.raubb.R;

/**
 * Created by falcon on 20/11/2017.
 */

public class GioHang extends AppCompatActivity{
    ListView lvGiohang;
    TextView txtThongbao;
    static TextView txtTongtien;
    Button btnThanhtoan,btnTieptuc;
    Toolbar tbGiohang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        adđControls();
        addEvents();
        actionToolbar();
        CheckData();
        Total();
        deleteProduct();
    }

    private void addEvents() {
        btnTieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.arrCart.size()>0){
                    if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                        Intent intent = new Intent(getApplicationContext(),Donhang.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getApplicationContext(),Signin.class);
                        startActivity(intent);
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"Giỏ hàng trống",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void deleteProduct() {
        lvGiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, final long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xóa sản phẩm!");
                builder.setMessage("Bạn có chắc chắn xóa sản phẩm này không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.arrCart.size() <=0){
                            txtThongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.arrCart.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            Total();
                            if(MainActivity.arrCart.size()<=0){
                                txtThongbao.setVisibility(View.VISIBLE);
                            }else {
                                txtThongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                Total();
                            }
                        }
                    }
                });
                  builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          gioHangAdapter.notifyDataSetChanged();
                          Total();
                      }
                  });
                  builder.show();
                return true;
            }
        });
    }

    public static void Total() {
        long tongtien = 0;
        for(int i = 0; i<MainActivity.arrCart.size();i++){
            tongtien += MainActivity.arrCart.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtien) + " vnđ");
    }

    private void CheckData() {

        if(MainActivity.arrCart.size() <= 0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            lvGiohang.setVisibility(View.INVISIBLE);
        }else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            lvGiohang.setVisibility(View.VISIBLE);

        }
    }

    private void actionToolbar() {
        setSupportActionBar(tbGiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbGiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void adđControls() {
        tbGiohang = findViewById(R.id.tbGiohang);
        lvGiohang = findViewById(R.id.lvGiohang);
        txtThongbao = findViewById(R.id.txtThongbao);
        txtTongtien = findViewById(R.id.txtTongtien);
        btnThanhtoan = findViewById(R.id.btnThanhtoan);
        btnTieptuc = findViewById(R.id.btnTieptuc);
        gioHangAdapter = new GioHangAdapter(GioHang.this, MainActivity.arrCart);
        lvGiohang.setAdapter(gioHangAdapter);
    }
}
