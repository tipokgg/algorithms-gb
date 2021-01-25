package lesson4.homework;

import java.util.Objects;
import java.util.Scanner;

public class DoubleLinkedList {

    private static class Cat {
        int age;
        String name;

        public Cat(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format("Cat(a=%d,n=%s)", age, name);
        }
    }

    private class Iterator {

        // внутри храним текущую ноду
        private Node current;

        // конструктор по умолчанию, т.к. инициализируем итератор при инициализации DLL
        // в этот момент DLL ещё пустой

        // устанавливает итератор в начало списка, т.е. на "голову"
        public void reset() {
            this.current = head;
        }

        // устанавилвает в конец списка, т.е. на хвост
        public void atEnd() {
            this.current = tail;
        }

        // проверяет есть ли следующий элемент
        public boolean hasNext() {
            if (current.next != null) return true; // если есть, то возвращает true
            else return false;
        }

        // перемещает итератор на следующую ноду
        public Node next() {
            Node tmp = current;
            if (hasNext()) this.current = current.next;
            else return null; // возвращает null если её нет
            return tmp;
        }

        // перемещает итератор на предыдущую ноду
        public Node prev() {
            Node tmp = current;
            if (current != head) this.current = current.prev;
            else return null; // возвращает null если итератор уже на "голове"
            return tmp;
        }


        // возвращает объект Cat из текущей ноды
        public Cat getCurrent() {
            return this.current.c;
        }

        // добавляет новый элемент в список перед текущим
        public void insertBefore(Cat c) {
            Node n = new Node(c); // создает новую ноду с объектом внутри
            if (current == head) { // если сейчас текущий элемент это голова
                // то реализует логику метода push();
                // новую ноду делает "головой" и меняет ссылки
                n.next = head;
                n.prev = null;
                if (head != null) head.prev = n;
                else iterator.current = n;
                head = n;
                if (tail == null) tail = n;
            } else { // если это не голова, то производим вставку, меняя ссылки у prev и next
                // плюс добавляем ссылки на prev и next для новой ноды
                n.next = current;
                n.prev = current.prev;
                current.prev.next = n;
                current.prev = n;
            }
        }

        // // добавляет новый элемент в список после текущего
        public void insertAfter(Cat c) {
            Node n = new Node(c);
            if (current == tail) { // если текущий элемент итератора это хвост
                // то новую ноду ставим на место хвоста, меняя ссылки у тещкуго элемента
                // плюс добавляем ссылки к новой ноде
                n.next = null;
                n.prev = current;
                current.next = n;
                tail = n;
            } else { // иначе добавляем элмент по аналогии с insertBefore()
                // и добавляем/меняем нужные ссылки
                n.next = current.next; // следущий элемент новой ноды - это next элемент текущей ноды
                n.prev = current; // предыдущий элемент новой ноды - это текущая нода итератора
                current.next.prev = n; // ссылка на предыдущую ноду у next ноды текущего элемента итератора - это новая нода
                current.next = n; // также next элемент текущей ноды становится новая нода
            }
        }

        // удаление текущего элемента итератора
        public Node deleteCurrent() {
            if (isEmpty()) return null;

            // удаление "головы"
            if (current == head) {
                Node tmp = head;
                head = head.next;
                head.prev = null;
                return tmp;
                // удаление хвоста
            } else if (current == tail) {
                Node tmp = tail;
                tail = tail.prev;
                tail.next = null;
                return tmp;
            } else { // удаление элемента где то в середине
                Node tmp = current; // записываем ссылку во временную переменную
                // меняем ссылки у next и prev эементов
                current.prev.next = current.next;
                current.next.prev = current.prev;
                return tmp;
            }

        }

    }

    private static class Node { // нода, написанная на уроке для SLL
        Cat c;
        Node next;
        Node prev;

        public Node(Cat c) {
            this.c = c;
        }

        @Override
        public String toString() {
            return String.format("Node(c=%s)", c);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(c, node.c);
        }
    }

    private Node head;
    private Node tail; // добавился указатель на "хвост"
    private final Iterator iterator; // добавилась сущность Итератор

    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
        this.iterator = new Iterator(); // создаем итератор при создании DLL
    }

    public boolean isEmpty() {
        return head == null;
    }

    // добавление нового элемента в "голову" списка
    public void push(Cat c) {
        Node n = new Node(c);
        n.next = head;
        n.prev = null; // предыдущий элемент будет null
        if (head != null) head.prev = n; // если это не первый элемент который добавляем,
        // то заполяем для текущей "головы" поле prev
        head = n; // и делаем "головой" новый элемент
        if (tail == null) {
            tail = n; // если хвоста ещё нет (т.е. это первый элемент) то назначем его хвостом
            iterator.current = n; // и устанавливаем итератор на добавленный первый элемент
        }
    }

    // получаем элемент из "головы"
    public Cat pop() {
        if (isEmpty()) return null;

        Cat temp = head.c;
        head = head.next; // голова теперь next элемент
        head.prev = null; // затираем поле prev
        return temp; // возвращаем котика
    }

    // метод проверки с урока по SLL
    public boolean contains(Cat c) {
        Node n = new Node(c);
        Node current = head;
        while (!current.equals(n)) {
            if (current.next == null) return false;
            else current = current.next;
        }
        return true;
    }

    // удаление элемента
    public Cat delete(Cat c) {
        // поиск с урока
        Node n = new Node(c);
        Node current = head;
        Node previous = null;
        while (!current.equals(n)) {
            if (current.next == null) return null;
            else {
                previous = current;
                current = current.next;
            }
        }

        // удаление элемента
        if (current == head) { // если найденный элемент это "голова"
            return pop(); // то удаляем методом pop()
        } else if (current == tail) { // если хвост
            tail = previous; // делаем хвостом предыдущий элемент
            tail.next = null; // и меняем ссылку на туче
        } else { // удаление из середины
            previous.next = current.next;
            current.next.prev = current.prev;
        }

        return current.c; // возвращаем котика
    }


    // переопредление toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node tmp = head;
        while (true) { // в while-true цилке аппендим в StringBuilder
            sb.append(tmp.c).append("\n"); // текущий элемент
            tmp = tmp.next; // и переключаемся на следующий
            if (tmp == tail) { // пока не дойдём до хвоста
                sb.append(tmp.c); // тогда добавляем его
                break; // и завершаем цикл
            }
        }
        return sb.toString();
    }


    // проверяю работу итератора и DLL в методе main
    public static void main(String[] args) {
        DoubleLinkedList dll = new DoubleLinkedList();

        System.out.println(dll.isEmpty());

        Cat cat1 = new Cat(10, "Barsik");
        Cat cat2 = new Cat(8, "Pushok");
        Cat cat3 = new Cat(5, "Vasya");
        Cat cat4 = new Cat(15, "Trump");

        dll.push(cat1);
        dll.push(cat2);
        dll.push(cat3);
        dll.push(cat4);

        System.out.println(dll.contains(cat4));

        System.out.println(dll);
        dll.iterator.reset();

        System.out.println("=============");

        System.out.println(dll.iterator.getCurrent());

        Cat cat5 = new Cat(22, "OldCat");
        dll.iterator.insertAfter(cat5);

        System.out.println("=============");

        System.out.println(dll);

        System.out.println("=============");
        dll.iterator.reset();
        dll.iterator.next();
        dll.iterator.next();
        System.out.println(dll.iterator.getCurrent());
        System.out.println("Deleted: " + dll.iterator.deleteCurrent());
        dll.iterator.reset();

        System.out.println("=============");

        System.out.println(dll);

        System.out.println("=============");
        System.out.println("Delete without iterator");
        System.out.println("Deleting...: " + dll.delete(cat5));
        System.out.println(dll);
    }
}

