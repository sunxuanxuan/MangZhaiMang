package com.example.mangzhaimang.assistance;

public class Seat {

    private int code;
    private Status status;
    private Cate cate;
    private double X;
    private double Y;

    public enum Status{FILL,EMPTY,MINE};
    public enum Cate{COM,POWER,BASE};

    public Seat(int code, Status status, Cate cate) {
        this.code = code;
        this.status = status;
        this.cate = cate;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCate(Cate cate) {
        this.cate = cate;
    }

    public void setX(double x) {
        X = x;
    }

    public void setY(double y) {
        Y = y;
    }

    public int getCode() {

        return code;
    }

    public Status getStatus() {
        return status;
    }

    public Cate getCate() {
        return cate;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }
}
