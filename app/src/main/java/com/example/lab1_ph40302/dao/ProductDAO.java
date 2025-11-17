package com.example.lab1_ph40302.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab1_ph40302.database.DbHelper;
import com.example.lab1_ph40302.model.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private DbHelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Thêm product mới
    public long addProduct(ProductDTO product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.PRODUCT_NAME, product.getName());
        values.put(DbHelper.PRODUCT_PRICE, product.getPrice());
        values.put(DbHelper.PRODUCT_ID_CAT, product.getIdCat());

        long result = db.insert(DbHelper.TABLE_PRODUCT, null, values);
        db.close();
        return result;
    }

    // Lấy tất cả products
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DbHelper.TABLE_PRODUCT,
                null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.PRODUCT_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.PRODUCT_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DbHelper.PRODUCT_PRICE));
                int idCat = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.PRODUCT_ID_CAT));

                ProductDTO product = new ProductDTO(id, name, price, idCat);
                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return productList;
    }

    // Lấy product theo id
    public ProductDTO getProductById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ProductDTO product = null;

        Cursor cursor = db.query(DbHelper.TABLE_PRODUCT,
                null,
                DbHelper.PRODUCT_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.PRODUCT_NAME));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DbHelper.PRODUCT_PRICE));
            int idCat = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.PRODUCT_ID_CAT));

            product = new ProductDTO(id, name, price, idCat);
            cursor.close();
        }
        db.close();
        return product;
    }

    // Lấy products theo category
    public List<ProductDTO> getProductsByCat(int idCat) {
        List<ProductDTO> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DbHelper.TABLE_PRODUCT,
                null,
                DbHelper.PRODUCT_ID_CAT + "=?",
                new String[]{String.valueOf(idCat)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.PRODUCT_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.PRODUCT_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DbHelper.PRODUCT_PRICE));

                ProductDTO product = new ProductDTO(id, name, price, idCat);
                productList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return productList;
    }

    // Cập nhật product
    public int updateProduct(ProductDTO product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.PRODUCT_NAME, product.getName());
        values.put(DbHelper.PRODUCT_PRICE, product.getPrice());
        values.put(DbHelper.PRODUCT_ID_CAT, product.getIdCat());

        int result = db.update(DbHelper.TABLE_PRODUCT,
                values,
                DbHelper.PRODUCT_ID + "=?",
                new String[]{String.valueOf(product.getId())});
        db.close();
        return result;
    }

    // Xóa product
    public int deleteProduct(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(DbHelper.TABLE_PRODUCT,
                DbHelper.PRODUCT_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
}
