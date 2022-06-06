package model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.Semaphore;

public class PIResult implements Counter{

    private static final int MAX_AVAILABLE = 1;

    private final Semaphore semaphore;
    private BigInteger pointsInside;
    private BigInteger n;

    public PIResult(BigInteger n){
        this.semaphore = new Semaphore(PIResult.MAX_AVAILABLE, true);
        this.pointsInside = BigInteger.ZERO;

        this.n = n;
    }

    @Override
    public void add(int q){
        try {
            this.semaphore.acquire();
            this.pointsInside  = this.pointsInside.add(new BigInteger(q + ""));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }
    }

    @Override
    public BigDecimal getResult(){
        BigDecimal pi = (new BigDecimal(this.pointsInside).divide(new BigDecimal(this.n))).multiply(new BigDecimal(4));
        return pi;
    }

}
