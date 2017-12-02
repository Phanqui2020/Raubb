package cofeas.dev.raubb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cofeas.dev.raubb.Model.LoaiSanPham;
import cofeas.dev.raubb.Model.SanPham;
import cofeas.dev.raubb.R;

/**
 * Created by falcon on 20/11/2017.
 */

public class RauLaAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrRaula;

    public RauLaAdapter(Context context, ArrayList<SanPham> arrRaula) {
        this.context = context;
        this.arrRaula = arrRaula;
    }

    @Override
    public int getCount() {
        return arrRaula.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRaula.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        TextView txtGiarl,txtRaula;
        ImageView imgRaula;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view==null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_raula,null);
            viewHolder.txtRaula = view.findViewById(R.id.txtRaula);
            viewHolder.txtGiarl = view.findViewById(R.id.txtGiarl);
            viewHolder.imgRaula = view.findViewById(R.id.imgRaula);
            view.setTag(viewHolder);
    }else{
        viewHolder = (ViewHolder) view.getTag();
    }

        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.txtRaula.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiarl.setText(decimalFormat.format(sanPham.getGia()) + "vnđ");
        Picasso.with(context).load(sanPham.getHinhAnh())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgRaula);
        return view;
    }
}
