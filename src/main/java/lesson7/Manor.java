package lesson7;


import lesson7.util.Queue;
import lesson7.util.Stack;

public class Manor {

    private static class Graph {
        private class Vertex {
            char label;
            boolean wasVisited;
            int prev;

            public Vertex(char label) {
                this.label = label;
                this.wasVisited = false;
                this.prev = -1; // добавил ссылку на вершину, из которой мы попали в текущую при обходе
                // это для того, чтобы потом можно было вернуть весь маршрут
            }

            @Override
            public String toString() {
                return String.format("V=%c", label);
            }
        }

        private final int MAX_VERTICES = 16;
        private Vertex[] vertexList;
        private int[][] adjacencyMatrix;
        private int currentSize;

        public Graph() {
            vertexList = new Vertex[MAX_VERTICES];
            adjacencyMatrix = new int[MAX_VERTICES][MAX_VERTICES];
            currentSize = 0;
        }
        public void addVertex(char label) {
            vertexList[currentSize++] = new Vertex(label);
        }
        public void addEdge(int start, int end) {
            adjacencyMatrix[start][end] = 1; // change 1 to weight for weight
            adjacencyMatrix[end][start] = 1; // delete this for direction
        }
        public void displayVertex(int v) {
            System.out.print(vertexList[v] + " ");
        }
        private int getUnvisitedVertex(int current) {
            for (int i = 0; i < currentSize; i++) {
                if (adjacencyMatrix[current][i] == 1 &&
                        !vertexList[i].wasVisited) {
                    return i;
                }
            }
            return -1;
        }
        public void depthTraverse() {
            Stack stack = new Stack(MAX_VERTICES);
            vertexList[0].wasVisited = true;
            displayVertex(0);
            stack.push(0);
            while (!stack.isEmpty()) {
                int v = getUnvisitedVertex(stack.peek());
                if (v == -1) {
                    stack.pop();
                } else {
                    vertexList[v].wasVisited = true;
                    displayVertex(v);
                    stack.push(v);
                }
            }
            resetFlagsAndPrevs();
        }

        // в метод передаём номер вершины
        public void widthTraverse(int i) {
            Queue queue = new Queue(MAX_VERTICES);
            vertexList[0].wasVisited = true;
            queue.insert(0); // ищем также начиная с нулевой вершины

            // цикл теперь while-true
            while (true) {

                // выходим теперь внутри цикла, когда очередь становится пустая, значит вершина не найдена
                if (queue.isEmpty()) {
                    System.out.println("Вершина не найдена!");
                    resetFlagsAndPrevs();
                    break;
                }

                int current = queue.remove();
//                displayVertex(current); // закомментил вывод всех вершин, чтобы печатался только путь к найденной

                // если мы нашли нужную вершину
                if (current == i)  {
                    // сообщаем об этом
                    System.out.println("\nНайдена вершина: " + vertexList[i]);
                    System.out.print("Путь к вершине: ");
                    // выводим путь к найденной вершине через метод displayPrevVertex()
                    displayPrevVertex(current);
                    break;
                }

                int next;
                while ((next = getUnvisitedVertex(current)) != -1) {
                    // при обходе также заполняем ссылку на предыдущю веришну,
                    // она пока ещё хранится в current
                    vertexList[next].prev = current;
                    vertexList[next].wasVisited = true;
                    queue.insert(next);
                }
            }
            resetFlagsAndPrevs();
        }

        // в метод сброса флагов добавил сброс предыдущих вершин
        private void resetFlagsAndPrevs() {
            for (int i = 0; i < currentSize; i++) {
                vertexList[i].wasVisited = false;
                vertexList[i].prev = -1;
            }
        }

        // метод печати пути к найденной вершине
        private void displayPrevVertex(int i) {
            // если бы дошли до стартовой вершины (у неё мы не меняли ссылку на предыдщую вершину)
            // то просто печатаем её
            if (vertexList[i].prev == -1) {
                System.out.print(vertexList[i]);
            } else {
                // иначе, печатаем переданную вершину
                displayPrevVertex(vertexList[i].prev);
                System.out.print(" -> "); // стрелочка для красоты
                System.out.print(vertexList[i]); // и опять вызвываем метод печати, передвая номер предыдущей вершины
                // так в рекурсии, будем передивагаться в обратной направлении, пока не дойдем до стартового элемента
                // где prev == -1
                // стек вызовов развернётся в обратном порядке
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        g.addVertex('a');
        g.addVertex('b');
        g.addVertex('c');
        g.addVertex('d');
        g.addVertex('e');
        g.addVertex('f');
        g.addVertex('g');
        g.addVertex('h');
        g.addVertex('i');
        g.addVertex('j');
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(3, 4);
        g.addEdge(0, 4);

        g.addEdge(4, 5);
        g.addEdge(5, 7);
        g.addEdge(7, 6);
        g.addEdge(6, 8);
        g.addEdge(8, 9);
        g.addEdge(7, 9);
        g.depthTraverse();
        System.out.println();
        g.widthTraverse(10); // здесь ничего не будет найдено
        g.widthTraverse(9); // а здесь будет путь V=a -> V=e -> V=f -> V=h -> V=j
    }
}