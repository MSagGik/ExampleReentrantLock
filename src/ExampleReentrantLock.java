import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExampleReentrantLock {
    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();

        // создание потоков
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                task.firstThread();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                task.secondThread();
            }
        });
        // запуск потоков
        thread1.start();
        thread2.start();
        // ожидание выполнения потоков
        thread1.join();
        thread2.join();

        // проверка выполнения потоков
        task.showCounter();
    }
}
class Task {
    private int counter;
    // ввод нового объекта для избежания deadlock (взаимной блокировки)
    private Lock lock = new ReentrantLock();

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            counter++;
        }
    }
    // создание методов для потоков
    public void firstThread() {
        // только один поток может вызвать метод lock()
        // и если это происходит, то все остальные потоки ждут пока этот метод не закончит работать
        lock.lock(); // избежание deadlock
        increment();
        lock.unlock(); // избежание deadlock
    }
    public void secondThread() {
        lock.lock(); // избежание deadlock
        increment();
        lock.unlock(); // избежание deadlock
    }

    public void showCounter() {
        System.out.println(counter);
    }
}
