package Wayfair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Service Class
class CouponService {
    // Product - Model Class
    class Product {
        public String prodName;
        public double price;
        public String catName;
        public Product(String prodName, double price, String catName) {
            this.prodName = prodName;
            this.price = price;
            this.catName = catName;
        }
    }

    // Coupon - Model Class
    class Coupon {
        public String coup;
        public Date date;
        public int discount;
        public boolean percFlag;
        public Coupon(String coup, String date, String discount) {
            this.coup = coup;
            try {
                this.date = new SimpleDateFormat("YYYY-MM-DD").parse(date);
            } catch (Exception e) {
                this.date = null;
            }

            if(discount.charAt(discount.length()-1)=='%') {
                this.percFlag = true;
                this.discount = Integer.valueOf(discount.substring(0, discount.length()-1));
            } else {
                this.percFlag = false;
                this.discount = Integer.valueOf(discount.substring(1, discount.length()));
            }
        }
    }


    Map<String, List<Coupon>> coupsMap; // category -> list of coupons sorted by date
    Map<String, String> catsMap; // category -> childCategory
    Map<String, Product> prodsMap; // prodName -> Product

    // helper method
    public List<Coupon> findCouponHelper(String cat) {
        if(coupsMap.containsKey(cat) && coupsMap.getOrDefault(cat, null)!=null) return coupsMap.get(cat);
        String par = catsMap.getOrDefault(cat, null);
        while(par!=null) {
            List<Coupon> result = coupsMap.getOrDefault(par, null);
            if(result!=null) return result;
            par = catsMap.getOrDefault(par, null);
        } return null;
    }

    // pre-processing
    public CouponService(List<List<String>> coups, List<List<String>> cats, List<List<String>> prods) {
        coupsMap = new HashMap<String, List<Coupon>>();
        catsMap = new HashMap<String, String>();
        prodsMap = new HashMap<String, Product>();

        for(List<String> coup: coups) {
            String category = coup.get(0);
            Coupon couponObj = new Coupon(coup.get(1), coup.get(2), coup.get(3));
            if(couponObj.date.after(new Date())) continue;

            if(!coupsMap.containsKey(category)) coupsMap.put(category, new ArrayList<Coupon>());
            coupsMap.get(category).add(couponObj);
            coupsMap.get(category).sort(new Comparator<Coupon>() {
                    @Override
                    public int compare(Coupon x, Coupon y) {
                        return y.date.compareTo(x.date);
                    }
                }
            );
        }

        for(List<String> cat: cats) {
            this.catsMap.put(cat.get(0), cat.get(1));
        }

        for(Map.Entry<String, String> entry: catsMap.entrySet()) {
            String childCat = entry.getValue();
            List<Coupon> coupon = findCouponHelper(childCat);
            if(coupon==null) {
                coupsMap.put(childCat, null);
                continue;
            }
            coupsMap.put(childCat, new ArrayList<Coupon>(coupon));
        }

        for(List<String> prod: prods) {
            String prodName = prod.get(0);
            double price = Double.valueOf(prod.get(1));
            String catName = prod.get(2);
            if(catName!=null && 
                coupsMap.getOrDefault(catName, null)!=null && 
                coupsMap.getOrDefault(catName, null).get(0)!=null && 
                coupsMap.getOrDefault(catName, null).get(0).coup!=null) {
                    int discount = coupsMap.getOrDefault(catName, null).get(0).discount;
                    boolean percFlag = coupsMap.getOrDefault(catName, null).get(0).percFlag;
                    if(percFlag) price -= (price*discount)/100;
                    else price -= discount;
            }
            prodsMap.put(prodName, new Product(prodName, price, catName));
        }
    }

    
    // method to implement - 1
    public String findCoupon(String cat) {
        List<Coupon> coupons = coupsMap.getOrDefault(cat, null);
        if(coupons==null) return null;
        return coupons.get(0).coup;
    }

    // method to implement - 2
    public double discountPrice(String prodName) {
        if (prodsMap.getOrDefault(prodName, null)==null) return Double.valueOf(0);
        return prodsMap.getOrDefault(prodName, null).price;
    }
} 

// invoker
class FindCoupons {
    public static void main(String[] args) {
        List<List<String>> coups = Arrays.asList(
            Arrays.asList("Comforter Sets", "Comforters Sale", "2020-01-01","10%"),
            Arrays.asList("Comforter Sets", "Cozy Comforter Coupon", "2020-01-01","$15"),
            Arrays.asList("Bedding", "Best Bedding Bargains", "2019-01-01","35%"),
            Arrays.asList("Bedding", "Savings on Bedding", "2019-01-01","25%"),
            Arrays.asList("Bed & Bath", "Low price for Bed & Bath", "2018-01-01","50%"),
            Arrays.asList("Bed & Bath", "Bed & Bath extravaganza", "2019-01-01","75%")
        );

        List<List<String>> cats = Arrays.asList(
            Arrays.asList("Comforter Sets", "Bedding"),
            Arrays.asList("Bedding", "Bed & Bath"),
            Arrays.asList("Bed & Bath", null),
            Arrays.asList("Soap Dispensers", "Bathroom Accessories"),
            Arrays.asList("Bathroom Accessories", "Bed & Bath"),
            Arrays.asList("Toy Organizers", "Baby And Kids"),
            Arrays.asList("Baby And Kids", null)
        );

        List<List<String>> prods = Arrays.asList(
            Arrays.asList("Cozy Comforter Sets","100.00", "Comforter Sets"),
            Arrays.asList("All-in-one Bedding Set", "50.00", "Bedding"),
            Arrays.asList("Infinite Soap Dispenser", "500.00" ,"Bathroom Accessories"),
            Arrays.asList("Rainbow Toy Box","257.00", "Baby And Kids")
        );

        CouponService cs = new CouponService(coups, cats, prods);
        String category = "Bed & Bath";
        String product = "Cozy Comforter Sets";
        System.out.println("Category: "+category+", Coupon: "+cs.findCoupon(category));
        System.out.println("Product: "+product+", Price: "+cs.discountPrice(product));
    }
}
