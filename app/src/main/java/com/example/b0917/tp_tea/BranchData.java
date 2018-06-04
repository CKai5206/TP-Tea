package com.example.b0917.tp_tea;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.List;

public class BranchData {
    @SerializedName("city")
    private String city;

    @SerializedName("districts")
    private String[] districts;

    @SerializedName("branches")
    private List<Branch> branches;

    public static class Branch{
        @SerializedName("district")
        private String district;

        @SerializedName("storeName")
        private String storeName;

        @SerializedName("phone")
        private String phoneNum;

        @SerializedName("address")
        private String address;

        @SerializedName("coordinate")
        private String coordinate;

        public String getDistrict() {
            return district;
        }

        public String getStoreName() {
            return storeName;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public String getAddress() {
            return address;
        }

        public String getCoordinate() {
            return coordinate;
        }
    }

    public String getCity() {
        return city;
    }

    public String[] getDistricts() {
        return districts;
    }

    public List<Branch> getBranches() {
        return branches;
    }

}
