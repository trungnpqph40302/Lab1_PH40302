package com.example.lab1_ph40302.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab1_ph40302.database.DbHelper;
import com.example.lab1_ph40302.model.CatDTO;

import java.util.ArrayList;
import java.util.List;

public class CatDAO {
    private DbHelper dbHelper;

    public CatDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Thêm category mới
    public long addCat(CatDTO cat) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.CAT_NAME, cat.getName());

        long result = db.insert(DbHelper.TABLE_CAT, null, values);
        db.close();
        return result;
    }

    // Lấy tất cả categories
    public List<CatDTO> getAllCats() {
        List<CatDTO> catList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DbHelper.TABLE_CAT,
                null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.CAT_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.CAT_NAME));

                CatDTO cat = new CatDTO(id, name);
                catList.add(cat);
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return catList;
    }

    // Lấy category theo id
    public CatDTO getCatById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        CatDTO cat = null;

        Cursor cursor = db.query(DbHelper.TABLE_CAT,
                null,
                DbHelper.CAT_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.CAT_NAME));
            cat = new CatDTO(id, name);
            cursor.close();
        }
        db.close();
        return cat;
    }

    // Cập nhật category
    public int updateCat(CatDTO cat) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.CAT_NAME, cat.getName());

        int result = db.update(DbHelper.TABLE_CAT,
                values,
                DbHelper.CAT_ID + "=?",
                new String[]{String.valueOf(cat.getId())});
        db.close();
        return result;
    }

    // Xóa category
    public int deleteCat(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(DbHelper.TABLE_CAT,
                DbHelper.CAT_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
}
