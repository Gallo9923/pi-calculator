package model;

import java.math.BigDecimal;

public class PiResult {

    public static BigDecimal getResult(String pointsInside, String n){
        BigDecimal pi = (new BigDecimal(pointsInside).divide(new BigDecimal(n))).multiply(new BigDecimal(4));
        return pi;
    }

}
