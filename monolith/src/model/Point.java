package model;

public class Point {

    private double x;
    private double y;
    private int power;

    private short xIndex;
    private short yIndex;
    private int id;

    public Point(int id, double x, double y, int power){
        this.x = x;
        this.y = y;
        this.power = power;

        this.id = id;
        double epsilon = Math.pow(10, -this.power);
        this.xIndex = (short)getIndex(this.x, power, epsilon);
        this.yIndex = (short)getIndex(this.y, power, epsilon);
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj){
            return true;
        }

        if (!(obj instanceof Point)){
            return false;
        }

        if (obj == null){
            return false;
        }

        Point p2 = (Point)obj;
        if (this.xIndex == p2.xIndex && this.yIndex == p2.yIndex){
            return true;
        }

//        double epsilon = Math.pow(10, -this.power);
//        Point p2 = (Point)obj;
//        if (this.x - p2.getX() <= epsilon){
//            if(this.y - p2.getY() <= epsilon){
//                return true;
//            }
//        }
        return false;
    }

    @Override
    public int hashCode() {

        double epsilon = Math.pow(10, -this.power);

        short xIndex = (short)getIndex(this.x, power, epsilon);
        short yIndex = (short)getIndex(this.y, power, epsilon);

        String xBits = getBinaryString(xIndex);
        String yBits = getBinaryString(yIndex);

        String hashBits = xBits + yBits;
        int hash = Integer.parseInt(hashBits, 2);
        return hash;
    }


    public static String getBinaryString(short index){
        String bits = Integer.toBinaryString((int)index);
        String zero = "0";
        int size = 16;
        int rep = size - bits.length();
        bits = zero.repeat(rep) + bits;
        return bits;
    }

    public static int getIndex(double number, int exponent, double epsilon){
        double x = truncate(number, exponent);
        if (x == 0.0){
            return 0;
        }

        int result = (int)((x / epsilon) - 1);
        return result;
    }

    public static double truncate(double value, int exponent){
        if (value == 0){
            return 0;
        }
        double powerNumber = Math.pow(10, exponent);
        return Math.floor( value * powerNumber) / powerNumber ;
    }
}
