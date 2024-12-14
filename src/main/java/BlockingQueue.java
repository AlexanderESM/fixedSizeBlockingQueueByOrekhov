import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    private final int capacity;
    private final Queue<T> queue;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    /**
     * synchronized — ключевое слово, которое используется для обеспечения потокобезопасности (thread safety) при работе с разделяемыми ресурсами в многозадачном
     * (многопоточном) окружении.
     * Оно гарантирует, что доступ к определенному ресурсу или коду будет ограничен только одним потоком в любой момент времени.
     * Когда мы используем synchronized, мы блокируем доступ к методу или блоку кода, чтобы только один поток мог выполнять этот код одновременно.
     * При добавлении ключевого слова synchronized к методу, весь код метода будет выполняться только одним потоком за раз.
     * Если несколько потоков пытаются вызвать этот метод одновременно, они будут выполняться по очереди.
     *
     * @param item
     * @throws InterruptedException
     */
    // Метод для добавления элемента в очередь
    public synchronized void enqueue(T item) throws InterruptedException {
        // Если очередь полна, ждем, пока место освободится
        while (queue.size() == capacity) {
            wait(); // блокировка текущего потока до тех пор, пока не будет выполнено условие
        }
        // Добавляем элемент в очередь
        queue.add(item);
        // Оповещаем один поток, ожидающий извлечения элемента
        notifyAll();
    }

    // Метод для извлечения элемента из очереди
    public synchronized T dequeue() throws InterruptedException {
        // Если очередь пуста, ждем, пока элемент появится
        while (queue.isEmpty()) {
            wait();
        }
        // Извлекаем элемент из очереди
        T item = queue.poll();
        // Оповещаем один поток, ожидающий добавления элемента
        notifyAll();
        return item;
    }

    // Метод для получения текущего размера очереди
    public synchronized int size() {
        return queue.size();
    }
}

