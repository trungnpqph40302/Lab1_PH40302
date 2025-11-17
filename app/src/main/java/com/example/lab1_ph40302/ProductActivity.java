package com.example.lab1_ph40302;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1_ph40302.adapter.ProductAdapter;
import com.example.lab1_ph40302.dao.ProductDAO;
import com.example.lab1_ph40302.model.ProductDTO;

import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.OnProductActionListener {

    private ListView lvProduct;
    private Button btnAddProduct;
    private ProductAdapter adapter;
    private ProductDAO productDAO;
    private List<ProductDTO> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Ánh xạ view
        lvProduct = findViewById(R.id.lvProduct);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        // Khởi tạo DAO
        productDAO = new ProductDAO(this);

        // Lấy dữ liệu từ database
        loadData();

        // Sự kiện thêm sản phẩm
        btnAddProduct.setOnClickListener(v -> showAddProductDialog());
    }

    private void loadData() {
        productList = productDAO.getAllProducts();
        adapter = new ProductAdapter(this, productList, this);
        lvProduct.setAdapter(adapter);
    }

    private void showAddProductDialog() {
        // Sẽ viết ở bước 8
        Toast.makeText(this, "Chức năng thêm sẽ được viết ở bước 8", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEdit(ProductDTO product) {
        // Sẽ viết ở bước 8
        Toast.makeText(this, "Sửa: " + product.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(ProductDTO product) {
        // Sẽ viết ở bước 8
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa " + product.getName() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    int result = productDAO.deleteProduct(product.getId());
                    if (result > 0) {
                        Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
