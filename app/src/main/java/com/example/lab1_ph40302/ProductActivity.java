package com.example.lab1_ph40302;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1_ph40302.adapter.ProductAdapter;
import com.example.lab1_ph40302.dao.CatDAO;
import com.example.lab1_ph40302.dao.ProductDAO;
import com.example.lab1_ph40302.model.CatDTO;
import com.example.lab1_ph40302.model.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.OnProductActionListener {

    private ListView lvProduct;
    private Button btnAddProduct;
    private ProductAdapter adapter;
    private ProductDAO productDAO;
    private CatDAO catDAO;
    private List<ProductDTO> productList;
    private List<CatDTO> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Ánh xạ view
        lvProduct = findViewById(R.id.lvProduct);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        // Khởi tạo DAO
        productDAO = new ProductDAO(this);
        catDAO = new CatDAO(this);

        // Lấy danh sách category
        categoryList = catDAO.getAllCats();

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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("THÊM SẢN PHẨM MỚI");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_product, null);
        builder.setView(dialogView);

        EditText edtName = dialogView.findViewById(R.id.edtProductName);
        EditText edtPrice = dialogView.findViewById(R.id.edtProductPrice);
        Spinner spinnerCat = dialogView.findViewById(R.id.spinnerCategory);

        // Setup spinner với danh sách category
        setupCategorySpinner(spinnerCat);

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String name = edtName.getText().toString().trim();
            String priceStr = edtPrice.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                CatDTO selectedCat = (CatDTO) spinnerCat.getSelectedItem();

                ProductDTO newProduct = new ProductDTO(name, price, selectedCat.getId());
                long result = productDAO.addProduct(newProduct);

                if (result > 0) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }

    private void showEditProductDialog(ProductDTO product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SỬA SẢN PHẨM");

        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_product, null);
        builder.setView(dialogView);

        EditText edtName = dialogView.findViewById(R.id.edtProductName);
        EditText edtPrice = dialogView.findViewById(R.id.edtProductPrice);
        Spinner spinnerCat = dialogView.findViewById(R.id.spinnerCategory);

        // Điền dữ liệu cũ
        edtName.setText(product.getName());
        edtPrice.setText(String.valueOf(product.getPrice()));

        // Setup spinner với danh sách category
        setupCategorySpinner(spinnerCat);

        // Chọn category hiện tại
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getId() == product.getIdCat()) {
                spinnerCat.setSelection(i);
                break;
            }
        }

        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            String name = edtName.getText().toString().trim();
            String priceStr = edtPrice.getText().toString().trim();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                CatDTO selectedCat = (CatDTO) spinnerCat.getSelectedItem();

                product.setName(name);
                product.setPrice(price);
                product.setIdCat(selectedCat.getId());

                int result = productDAO.updateProduct(product);

                if (result > 0) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }

    private void setupCategorySpinner(Spinner spinner) {
        List<String> catNames = new ArrayList<>();
        for (CatDTO cat : categoryList) {
            catNames.add(cat.getName());
        }

        ArrayAdapter<CatDTO> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onEdit(ProductDTO product) {
        showEditProductDialog(product);
    }

    @Override
    public void onDelete(ProductDTO product) {
        new AlertDialog.Builder(this)
                .setTitle("XÁC NHẬN XÓA")
                .setMessage("Bạn có chắc muốn xóa \"" + product.getName() + "\"?")
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
