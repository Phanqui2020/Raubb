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
import java.util.Locale;

import cofeas.dev.raubb.Activity.RauCu;
import cofeas.dev.raubb.Model.SanPham;
import cofeas.dev.raubb.R;

/**
 * Created by falcon on 21/11/2017.
 */

public class RauCuAdapter extends BaseAdapter {
    Context context;
    ArrayList<SanPham> arrRauCu;
    ArrayList<SanPham> arrsearch;

    public RauCuAdapter(Context context, ArrayList<SanPham> arrRauCu) {
        this.context = context;
        this.arrRauCu = arrRauCu;
        this.arrsearch = new ArrayList<SanPham>();
        this.arrsearch.addAll(arrRauCu);
    }

    @Override
    public int getCount() {
        return arrRauCu.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRauCu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        public TextView txtGiarc,txtRaucu;
        public ImageView imgRaucu;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_raucu,null);
            viewHolder.txtRaucu = view.findViewById(R.id.txtRaucu);
            viewHolder.txtGiarc = view.findViewById(R.id.txtGiarc);
            viewHolder.imgRaucu = view.findViewById(R.id.imgRaucu);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.txtRaucu.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiarc.setText(decimalFormat.format(sanPham.getGia()) + "vnđ");
        Picasso.with(context).load(sanPham.getHinhAnh())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgRaucu);
        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (charText.length() == 0) {
            arrRauCu.addAll(arrsearch);
        } else {
            for (SanPham sp : arrsearch) {
                if (sp.getTenSanPham().toLowerCase(Locale.getDefault()).contains(charText)) {
                    arrRauCu.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }
}