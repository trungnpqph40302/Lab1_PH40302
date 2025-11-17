package com.example.lab1_ph40302.model;

public class CatDTO {
    private int id;
    private String name;

    // Constructor đầy đủ
    public CatDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor không có id (dùng khi thêm mới)
    public CatDTO(String name) {
        this.name = name;
    }

    // Constructor rỗng
    public CatDTO() {
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

    @Override
    public String toString() {
        return "CatDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
