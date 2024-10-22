package ch.heig.sio.lab1.groupE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked list that allows us to insert element in the middle with o(1) complexity if we keep the node after which we want to insert
 * @param <T> the type of the elements in the list
 */

public class DoublyLinkedList<T> {


    public Iterator<T> iterator() {
        return new DoublyLinkedListIterator();
    }


    class DoublyLinkedListIterator implements Iterator<T> {

        private DoublyLinkedList.Node<T> current;
        public DoublyLinkedListIterator() {
            current = head;
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if(!hasNext()) {
                throw new NoSuchElementException("No next element");
            }
            var old = current;
            current = current.next;
            return old.data;
        }
    }

    Node<T> head;
    Node<T> tail;
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }

        public boolean hasNext() {
            return next != null;
        }


        public boolean hasPrev() {
            return prev != null;
        }

        public Node<T> prev() {
            if (!hasPrev()) {
                throw new NoSuchElementException("No previous element");
            }
            return prev;
        }

        public Node<T> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element");
            }
            return next;
        }
    }

    public void addAfter(Node<T> node, T value) {

        if (node.next == null) {
            addLast(value);
        } else {
            Node<T> newNode = new Node<>(value);
            node.next.prev = newNode;
            newNode.next = node.next;
            newNode.prev = node;
            node.next = newNode;
            size++;
        }
    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;
        return data;
    }

    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }
        T data = tail.data;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;
        return data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

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