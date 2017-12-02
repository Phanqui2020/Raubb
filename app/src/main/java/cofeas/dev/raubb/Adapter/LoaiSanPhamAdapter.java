package cofeas.dev.raubb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cofeas.dev.raubb.Model.LoaiSanPham;
import cofeas.dev.raubb.R;

/**
 * Created by falcon on 18/11/2017.
 */

public class LoaiSanPhamAdapter extends BaseAdapter{

    ArrayList<LoaiSanPham> arrlsp;
    Context context;

    public LoaiSanPhamAdapter(ArrayList<LoaiSanPham> arrLsp, Context context) {
        this.arrlsp =arrLsp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrlsp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrlsp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {

        TextView txtLsp;
        ImageView imgLsp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_loaisanpham, null);
            viewHolder.txtLsp = view.findViewById(R.id.txtLsp);
            viewHolder.imgLsp = view.findViewById(R.id.imgLsp);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        LoaiSanPham loaiSanPham = (LoaiSanPham) getItem(i);
        viewHolder.txtLsp.setText(loaiSanPham.getTenloaisanpham());
        Picasso.with(context).load(loaiSanPham.getHinhanhloaisanpham())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgLsp);

        return view;

    }
}
