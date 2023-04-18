package com.axb.settings.bean;

public class SetItem {
    private int id;
    private  String name;
    private boolean check;//是否选中

    public SetItem() {
    }

    public SetItem(int id) {
        this.id = id;
    }

    public SetItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SetItem(int id, String name, boolean check) {
        this.id = id;
        this.name = name;
        this.check = check;
    }

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

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
