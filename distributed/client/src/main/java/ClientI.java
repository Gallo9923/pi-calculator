import Pi.Result;
import Pi.Time;
import com.zeroc.Ice.Current;

public class ClientI implements Pi.Client {

    @Override
    public void setResult(Result result, Current current) {
        System.out.println("Slkdfndsilfgjzdlkjvksdgf");
        int a = 1/0;
        Time time = result.totalTime;
        System.out.println(
            "========== RESULT ==========\n" +
            "PI: " + result.pi + "\n" +
            "Repeated points %: " + result.repNumbers + "\n" +
            "Minutes: " + time.minutes + "\n" +
            "Seconds: " + time.seconds + "\n" +
            "Millis: " + time.milliseconds + "\n" +
            "========== END RESULT ==========");
    }
}
