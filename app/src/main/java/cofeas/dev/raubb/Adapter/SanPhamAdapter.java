package cofeas.dev.raubb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cofeas.dev.raubb.Activity.ChiTietSanPham;
import cofeas.dev.raubb.Model.SanPham;
import cofeas.dev.raubb.R;

/**
 * Created by falcon on 18/11/2017.
 */

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {

    Context context;
    ArrayList<SanPham> arrsp;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arrsp) {
        this.context = context;
        this.arrsp = arrsp;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sanpham, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        final SanPham sanPham = arrsp.get(position);
        holder.txtTenSP.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGia.setText(decimalFormat.format(sanPham.getGia()) + "vnđ");
        Picasso.with(context).load(sanPham.getHinhAnh())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(holder.imgProduct);

       /* holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tensp",sanPham.getTenSanPham());
                intent.putExtra("giasp",sanPham.getGia());
                intent.putExtra("mota",sanPham.getThongTinSanPham());
                intent.putExtra("hinhanh",sanPham.getHinhAnh());
                context.startActivity(intent);
                Toast.makeText(context,arrsp.get(position).getTenSanPham(),Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return arrsp.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgProduct;
        public TextView txtGia, txtTenSP;

        public ItemHolder(View itemView) {

            super(itemView);

            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtTenSP = (TextView) itemView.findViewById(R.id.txtTenSP);
            txtGia = (TextView) itemView.findViewById(R.id.txtGia);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("tt",arrsp.get(getAdapterPosition()));
                    // Toast.makeText(context,arrsp.get(getAdapterPosition()).getTenSanPham(),Toast.LENGTH_LONG).show();
                    context.startActivity(intent);

                }
            });

        }
    }
}
