package ch.heig.sio.lab1.groupE;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simply linked list that allows us to insert element in the middle with o(1) complexity if we keep the node after which we want to insert
 *
 * @param <T> the type of the elements in the list
 * @author Rachel Tranchida
 * @author Eva Ray
 */
public class CustomLinkedList<T> {

    // The head of the list
    Node<T> head;
    // The size of the list
    private int size;

    /**
     * Default constructor for the CustomLinkedList
     */
    public CustomLinkedList() {
        head = null;
        size = 0;
    }

    // Iterator class for the CustomLinkedList that implements the Iterator interface
    class CustomLinkedListIterator implements Iterator<T> {

        // The current node in the iteration
        private Node<T> current;

        /**
         * Constructor for the iterator, sets the current node to the head of the list
         */
        public CustomLinkedListIterator() {
            current = head;
        }

        /**
         * Checks if there is a next element in the list
         * @return true if there is a next element, false otherwise
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }
        /**
         * Returns the next element in the list
         * @return the next element in the list
         * @throws NoSuchElementException if there is no next element
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element");
            }
            var old = current;
            current = current.next;
            return old.data;
        }
    }

    /**
     * Class Node that represents a node in the CustomLinkedList
     * @param <T> the type of the data in the node
     */
    public static class Node<T> {

        // The data in the node
        T data;

        // The next node
        Node<T> next;

        /**
         * Constructor for the Node
         * @param data the data contained in the node
         */
        Node(T data) {
            this.data = data;
        }

        /**
         * Checks if there is a next element in the list
         * @return true if there is a next element, false otherwise
         */
        public boolean hasNext() {
            return next != null;
        }


        /**
         * Returns the next element in the list
         * @return the next element in the list
         * @throws NoSuchElementException if there is no next element
         */
        public Node<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element");
            }
            return next;
        }


    }

    /**
     * Returns an iterator for the CustomLinkedList
     * @return an iterator for the CustomLinkedList
     */
    public Iterator<T> iterator() {
        return new CustomLinkedListIterator();
    }

    /**
     * Adds a new element after a given node
     * @param node the node after which we want to insert the new element
     * @param value the value of the new element
     */
    public void addAfter(Node<T> node, T value) {

        Node<T> newNode = new Node<>(value);
        newNode.next = node.next;
        node.next = newNode;
        size++;
    }

    /**
     * Adds a new element at the beginning of the list
     * @param data the value of the new element
     */
    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head != null) {
            newNode.next = head;
        }
        head = newNode;
        size++;
    }

    /**
     * Get the size of the list
     * @return the size of the list
     */
    public int getSize() {
        return size;
    }

    /**
     * Print the list in a readable format
     * @return the list in a readable format
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

}