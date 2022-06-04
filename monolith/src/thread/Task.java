package thread;

import datastructures.AVLBSTree;
import model.Counter;
import model.Point;

import java.math.BigInteger;
import java.util.Random;

public class Task implements Runnable {

    private final int numbersToCalculate;
    private final Random r;
    private AVLBSTree<Point, BigInteger> tree;
    private Counter counter;

    public Task(int seed, int numbersToCalculate, AVLBSTree<Point, BigInteger> tree, Counter counter){
        this.r = new Random(seed);
        this.numbersToCalculate = numbersToCalculate;
        this.tree = tree;
        this.counter = counter;
    }

    @Override
    public void run() {

        int pointsInsideCircle = 0;
        for(int i=0; i < this.numbersToCalculate; i++){
            Point p = new Point(r.nextDouble(), r.nextDouble());

            if (Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2) <= 1){
                pointsInsideCircle++;
            }
            tree.add(p, BigInteger.ONE);
        }

        counter.add(pointsInsideCircle);
    }
}
