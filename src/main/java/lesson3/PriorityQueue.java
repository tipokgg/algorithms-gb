package lesson3;

public class PriorityQueue {
    private static class StringWithPriority {
        private String s; // где бухет храниться сам объект
        private int prio; // его приоритет

        public StringWithPriority(String s, int prio) {
            this.s = s;
            this.prio = prio;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public int getPrio() {
            return prio;
        }

        public void setPrio(int prio) {
            this.prio = prio;
        }

        @Override
        public String toString() {
            return this.s + "=" + this.prio;
        }
    }

    private StringWithPriority[] queue;
    private int head;
    private int tail;
    private int capacity;

    public PriorityQueue(int initial) {
        queue = new StringWithPriority[initial];
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

    // добавление элемента происходит сразу с учётом приоритета
    public void add(StringWithPriority s) {
        if (isFull()) // проверяем что очередь не заполнена
            throw new RuntimeException("Queue is full!");

        if (isEmpty()) { // если очередь пустая, то вставляем в первый элемент
            queue[0] = s;
            tail++;
            capacity++;
            return;
        }

        int insertIndex = 0; // объявляем индекс, куда будем вставлять элемент

        // c хвоста проверяем элементы
        for (int i = tail; i >= head; i--) {
            // если находим такой элемент, приоритет которого больше или равен переданному
            if (s.getPrio() <= queue[i].getPrio()) {
                insertIndex = i + 1;  // то вставляем переданный элемент справа от него
                break; // и выходим из цикла
            }
        } // если не будет найдено таких элементов, то у переданного элемента самый высокий приоритет
        // в insertIndex останется ноль, то есть его нужно вставить в "голову" очереди


        // т.к. "голова" может быть не нулевым индексом, а мы получили в insertIndex ноль, то проверяем это
        if (head != 0 && insertIndex == 0) {
            queue[--head] = s; // и вставляем элемент слева от "головы"
            capacity++;
        } else if (tail != queue.length - 1) { // далее проверяем, есть ли куда двигаться "хвосту"
            // если есть , то записываем ссылку на массив во временную переменную
            StringWithPriority[] temp = queue;
            // выполняем копирование правой части массива (по отношению к insertIndex), сдвигаясь вправо на один элемент
            System.arraycopy(temp, insertIndex, queue, insertIndex + 1, tail - insertIndex + 1);
            tail++; // двигаем "хвост"
            capacity++; // увеличиваем размер
            queue[insertIndex] = s; // в "освободившийся" элемент записываем нужный
        } else { // если "хвост" достиг последнего элемента массива, то будем двигать "голову" влево
            StringWithPriority[] temp = queue;
            // копируем левую часть массива, на один элемент влево
            System.arraycopy(temp, head, queue, head - 1, insertIndex - head);
            head--; // сдвигаем "голову" влево
            capacity++; // увеличиваем размер
            queue[insertIndex - 1] = s; // в освободившийся место записываем переданный элемент
            // при этом insertIndex уменьшаем на 1, т.к. двигаем левую часть массива
        }
    }

    public StringWithPriority remove() { // метод удаления из очереди
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        StringWithPriority temp = queue[head++]; // берём элемент из "головы"
        head %= queue.length;
        capacity--;
        if (capacity == 0) tail = -1; // "сбрасвыаем хвост", если размер стал равен нулю
        return temp;
    }

    public StringWithPriority peek() { // забираем элемент без его удаления из очереди
        if (isEmpty()) throw new RuntimeException("Queue is empty");
        return queue[head];
    }

    // переопределние toString() для удобства
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

    // в мейне проверяю, как отрабатыывает очередь
    public static void main(String[] args) {

        StringWithPriority prion566 = new StringWithPriority("prion566", -566);
        StringWithPriority prio12 = new StringWithPriority("prio12", 12);
        StringWithPriority prio20 = new StringWithPriority("prio20", 20);
        StringWithPriority prio66385 = new StringWithPriority("prio66385", 66385);
        StringWithPriority prio18 = new StringWithPriority("prio18", 18);
        StringWithPriority prio15 = new StringWithPriority("prio15", 15);
        StringWithPriority prio0 = new StringWithPriority("prio0", 0);

        PriorityQueue priorityQueue = new PriorityQueue(5);

        priorityQueue.add(prio20);
        priorityQueue.add(prio66385);
        priorityQueue.add(prio0);
        priorityQueue.add(prio12);
        priorityQueue.add(prion566);

        System.out.println(priorityQueue);

        System.out.println("Removed from queue: " + priorityQueue.remove());

        priorityQueue.add(prio15);

        System.out.println(priorityQueue);

        System.out.println("Removed from queue: " + priorityQueue.remove());
        System.out.println("Removed from queue: " + priorityQueue.remove());
        System.out.println("Removed from queue: " + priorityQueue.remove());

        System.out.println(priorityQueue);
        priorityQueue.add(prio18);
        priorityQueue.add(prion566);
        System.out.println(priorityQueue);

        System.out.println("Peek from queue: " + priorityQueue.peek());

    }
}
