package cofeas.dev.raubb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cofeas.dev.raubb.Activity.MainActivity;
import cofeas.dev.raubb.Model.GioHang;
import cofeas.dev.raubb.R;

/**
 * Created by falcon on 22/11/2017.
 */

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrGiohang;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrGiohang) {
        this.context = context;
        this.arrGiohang = arrGiohang;
    }

    @Override
    public int getCount() {
        return arrGiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrGiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txtGiohang,txtGiagh;
        public ImageView imgGiohang;
        public Button btnMinus,btnPlus,btnValue;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_giohang,null);
            viewHolder.txtGiohang = view.findViewById(R.id.txtGiohang);
            viewHolder.txtGiagh = view.findViewById(R.id.txtGiagh);
            viewHolder.imgGiohang = view.findViewById(R.id.imghinhsp);
            viewHolder.btnMinus = view.findViewById(R.id.btnMinus);
            viewHolder.btnPlus = view.findViewById(R.id.btnPlus);
            viewHolder.btnValue = view.findViewById(R.id.btnValue);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.txtGiohang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiagh.setText(decimalFormat.format(gioHang.getGiasp())+ "vnđ");
        Picasso.with(context).load(gioHang.getHinhsp())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgGiohang);
        viewHolder.btnValue.setText(gioHang.getSoluongsp()+ "");
        int sl = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if(sl>= 5){
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
//        }else if(sl<=1)
//        {
//            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        }else if(sl<=1)
        {
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
        }else
        {
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) + 1;
                int slhientai = MainActivity.arrCart.get(position).getSoluongsp();
                long giahientai = MainActivity.arrCart.get(position).getGiasp();
                MainActivity.arrCart.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainActivity.arrCart.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiagh.setText(decimalFormat.format(giamoinhat)+ "vnđ");
                cofeas.dev.raubb.Activity.GioHang.EvenUltil();
                if(slmoinhat>4){
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString()) - 1;
                int slhientai = MainActivity.arrCart.get(position).getSoluongsp();
                long giahientai = MainActivity.arrCart.get(position).getGiasp();
                MainActivity.arrCart.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giahientai * slmoinhat) / slhientai;
                MainActivity.arrCart.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiagh.setText(decimalFormat.format(giamoinhat)+ "vnđ");
                cofeas.dev.raubb.Activity.GioHang.EvenUltil();
                if(slmoinhat>2){
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }else {
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return view;
    }
}
