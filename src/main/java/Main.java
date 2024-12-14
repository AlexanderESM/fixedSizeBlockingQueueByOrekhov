public class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new BlockingQueue<>(5);

        // Производитель
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    queue.enqueue(i);
                    System.out.println("Производитель добавил: " + i);
                    Thread.sleep(500); // Имитация работы, поток будет приостановлен на 500 миллисекунд
                }
            } catch (InterruptedException e) { // исключение сигнализирует о том, что текущий поток был прерван другим потоком.
                Thread.currentThread().interrupt();
            }
        });

        // Потребитель
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    int item = queue.dequeue();
                    System.out.println("Потребитель забрал: " + item);
                    Thread.sleep(1000); // Имитация работы
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}

