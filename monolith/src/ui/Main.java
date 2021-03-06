package ui;

import model.Solver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Main {

    // public static final int EPSILON_POWER = 3;
    // public static final int SEED = 9923;

    public static final int THREAD_NUMBER = 8;
    // public static final BigInteger N = new BigInteger("10000000");  // 10^7 tiempo limite
    public static final BigInteger BATCH_SIZE = new BigInteger("10000");

    private static int epsilon;
    private static int seed;
    private static int n;

    public static void main(String[] args){

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;

        while(!exit) {
            System.out.println("------------- WELCOME TO PI CALCULATOR -------------\n"
                         + "1. SET SEED FOR RANDOM NUMBER GENERATOR.\n"
                         + "2. SET POWER OF 10 FOR NUMBERS TO GENERATE.\n"
                         + "3. SET EPSILON TO CALCULATE REPEATED NUMBERS.\n"
                         + "4. CALCULATE PI.");
            
            try {
                int sel = Integer.valueOf(reader.readLine());

                switch (sel) {
                    case 1:
                        System.out.print("SEED: ");
                        seed = Integer.valueOf(reader.readLine());
                        break;
                    case 2:
                        System.out.print("10^N POWER: ");
                        n = Math.abs(Integer.valueOf(reader.readLine()));
                        break;
                    case 3:
                        System.out.print("EPSILON: ");
                        epsilon = Math.abs(Integer.valueOf(reader.readLine()));
                        break;
                    case 4:
                        System.out.println("Calculating PI...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Unknown option :(");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long t0 = System.currentTimeMillis();

        Solver solver = new Solver(epsilon, seed, new BigInteger("10").pow(n));

        BigDecimal pi = solver.solve();
        System.out.println("PI: " + pi);
        System.out.println("Repeated points %: " + solver.getRepeatedPoints());

        long t1 = System.currentTimeMillis();
        System.out.println("Time taken: " + (t1-t0));
    }

    // private static BigDecimal solve(int seed, BigInteger n, int epsilonPower){

    //     // Initialization
    //     Point.setEpsilonPower(epsilonPower);
    //     Random r = new Random(seed);
    //     ExecutorService pool = Executors.newFixedThreadPool(Main.THREAD_NUMBER);
    //     PIResult result = new PIResult(n);
    //     AVLBSTree<Point, BigInteger> counter = new AVLBSTree<>();

    //     BigInteger numberOfTasks = n.divide(Main.BATCH_SIZE);
    //     for(BigInteger i = BigInteger.ZERO; i.compareTo(numberOfTasks) == -1 ; i = i.add(BigInteger.ONE)){
    //         Task t = new Task(r.nextInt(), BATCH_SIZE.intValue(), counter, result);
    //         pool.execute(t);
    //     }

    //     try {
    //         pool.shutdown();
    //         pool.awaitTermination(30, TimeUnit.DAYS);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }finally {
    //         // TODO: GET FINAL RESULT FROM PI AND TREE
    //         return result.getResult();
    //     }
    // }

//    public static void main(String[] args){
//
//        long t0 = System.currentTimeMillis();
//
//        Random r = new Random(9923);
//        ArrayList<Point> points = new ArrayList<>();
//
//        int power = 1;
//        Point.setEpsilonPower(power);
//
////        int ranges = (int) Math.ceil(1 / epsilon);
//        HashMap<Integer, Integer> counter = new HashMap<>();
//        HashMap<Integer, ArrayList<Point>> counterPoints = new HashMap<>();
//
//
//
//        for(int x=0; x<1000; x++){
//            Point p = new Point(x, r.nextDouble(), r.nextDouble());
//            points.add(p);
//
//            int pHashCode = p.hashCode();
//            if (counter.containsKey(pHashCode)) {
//                counter.put(pHashCode, counter.get(pHashCode) + 1);
//            } else {
//                counter.put(p.hashCode(), 1);
//            }
//
//            if (counterPoints.containsKey(pHashCode)){
//                ArrayList<Point> pts = counterPoints.get(pHashCode);
//                pts.add(p);
//            }else{
//                ArrayList<Point> aux = new ArrayList<>();
//                aux.add(p);
//                counterPoints.put(pHashCode, aux);
//            }
//            //System.out.println(p.hashCode());
//        }
//
//        long t1 = System.currentTimeMillis();
//        System.out.println("Randoms: " + (t1-t0));
//
//        // VALIDATE RESULTS - HashMap Counter;
//
//        int totalMapCounter = 0;
//
//        Iterator it = counter.values().iterator();
//        while(it.hasNext()){
//            Integer val = (int)it.next();
//            totalMapCounter += val;
//        }
//
//        long t2 = System.currentTimeMillis();
//        System.out.println("MAP Counter: " + (t2-t1));
//
//        long t3 = System.currentTimeMillis();
//        // N^2 Counter
//        int iterations = 0;
//        int totalCounter = 0;
//        for (int i=0; i< points.size(); i++){
//            Point p1 = points.get(i);
//            for (int j=i+1; j<points.size(); j++){
//                iterations++;
//                Point p2 = points.get(j);
//                if (p1.equals(p2)){
//                    totalCounter = totalCounter + 1;
//                }
//            }
//        }
//        System.out.println("points size: " + points.size());
//        System.out.println("iterations: " + iterations);
//
//        long t4 = System.currentTimeMillis();
//        System.out.println("N2 Counter: " + (t4-t3));
//        System.out.println(totalMapCounter + " " + totalCounter);
//        System.out.println(totalMapCounter == totalCounter);
//
//
//        // 11 , 93
//        Point p1 = points.get(11);
//        Point p2 = points.get(93);
//        boolean equal = p1.equals(p2);
//
//    }

//    public static void main(String[] args){
//
//        ArrayList<Point> points = new ArrayList<>();
//
//        int power = 1;
//        HashMap<Integer, Integer> counter = new HashMap<>();
//        double epsilon = Math.pow(10, -power);
//
//        for(double x=0; x<1.0; x+=epsilon){
//            for(double y=0; y<1.0; y+=epsilon){
//                for(int i=0; i<2; i++) {
//                    Point p = new Point(x, y, power);
//                    points.add(p);
//
//                    if (counter.containsKey(p.hashCode())) {
//                        counter.put(p.hashCode(), counter.get(p.hashCode()) + 1);
//                    } else {
//                        counter.put(p.hashCode(), 1);
//                    }
//                    System.out.println(p.hashCode());
//                }
//            }
//        }
//
//        // VALIDATE RESULTS - HashMap Counter;
//
//        int totalMapCounter = 0;
//
//        Iterator it = counter.values().iterator();
//        while(it.hasNext()){
//            Integer val = (int)it.next();
//            totalMapCounter += val;
//        }
//
//        // N^2 Counter
//        int totalCounter = 0;
//        for (int i=0; i< points.size(); i++){
//            Point p1 = points.get(i);
//            for (int j=i+1; i< points.size(); i++){
//                Point p2 = points.get(j);
//                if (p1.equals(p2)){
//                    totalCounter++;
//                }
//            }
//        }
//
//        System.out.println(totalMapCounter == totalCounter);
//
//    }

//    public static int getPointHash(Point point, int exponent){
//        double epsilon = Math.pow(10, -exponent);
//
//        short xIndex = (short)getIndex(point.getX(), exponent, epsilon);
//        short yIndex = (short)getIndex(point.getY(), exponent, epsilon);
//
//        String xBits = getBinaryString(xIndex);
//        String yBits = getBinaryString(yIndex);
//
//        String hashBits = xBits + yBits;
//        int hash = Integer.parseInt(hashBits, 2);
//        return hash;
//    }
//
//    public static String getBinaryString(short index){
//        String bits = Integer.toBinaryString((int)index);
//        String zero = "0";
//        int size = 16;
//        int rep = size - bits.length();
//        bits = zero.repeat(rep) + bits;
//        return bits;
//    }
//
//    public static int getIndex(double number, int exponent, double epsilon){
//        double x = truncate(number, exponent);
//        return (int)((x / epsilon) - 1);
//    }
//
//    public static double truncate(double value, int exponent){
//        double powerNumber = Math.pow(10, exponent);
//        return Math.floor( value * powerNumber) / powerNumber ;
//    }

}
