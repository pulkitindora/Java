package Wayfair;

import java.util.*;

interface IPackage {
    int getId();
    double getWeight();
    double getHeight();
    double getWidth();
    double getLength();
    String getType();
    void setId(int id);
    void setWeight(double weight);
    void setHeight(double height);
    void setWidth(double width);
    void setLength(double length);
    void setType(String type);
}

class Package implements IPackage {
    private int id;
    private double weight;
    private double height;
    private double width;
    private double length;
    private String type;

    public Package(int id, double weight, double height, double width, double length, String type) {
        this.id = id;
        this.weight = weight;
        this.height = height;
        this.width = width;
        this.length = length;
        this.type = type;
    }

    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public double getWeight() {
        return this.weight;
    }
    
    @Override
    public double getHeight() {
        return this.height;
    }
    
    @Override
    public double getWidth() {
        return this.width;
    }
    
    @Override
    public double getLength() {
        return this.length;
    }
    
    @Override
    public String getType() {
        return this.type;
    }
    
    @Override
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    @Override
    public void setHeight(double height) {
        this.height = height;
    }
    
    @Override
    public void setWidth(double width) {
        this.width = width;
    }
    
    @Override
    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
}

interface IShipment {
    void insert(IPackage pkg);
    void remove(int id);
    Map<String, Double> getTotalCosts(int distance);
    List<IPackage> getPackages();
}

class Cargo implements IShipment {
    List<IPackage> packs;
    public Cargo() {
        packs = new ArrayList<IPackage>();
    }

    @Override
    public void insert(IPackage pkg) {
        packs.add(pkg);
    }

    @Override
    public void remove(int id) {
        int i = 0;
        while(i<packs.size()) {
            if(packs.get(i).getId()==id) {
                packs.remove(i);
                return;
            }
        } return;
    }

    @Override
    public Map<String, Double> getTotalCosts(int distance) {
        Map<String, Double> result = new HashMap<>();
        double totalTransportCost = 0;
        double totalServiceCost = 0;
        for(IPackage pack: packs) {
            double volume = pack.getHeight()*pack.getWidth()*pack.getLength();
            double weight = pack.getWeight();
            switch(pack.getType().toLowerCase()) {
                case "standard": {
                    totalTransportCost += 0.5*volume;
                    totalServiceCost += 0.5*weight+0.5*distance;
                    break;
                } case "hazardous": {
                    totalTransportCost += 0.75*volume;
                    totalServiceCost += 0.75*weight+0.75*distance;
                    break;
                } case "fragile": {
                    totalTransportCost += 0.625*volume;
                    totalServiceCost += 0.625*weight+0.625*distance;
                    break;
                }
            }
        }

        result.put("TotalTransportCost", totalTransportCost);
        result.put("TotalServiceCost", totalServiceCost);
        return result;
    }

    @Override
    public List<IPackage> getPackages() {
        return this.packs;
    }
}

class TransportAndServiceCost {
    public static void main(String[] args) {
        IPackage pack1 = new Package(1, 10, 2, 3, 4, "standard");
        IPackage pack2 = new Package(2, 20, 3, 4, 5, "hazardous");
        IPackage pack3 = new Package(3, 15, 2.5, 3.5, 4.5, "fragile");

        IShipment cargo = new Cargo();
        cargo.insert(pack1);
        cargo.insert(pack2);
        cargo.insert(pack3);

        int distance = 100;
        Map<String, Double> costs = cargo.getTotalCosts(distance);

        System.out.println("Total Transport Cost: " + costs.get("TotalTransportCost"));
        System.out.println("Total Service Cost: " + costs.get("TotalServiceCost"));
    }
}