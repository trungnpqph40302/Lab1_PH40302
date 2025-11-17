package com.example.lab1_ph40302.model;

public class ProductDTO {
    private int id;
    private String name;
    private double price;
    private int idCat;

    // Constructor đầy đủ
    public ProductDTO(int id, String name, double price, int idCat) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.idCat = idCat;
    }

    // Constructor không có id (dùng khi thêm mới)
    public ProductDTO(String name, double price, int idCat) {
        this.name = name;
        this.price = price;
        this.idCat = idCat;
    }

    // Constructor rỗng
    public ProductDTO() {
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", idCat=" + idCat +
                '}';
    }
}
