package com.example.b0917.tp_tea.models;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class menu_category implements Serializable{
    public String category;
    public ArrayList<menu_drinks> item;

    public menu_category(String category,ArrayList<menu_drinks> item) {
        this.category = category;
        this.item = item;
    }
}
