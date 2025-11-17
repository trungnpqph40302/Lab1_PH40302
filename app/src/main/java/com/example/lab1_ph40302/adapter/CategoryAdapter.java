package com.example.lab1_ph40302.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lab1_ph40302.R;
import com.example.lab1_ph40302.model.CatDTO;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<CatDTO> categoryList;

    public CategoryAdapter(Context context, List<CatDTO> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
            holder = new ViewHolder();
            holder.tvCatId = convertView.findViewById(R.id.tvCatId);
            holder.tvCatName = convertView.findViewById(R.id.tvCatName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CatDTO category = categoryList.get(position);
        holder.tvCatId.setText("ID: " + category.getId());
        holder.tvCatName.setText(category.getName());

        return convertView;
    }

    static class ViewHolder {
        TextView tvCatId;
        TextView tvCatName;
    }

    // Cập nhật danh sách
    public void updateData(List<CatDTO> newList) {
        this.categoryList = newList;
        notifyDataSetChanged();
    }
}
