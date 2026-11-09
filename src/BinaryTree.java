import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Arrays;
public class BinaryTree {

    TreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(TreeNode root) {
        this.root = root;
    }

    /*
    IMPORTANTE: respete EXACTAMENTE los nombres y firmas de los metodos que se
    piden abajo; la revision automatica los busca por nombre. Puede agregar
    todos los metodos auxiliares (privados) que necesite.
    */

    /*
    1. Construccion del arbol: implemente aqui

    public void insertRoot(Object data)
        Si el arbol esta vacio, crea la raiz con 'data'.
        */
    public void insertRoot(Object data){
        if (root == null) {
            root = new TreeNode(data);
        }
    }

    /*
    public TreeNode search(Object data)
        Devuelve el nodo cuyo dato es igual (equals) a 'data', o null si no
        existe.
        */
    public TreeNode search(Object data) {
        return search(root, data);
    }

    private TreeNode search(TreeNode node, Object data) {
        if (node == null) return null;
        if (node.data.equals(data)) return node;

        TreeNode found = search(node.left, data);
        if (found != null) return found;

        return search(node.right, data);
    }




     /*

    public boolean insertLeft(Object parent, Object data)
        Agrega 'data' como hijo izquierdo del nodo cuyo dato es 'parent'.
        Devuelve false (y no modifica nada) si 'parent' no existe o si ya
        tiene hijo izquierdo; true en otro caso.
  */

    public boolean insertLeft(Object parent, Object data) {
        TreeNode p = search(parent);
        if (p == null || p.left != null) return false;
        p.left = new TreeNode(data);
        return true;
    }

/*
    public boolean insertRight(Object parent, Object data)
        Igual que insertLeft, pero con el hijo derecho.
  */
    public boolean insertRight(Object parent, Object data) {
        TreeNode p = search(parent);
        if (p == null || p.right != null) return false;
        p.right = new TreeNode(data);
        return true;
    }


/*
    public void buildTree(Scanner scanner)
        Construye el arbol leyendo los datos con 'scanner' (en el main se le
        pasa new Scanner(System.in)). Primero se lee la raiz y luego, por
        niveles, el hijo izquierdo y el hijo derecho de cada nodo
        ("Hijo izquierdo de A: ", "Hijo derecho de A: ", ...). Una linea
        vacia o "-" significa que ese hijo no existe.
    */

    public void buildTree(Scanner scanner) {
        System.out.print("Raiz: ");
        String valor = scanner.nextLine().trim();
        if (valor.isEmpty() || valor.equals("-")) {
            root = null;
            return;
        }

        root = new TreeNode(valor);
        Queue<TreeNode> cola = new LinkedList<>();
        cola.add(root);

        while (!cola.isEmpty()) {
            TreeNode actual = cola.poll();

            System.out.print("Hijo izquierdo de " + actual.data + ": ");
            String izq = scanner.nextLine().trim();
            if (!izq.isEmpty() && !izq.equals("-")) {
                actual.left = new TreeNode(izq);
                cola.add(actual.left);
            }

            System.out.print("Hijo derecho de " + actual.data + ": ");
            String der = scanner.nextLine().trim();
            if (!der.isEmpty() && !der.equals("-")) {
                actual.right = new TreeNode(der);
                cola.add(actual.right);
            }
        }
    }

    /*
    2. Visualizacion: implemente aqui

    public void printTree()
        Muestra el arbol en consola de forma grafica, indicando claramente
        la relacion entre padres e hijos.
    */

    public int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }



    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    // Dibuja el subárbol y devuelve la columna donde quedó 'node'
    private int dibujar(TreeNode node, int nivel, char[][] lienzo, int[] contador) {
        int colIzq = -1, colDer = -1;

        if (node.left != null) colIzq = dibujar(node.left, nivel + 1, lienzo, contador);

        int col = contador[0]++ * 3;      // columna de este nodo (inorden)
        String texto = String.valueOf(node.data);
        for (int i = 0; i < texto.length() && col + i < lienzo[0].length; i++) {
            lienzo[nivel * 2][col + i] = texto.charAt(i);
        }

        if (node.right != null) colDer = dibujar(node.right, nivel + 1, lienzo, contador);

        // ramas: una fila debajo del nodo
        if (colIzq != -1) lienzo[nivel * 2 + 1][(col + colIzq) / 2] = '/';
        if (colDer != -1) lienzo[nivel * 2 + 1][(col + colDer) / 2 + 1] = '\\';

        return col;
    }

    /*
    3. Recorridos: implemente aqui. Cada recorrido debe ser una funcion
    RECURSIVA independiente (por ejemplo, un metodo privado que reciba un
    TreeNode). Los metodos publicos devuelven la secuencia separada por
    espacios, p.ej. "A B D H I E C F G".

    public String preorder()
    public String inorder()
    public String postorder()
    */
    public String preorder() {
        StringBuilder sb = new StringBuilder();
        preorder(root, sb);
        return sb.toString().trim();
    }

    private void preorder(TreeNode node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.data).append(" ");   // 1. visita el nodo
        preorder(node.left, sb);            // 2. izquierda
        preorder(node.right, sb);           // 3. derecha
    }

    public String inorder() {
        StringBuilder sb = new StringBuilder();
        inorder(root, sb);
        return sb.toString().trim();
    }

    private void inorder(TreeNode node, StringBuilder sb) {
        if (node == null) return;
        inorder(node.left, sb);             // 1. izquierda
        sb.append(node.data).append(" ");   // 2. visita el nodo
        inorder(node.right, sb);            // 3. derecha
    }

    public String postorder() {
        StringBuilder sb = new StringBuilder();
        postorder(root, sb);
        return sb.toString().trim();
    }

    private void postorder(TreeNode node, StringBuilder sb) {
        if (node == null) return;
        postorder(node.left, sb);           // 1. izquierda
        postorder(node.right, sb);          // 2. derecha
        sb.append(node.data).append(" ");   // 3. visita el nodo
    }

    /*
    4. Factores de equilibrio: implemente aqui

    public int height(TreeNode node)
        Altura del subarbol que empieza en 'node' (null tiene altura 0 y una
        hoja altura 1).

    public int balanceFactor(TreeNode node)
        Factor de equilibrio de 'node': height(node.right) - height(node.left).

    public boolean isBalanced()
        true si TODOS los nodos tienen factor de equilibrio -1, 0 o 1.

    public List<Object> unbalancedNodes()
        Lista con los datos de los nodos cuyo factor de equilibrio es menor
        que -1 o mayor que 1 (los que requieren balanceo).

    public void printBalanceFactors()
        Muestra el arbol en consola indicando en cada nodo su factor de
        equilibrio.
    */

    public int balanceFactor(TreeNode node) {
        if (node == null) return 0;
        return height(node.right) - height(node.left);
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(TreeNode node) {
        if (node == null) return true;
        int fb = balanceFactor(node);
        if (fb < -1 || fb > 1) return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }

    public List<Object> unbalancedNodes() {
        List<Object> lista = new ArrayList<>();
        unbalancedNodes(root, lista);
        return lista;
    }

    private void unbalancedNodes(TreeNode node, List<Object> lista) {
        if (node == null) return;
        unbalancedNodes(node.left, lista);
        int fb = balanceFactor(node);
        if (fb < -1 || fb > 1) lista.add(node.data);
        unbalancedNodes(node.right, lista);
    }




    public void printTree() {
        dibujarArbol(false);
    }

    public void printBalanceFactors() {
        dibujarArbol(true);
    }

    private void dibujarArbol(boolean mostrarFB) {
        if (root == null) {
            System.out.println("(arbol vacio)");
            return;
        }
        int alto = height(root);
        int filas = alto * 2 - 1;
        int ancho = countNodes(root) * 7;

        char[][] lienzo = new char[filas][ancho];
        for (char[] fila : lienzo) Arrays.fill(fila, ' ');

        int[] contador = {0};
        dibujar(root, 0, lienzo, contador, mostrarFB);

        for (char[] fila : lienzo) {
            System.out.println(new String(fila).replaceAll("\\s+$", ""));
        }
    }

    private int dibujar(TreeNode node, int nivel, char[][] lienzo, int[] contador, boolean mostrarFB) {
        int colIzq = -1, colDer = -1;

        if (node.left != null) colIzq = dibujar(node.left, nivel + 1, lienzo, contador, mostrarFB);

        int col = contador[0]++ * 7;
        String texto = String.valueOf(node.data);
        if (mostrarFB) texto += "(" + balanceFactor(node) + ")";
        for (int i = 0; i < texto.length() && col + i < lienzo[0].length; i++) {
            lienzo[nivel * 2][col + i] = texto.charAt(i);
        }

        if (node.right != null) colDer = dibujar(node.right, nivel + 1, lienzo, contador, mostrarFB);

        if (colIzq != -1) lienzo[nivel * 2 + 1][(col + colIzq) / 2] = '/';
        if (colDer != -1) lienzo[nivel * 2 + 1][(col + colDer) / 2 + 1] = '\\';

        return col;
    }

    /*
    5. Balanceo AVL: implemente aqui. Cada rotacion recibe la raiz del
    subarbol desbalanceado y devuelve la NUEVA raiz de ese subarbol.

    public TreeNode rotateRight(TreeNode node)       rotacion simple a la derecha
    public TreeNode rotateLeft(TreeNode node)        rotacion simple a la izquierda
    public TreeNode rotateLeftRight(TreeNode node)   rotacion doble izquierda-derecha
    public TreeNode rotateRightLeft(TreeNode node)   rotacion doble derecha-izquierda

    public void balance()
        Identifica los nodos desbalanceados y aplica las rotaciones necesarias
        hasta que el arbol sea AVL (actualiza 'root' si cambia). Debe mostrar
        el arbol con sus factores de equilibrio antes y despues de balancear.
    */

    public TreeNode rotateRight(TreeNode node) {
        TreeNode newRoot = node.left;   // 1. el hijo izquierdo sube
        node.left = newRoot.right;      // 2. el subárbol "del medio" cambia de dueño
        newRoot.right = node;           // 3. el nodo baja a la derecha
        return newRoot;
    }

    public TreeNode rotateLeft(TreeNode node) {
        TreeNode newRoot = node.right;  // 1. el hijo derecho sube
        node.right = newRoot.left;      // 2. el subárbol "del medio" cambia de dueño
        newRoot.left = node;            // 3. el nodo baja a la izquierda
        return newRoot;
    }

    public TreeNode rotateLeftRight(TreeNode node) {
        node.left = rotateLeft(node.left);   // endereza el zigzag
        return rotateRight(node);            // cierra
    }

    public TreeNode rotateRightLeft(TreeNode node) {
        node.right = rotateRight(node.right); // endereza el zigzag
        return rotateLeft(node);              // cierra
    }



    private TreeNode balanceNode(TreeNode node) {
        int fb = balanceFactor(node);

        if (fb < -1) {                                   // pesa a la izquierda
            if (balanceFactor(node.left) <= 0) {
                return rotateRight(node);                // LL
            } else {
                return rotateLeftRight(node);            // LR
            }
        }
        if (fb > 1) {                                    // pesa a la derecha
            if (balanceFactor(node.right) >= 0) {
                return rotateLeft(node);                 // RR
            } else {
                return rotateRightLeft(node);            // RL
            }
        }
        return node;
    }



    private TreeNode balance(TreeNode node) {
        if (node == null) return null;
        node.left = balance(node.left);
        node.right = balance(node.right);
        return balanceNode(node);
    }


    public void balance() {
        if (root == null) {
            System.out.println("(arbol vacio)");
            return;
        }

        System.out.println("--- Arbol original (con factores de equilibrio) ---");
        printBalanceFactors();

        List<Object> malos = unbalancedNodes();
        if (malos.isEmpty()) {
            System.out.println("El arbol ya es AVL, no requiere balanceo.");
            return;
        }
        System.out.println("Nodos que requieren balanceo: " + malos);

        int intentos = 0;
        while (!isBalanced() && intentos < 100) {
            root = balance(root);
            intentos++;
        }

        System.out.println("--- Arbol despues de balancear (" + intentos + " pasada(s)) ---");
        printBalanceFactors();
        System.out.println("Balanceado: " + isBalanced());
    }
}
