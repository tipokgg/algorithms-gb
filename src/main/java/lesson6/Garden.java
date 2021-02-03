package lesson6;

import java.util.ArrayList;
import java.util.List;

public class Garden {

    private static class Tree {
        private static class TreeNode implements Comparable {
            private Integer i; // заменил котика на Integer
            public TreeNode left;
            public TreeNode right;

            public TreeNode(Integer i) {
                this.i = i;
            }

            @Override
            public String toString() {
                return String.format("TN(%s)", i);
            }

            @Override
            public int compareTo(Object o) {
                if (!(o instanceof Integer))
                    throw new ClassCastException("Not a int!");
                return i - ((Integer) o);
            }
        }

        TreeNode root;

        public void insert(Integer i) {
            TreeNode node = new TreeNode(i);
            if (root == null) {
                root = node;
            } else {
                TreeNode current = root;
                TreeNode parent;
                while (true) {
                    parent = current;
                    if (i < current.i) {
                        current = current.left;
                        if (current == null) {
                            parent.left = node;
                            return;
                        }
                    } else if (i > current.i) {
                        current = current.right;
                        if (current == null) {
                            parent.right = node;
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        public Integer find(Integer i) {
            TreeNode current = root;
            while (current.i != i) {
                current = (i < current.i) ? current.left : current.right;
                if (current == null) return null;
            }
            return current.i;
        }

        public void preOrderTraverse(TreeNode currentNode) {
            if (currentNode != null) {
                System.out.println(currentNode);
                preOrderTraverse(currentNode.left);
                preOrderTraverse(currentNode.right);
            }
        }

        // LeftRootRight
        // RightLeftRoot
        public void displayTree() {
            preOrderTraverse(root);
        }

        // вычисляем глубину, начиная от переданной ноды
        public int depth(TreeNode node) {
            if (node == null) return 0; // если перели null, то ничего не делаем с глубиной и возвращаем ноль
                // иначе прибавляем 1 к возвращаемому значению глубины
                // и при помощи Math.max, находим наибольшую глубину меджу левой и правой частями,
                // при этом погружаясь в рекурсию пока не дойдем до "листа"
                // когда стек вызовов раскрутится в обратную сторону, из-за прибавлении единицы на каждом уровне
                // мы найдем максимальную глубину
            else return Math.max(depth(node.left), depth(node.right)) + 1;
        }


        // проверяем сбалансировано ли наше дерево
        public boolean isBalanced() {
            // возвращаем булево значение
            // находим разницу между левой и правой частями дерева относительно корневого элемента
            // берём ёё модуль, на случай, если левая часть окажется меньше правой и будет отрицательное занчение
            // и проверяем, больше ли единицы разница глубин правой и левой частей дерева
            return Math.abs(depth(root.left) - depth(root.right)) <= 1;
        }

        public Integer delete(Integer i) {
            TreeNode current = root;
            TreeNode parent = root;
            boolean isLeftChild = true;
            while (current.i != i) {
                parent = current;
                if (i < current.i) {
                    current = current.left;
                    isLeftChild = true;
                } else {
                    current = current.right;
                    isLeftChild = false;
                }
                if (current == null) {
                    return null;
                }
            }
            //leaf
            if (current.left == null && current.right == null) {
                if (current == root) {
                    root = null;
                } else if (isLeftChild) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
            // one ancestor
            else if (current.right == null) {
                if (isLeftChild)
                    parent.left = current.left;
                else
                    parent.right = current.left;
            } else if (current.left == null) {
                if (isLeftChild)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            }
            // two ancestors
            else {
                TreeNode successor = getSuccessor(current);
                if (current == root) {
                    root = successor;
                } else if (isLeftChild) {
                    parent.left = successor;
                } else {
                    parent.right = successor;
                }
                successor.left = current.left;
            }
            return current.i;
        }

        private TreeNode getSuccessor(TreeNode node) {
            TreeNode current = node.right;
            TreeNode parent = node;
            TreeNode successor = node;
            while (current != null) {
                parent = successor;
                successor = current;
                current = current.left;
            }

            if (successor != node.right) {
                parent.left = successor.right;
                successor.right = node.right;
            }
            return successor;
        }
    }

    public static void main(String[] args) {

        // подготавливаем список под наши деревья
        List<Tree> treeRepo = new ArrayList<>(20);

        // в цикле создаём 20 деревьев
        for (int i = 0; i < 20; i++) {
            Tree tree = new Tree(); // создаём
            fillTree(tree); // заполняем статическим методом fillTree()
            treeRepo.add(tree); // помещаем в списко
        }


        // Выводим процент несбалансированных деревьев при помощи статик метода percentOfUnbalance()
        System.out.printf("Percentage of unbalanced trees is %s %%\n", percentOfUnbalance(treeRepo));
    }

    // метод заполнения дерева
    public static void fillTree(Tree tree) {
        // в цикле добавляем 100 элементов в дерево
        for (int i = 0; i < 100; i++) {
            // ненерируем значения в диапазоне от -100 до 100
            tree.insert(((-100) + (int) (Math.random() * ((100 - (-100)) + 1))));
        }
    }


    // подсчёт процента несбалансированных деревьев
    public static double percentOfUnbalance(List<Tree> treeRepo) {

        double count = 0; // счётчик несбалансированных деревьев

        // в цикле проходим по всем элементам переданного списка
        for (Tree tree : treeRepo) {
            // если дерево несбалансированно, то инкриментим счётчик
            if (!tree.isBalanced()) count++;
        }

        // возвращаем процент несбалансированных деревьев у переданного списка
        return count / treeRepo.size() * 100;
    }
}