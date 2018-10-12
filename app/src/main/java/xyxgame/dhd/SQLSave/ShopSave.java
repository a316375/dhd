package xyxgame.dhd.SQLSave;

public class ShopSave {
    Integer num;
    String name;
    int nums;

    @Override
    public String toString() {
        return "\nShopSave{" +
                "num=" + num +
                ", name='" + name + '\'' +
                ", nums=" + nums +
                '}';
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public ShopSave(Integer num, String name, int nums) {

        this.num = num;
        this.name = name;
        this.nums = nums;
    }
}
