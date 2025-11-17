package com.example.lab1_ph40302;


import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1_ph40302.adapter.CategoryAdapter;
import com.example.lab1_ph40302.dao.CatDAO;
import com.example.lab1_ph40302.model.CatDTO;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private ListView lvCategory;
    private CategoryAdapter adapter;
    private CatDAO catDAO;
    private List<CatDTO> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Ánh xạ view
        lvCategory = findViewById(R.id.lvCategory);

        // Khởi tạo DAO
        catDAO = new CatDAO(this);

        // Lấy dữ liệu từ database
        loadData();
    }

    private void loadData() {
        categoryList = catDAO.getAllCats();
        adapter = new CategoryAdapter(this, categoryList);
        lvCategory.setAdapter(adapter);
    }
}