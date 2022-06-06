package model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import thread.Task;

public class Solver {

    public static final int THREAD_NUMBER = 8;
    // TODO: Change this
    public static final BigInteger BATCH_SIZE = new BigInteger("1000");

    private final int epsilonPower;
    private final int seed;
    private final BigInteger n;
    private RepeatedCounter repCounter;

    public Solver(int epsilonPower, int seed, BigInteger n) {
        this.epsilonPower = epsilonPower;
        this.seed = seed;
        this.n = n;
        repCounter = null;
    }
    
    public BigDecimal solve() {

        if (epsilonPower != 1) {
            repCounter = new RepeatedCounterImp(this.n);
        }

        // Initialization
        Point.setEpsilonPower(this.epsilonPower);
        Random r = new Random(this.seed);
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_NUMBER);
        Counter result = new PIResult(n);

        BigInteger numberOfTasks = n.divide(BATCH_SIZE);
        for(BigInteger i = BigInteger.ZERO; i.compareTo(numberOfTasks) == -1 ; i = i.add(BigInteger.ONE)){
            Task t = new Task(r.nextInt(), BATCH_SIZE.intValue(), repCounter, result);
            pool.execute(t);
        }

        try {
            pool.shutdown();
            pool.awaitTermination(30, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // TODO: GET FINAL RESULT FROM PI AND TREE
            return result.getResult();
        }
    }

    public BigDecimal getRepeatedPoints() {
        if (this.repCounter != null) {
            return this.repCounter.getRepeatedNumPercentage();
        } else {
            return new BigDecimal(-1);
        }
    }
}
