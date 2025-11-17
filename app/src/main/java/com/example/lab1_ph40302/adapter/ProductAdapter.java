package com.example.lab1_ph40302.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lab1_ph40302.R;
import com.example.lab1_ph40302.model.ProductDTO;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<ProductDTO> productList;
    private OnProductActionListener listener;

    public interface OnProductActionListener {
        void onEdit(ProductDTO product);
        void onDelete(ProductDTO product);
    }

    public ProductAdapter(Context context, List<ProductDTO> productList, OnProductActionListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            holder = new ViewHolder();
            holder.tvProductId = convertView.findViewById(R.id.tvProductId);
            holder.tvProductName = convertView.findViewById(R.id.tvProductName);
            holder.tvProductPrice = convertView.findViewById(R.id.tvProductPrice);
            holder.tvProductCat = convertView.findViewById(R.id.tvProductCat);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductDTO product = productList.get(position);

        // Format giá tiền
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String priceFormatted = formatter.format(product.getPrice()) + " VNĐ";

        holder.tvProductId.setText("ID: " + product.getId());
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText("Giá: " + priceFormatted);
        holder.tvProductCat.setText("Danh mục: ID " + product.getIdCat());

        // Sự kiện nút Sửa
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(product);
            }
        });

        // Sự kiện nút Xóa
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(product);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView tvProductId;
        TextView tvProductName;
        TextView tvProductPrice;
        TextView tvProductCat;
        Button btnEdit;
        Button btnDelete;
    }

    // Cập nhật danh sách
    public void updateData(List<ProductDTO> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }
}
