package rb20039.darpv2.domain;

public class Vehicle {
    private String name;
    private Location startlocation;
    private int capacity;
    private double speed;

    public Vehicle(String name, Location startlocation, int capacity, double speed) {
        this.name = name;
        this.startlocation = startlocation;
        this.capacity = capacity;
        this.speed = speed;
    }

    public Vehicle() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getStartlocation() {
        return startlocation;
    }

    public void setStartlocation(Location startlocation) {
        this.startlocation = startlocation;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
