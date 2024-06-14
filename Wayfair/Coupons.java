package Wayfair;

import java.util.Map;
import java.util.HashMap;

class Solution {
    class OnlineCoupon {
        String category;
        String coupon;
        String parent;
        boolean flag;
        public OnlineCoupon(String category, String coupon, String parent, boolean flag) {
            this.category = category;
            this.coupon = coupon;
            this.parent = parent;
            this.flag = flag;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getParent() {
            return this.parent;
        }

        public String getCoupon() {
            return this.coupon;
        }

        public boolean getFlag() {
            return this.flag;
        }
    }

    Map<String, OnlineCoupon> mapping;

    public String populateCoupon(String category) {
        if(mapping.getOrDefault(category, null)==null) return null;
        if(mapping.get(category).getCoupon()!=null && mapping.get(category).getFlag()) return mapping.get(category).getCoupon();
        return populateCoupon(mapping.get(category).getParent());
    }

    public Solution(String[][] categories, String[][] coupons) {
        mapping = new HashMap<String, OnlineCoupon>();
        for(String[] coupon: coupons) {
            String cat = coupon[0];
            String coup = coupon[1];
            mapping.put(cat, new OnlineCoupon(cat, coup, null, true));
        }

        for(String[] category: categories) {
            String par = category[0];
            String child = category[1];
            if(!mapping.containsKey(child)) {
                mapping.put(child, new OnlineCoupon(child, null, par, true));
            } else {
                OnlineCoupon temp = mapping.get(child);
                temp.setParent(par);
                mapping.put(child, temp);
            }
        }

        for(Map.Entry<String, OnlineCoupon> entry: mapping.entrySet()) {
            String category = entry.getKey();
            OnlineCoupon categoryValues = entry.getValue();
            if(categoryValues.getParent()==null) {
                String coupon = populateCoupon(category);
                mapping.put(category, new OnlineCoupon(category, coupon, categoryValues.getParent(), false));
            }
        }
    }

    public String getCoupon(String category) {
        OnlineCoupon temp = mapping.getOrDefault(category, null);
        return temp==null ? "":temp.getCoupon();
    }
}

public class Coupons {
    public static void main(String[] args) {
        String [][] categories = {
            {"Comforter Sets", "Bedding"},
            {"Bedding", "Bed & Bath"},
            {"Bed & Bath", null},
            {"Soap Dispensers", "Bathroom Accessories"},
            {"Bathroom Accessories", "Bed & Bath"},
            {"Toy Organizers", "Baby And Kids"},
            {"Baby And Kids", null}
        };
        
        String [][] coupons = {
            {"Comforter Sets", "Comforters Sale"},
            {"Bedding", "Savings on Bedding"},
            {"Bed & Bath", "Low price for Bed & Bath"}
        };

        Solution sc = new Solution(categories, coupons);
        String output = sc.getCoupon("Toy Organizers");
        System.out.println("output: "+output);
    }
}