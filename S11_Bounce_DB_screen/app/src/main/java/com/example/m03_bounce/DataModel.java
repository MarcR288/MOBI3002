package com.example.m03_bounce;

public class DataModel {

    // Data Model for a Ball with position and velocity (x, y, dx, dy)
    private long id;
    float x; // Ball's center (x, y)
    float y;
    float dx; // Ball's speed in the x direction
    float dy; // Ball's speed in the y direction

    public DataModel() {
        this.setId(0);
        this.setModelPosVel(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public DataModel(long id, Float x, Float y, Float dx, Float dy) {
        this.setId(id);
        this.setModelPosVel(x, y, dx, dy);
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "id=" + getId() +
                ", x=" + x +
                ", y=" + y +
                ", dx=" + dx +
                ", dy=" + dy +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setModelPosVel(Float x, Float y, Float dx, Float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public float getModelX() {
        return this.x;
    }

    public float getModelY() {
        return this.y;
    }

    public float getModelDX() {
        return this.dx;
    }

    public float getModelDY() {
        return this.dy; // Corrected to return dy instead of dx
    }
}

