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

import cofeas.dev.raubb.Model.SanPham;
import cofeas.dev.raubb.R;

/**
 * Created by falcon on 24/11/2017.
 */

public class RauMamAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrRaumam;

    public RauMamAdapter(Context context, ArrayList<SanPham> arrRaumam) {
        this.context = context;
        this.arrRaumam = arrRaumam;
    }

    @Override
    public int getCount() {
        return arrRaumam.size();
    }

    @Override
    public Object getItem(int position) {
        return arrRaumam.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {

        public TextView txtGiarm,txtRaurm;
        public ImageView imgRaumam;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view==null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_raumam,null);
            viewHolder.txtRaurm = view.findViewById(R.id.txtRaurm);
            viewHolder.txtGiarm = view.findViewById(R.id.txtGiarm);
            viewHolder.imgRaumam = view.findViewById(R.id.imgRaumam);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = (SanPham) getItem(position);
        viewHolder.txtRaurm.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiarm.setText(decimalFormat.format(sanPham.getGia()) + "vnđ");
        Picasso.with(context).load(sanPham.getHinhAnh())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgRaumam);
        return view;
    }
}
