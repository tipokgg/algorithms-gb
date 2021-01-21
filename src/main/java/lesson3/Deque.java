package lesson3;

public class Deque {

    private int[] queue;
    private int head;
    private int tail;
    private int capacity;

    public Deque (int initial) {
        queue = new int[initial];
        head = 0;
        tail = -1;
        capacity = 0;
    }

    public boolean isEmpty() {
        return capacity == 0;
    }

    public boolean isFull() {
        return capacity == queue.length;
    }

    public int length() {
        return capacity;
    }

    public void addLast(int i) { // добавление последним элементом в очередь
        if (isFull())
            throw new RuntimeException("Queue is full!");
        if (tail == queue.length -1)
            tail = -1;
        queue[++tail] = i;
        capacity++;
    }

    public void addFirst(int i) { // добавляем первым элементов в очередь
        if (isFull())
            throw new RuntimeException("Queue is full!");

        if (head != 0) { // если "голова" не на нулевом индексе
            queue[--head] = i; // то добавляем в элемент слева от "головы" и сдвигаем индекс
        } else {
            int[] temp = queue; // если "голова" на нулевом индексе
            // то копируем весь массив, сдвигая вправо на один элемент
            System.arraycopy(temp, 0, queue, 1, tail+1);
            queue[0] = i; // в нулевой элемент помещаем переднный
            tail++; // и двигаем хвост
        }
        capacity++;
    }

    public int removeLast() { // удаление последнего элемента
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        capacity--; // уменьшаем размер
        return queue[tail--]; // возращаем элемент из "хвоста" и сдвгаем его индекс
    }

    public int removeFirst() { // удаление первого элемента
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        int temp = queue[head++]; // возвращаем элемент из головы и двигаем голову
        head %= queue.length; //if (head == queue.length) head = 0;
        capacity--; // уменьшка размер
        return temp;
    }

    public int getFirst() { // забираем первый элемент без удаления
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return queue[head];
    }

    // забираем последний элемент без удаления
    public int getLast() {
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return queue[tail];
    }

    // переопределние toString() для удобства
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = head; i <= tail; i++) {
            sb.append(queue[i]);
            if (i != tail) sb.append(", ");
        }

        sb.append("]");

        return sb.toString();
    }

    // в мейне проверяю как отрабатывает "дека"
    public static void main(String[] args) {
        Deque deque = new Deque(10);

        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);

        deque.addFirst(0);
        deque.addFirst(-1);
        deque.addLast(-2);
        deque.addFirst(88);
        System.out.println(deque);
        deque.removeFirst();
        deque.addFirst(14);
        deque.addFirst(3333);
        deque.addFirst(5555);
        deque.addFirst(14141414);
        System.out.println(deque);
        System.out.println(deque.removeLast());
        System.out.println(deque);
        System.out.println(deque.getFirst());
        System.out.println(deque.getLast());
    }
}

