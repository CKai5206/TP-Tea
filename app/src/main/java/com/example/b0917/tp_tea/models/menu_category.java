package com.example.b0917.tp_tea.models;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class menu_category {
    public String category_name;
    public ArrayList<menu_drinks> drinks;

    public menu_category(String category_name) {
        this.category_name = category_name;
        this.drinks = new ArrayList<>();
    }
}
