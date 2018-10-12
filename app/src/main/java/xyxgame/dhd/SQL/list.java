package xyxgame.dhd.SQL;

public  class list{
    public    String name;
    public  double price;
    public  boolean ishow;

    public list(String name, double price, boolean ishow) {
        this.name = name;
        this.price = price;
        this.ishow = ishow;
    }

    @Override
    public String toString() {
        return "\nlist{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", ishow=" + ishow +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isIshow() {
        return ishow;
    }

    public void setIshow(boolean ishow) {
        this.ishow = ishow;
    }
}