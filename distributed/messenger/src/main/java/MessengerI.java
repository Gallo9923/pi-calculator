import Pi.TaskReportPrx;
import com.zeroc.Ice.Current;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class MessengerI implements Pi.Messenger {

    private ArrayList<TaskReportPrx> observers;
    private Semaphore sem;

    public MessengerI(){
        //this.workers = new ArrayList<>();
        this.sem = new Semaphore(1, true);
        this.observers = new ArrayList<>();
    }

    @Override
    public void subscribeListener(TaskReportPrx proxy, Current current) {
        try {
            this.sem.acquire();
            if (!this.observers.contains(proxy)){
                this.observers.add(proxy);
                System.out.println("ATTACHED: " + proxy.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            this.sem.release();
        }
    }

    @Override
    public void unsubscribeListener(TaskReportPrx prox, Current current) {
        try {
            this.sem.acquire();
            boolean removed = this.observers.remove(prox);
            if (removed){
                System.out.println("DETACHED: " + prox.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            this.sem.release();
        }
    }

    @Override
    public void publish(String jobId, Current current) {
        try {
            this.sem.acquire();
            new Thread(() -> publish(jobId)).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            this.sem.release();
        }
    }

    public void publish(String jobId) {
        ArrayList<TaskReportPrx> observersToRemove = new ArrayList<TaskReportPrx>();
        for (TaskReportPrx subscriber : this.observers){
            try {
                System.out.println("Proxy" + subscriber.toString());
                subscriber.notifyTaskAvailable(jobId);
                // TODO: Find the correct exception to catch
            }catch(Exception e){
                e.printStackTrace();
                observersToRemove.add(subscriber);
            }
        }

        for (TaskReportPrx observer : observersToRemove){
            unsubscribeListener(observer, null);
        }
    }

}
