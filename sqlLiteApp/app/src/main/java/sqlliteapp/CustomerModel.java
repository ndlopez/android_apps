package moji.physics.sqlliteapp;

public class CustomerModel {
    private int id;
    private String name;
    private int age;
    private int price;//2021-09-13
    private int subTotal;//2021-09-30
    private boolean isActive;
    //constructor

    public CustomerModel(int id, String name, int age, int price,int subTotal,boolean isActive) {
        this.id = id;
        this.name = name;//product name
        this.age = age; //number of items
        this.price=price;//product price/unit 2021-09-13
        this.subTotal=subTotal;
        this.isActive = isActive;//coupon available?
    }

    public CustomerModel(){
    }

    //toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        /*return "CustomerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                '}';*/
        return  name + "  ( " + age + " x " + price + " )"+
                "      " + subTotal + "円";//2021-09-30
    }

    //getters and setters
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSubTotal(){return subTotal;}

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
