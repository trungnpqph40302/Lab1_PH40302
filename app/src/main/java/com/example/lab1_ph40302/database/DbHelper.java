package com.example.lab1_ph40302.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ProductManager.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng
    public static final String TABLE_CAT = "tb_cat";
    public static final String TABLE_PRODUCT = "tb_product";

    // Các cột của tb_cat
    public static final String CAT_ID = "id";
    public static final String CAT_NAME = "name";

    // Các cột của tb_product
    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_ID_CAT = "id_cat";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng tb_cat
        String createCatTable = "CREATE TABLE " + TABLE_CAT + " (" +
                CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAT_NAME + " TEXT NOT NULL)";
        db.execSQL(createCatTable);

        // Tạo bảng tb_product
        String createProductTable = "CREATE TABLE " + TABLE_PRODUCT + " (" +
                PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PRODUCT_NAME + " TEXT NOT NULL, " +
                PRODUCT_PRICE + " REAL NOT NULL, " +
                PRODUCT_ID_CAT + " INTEGER, " +
                "FOREIGN KEY(" + PRODUCT_ID_CAT + ") REFERENCES " +
                TABLE_CAT + "(" + CAT_ID + "))";
        db.execSQL(createProductTable);

        // Thêm dữ liệu mẫu cho tb_cat
        db.execSQL("INSERT INTO " + TABLE_CAT + " (" + CAT_NAME + ") VALUES ('Điện thoại')");
        db.execSQL("INSERT INTO " + TABLE_CAT + " (" + CAT_NAME + ") VALUES ('Laptop')");
        db.execSQL("INSERT INTO " + TABLE_CAT + " (" + CAT_NAME + ") VALUES ('Máy tính bảng')");
        db.execSQL("INSERT INTO " + TABLE_CAT + " (" + CAT_NAME + ") VALUES ('Phụ kiện')");

        // Thêm dữ liệu mẫu cho tb_product
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (" + PRODUCT_NAME + ", " +
                PRODUCT_PRICE + ", " + PRODUCT_ID_CAT + ") VALUES ('iPhone 15', 25000000, 1)");
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (" + PRODUCT_NAME + ", " +
                PRODUCT_PRICE + ", " + PRODUCT_ID_CAT + ") VALUES ('Samsung Galaxy S24', 22000000, 1)");
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (" + PRODUCT_NAME + ", " +
                PRODUCT_PRICE + ", " + PRODUCT_ID_CAT + ") VALUES ('MacBook Pro', 45000000, 2)");
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (" + PRODUCT_NAME + ", " +
                PRODUCT_PRICE + ", " + PRODUCT_ID_CAT + ") VALUES ('Dell XPS 15', 35000000, 2)");
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " (" + PRODUCT_NAME + ", " +
                PRODUCT_PRICE + ", " + PRODUCT_ID_CAT + ") VALUES ('iPad Pro', 28000000, 3)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAT);
        onCreate(db);
    }
}
