import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Quang Nguyen
 * @version 1.0
 * @userid qnguyen305
 * @GTID 903770019
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }

        this.backingArray = (T[]) new Comparable[(2 * data.size()) + 1];

        for (int i = 1; i <= data.size(); i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException("element " + i + " is null");
            }

            this.backingArray[i] = data.get(i - 1);
        }
        this.size = data.size();

        for (int i = this.size / 2; i > 0; i--) {
            buildHeap(i);
        }
    }

    /**
     *
     *
     * @param currIndex the currIndex heap
     * @return true or false
     */

    private boolean buildHeap(int currIndex) {
        int leftChild = currIndex * 2;
        int rightChild = currIndex * 2 + 1;
        int smallestChild = currIndex;

        if ((leftChild > this.size || rightChild > this.size)
                || (this.backingArray[leftChild] == null && this.backingArray[rightChild] == null)) {
            return false;
        } else if (this.backingArray[leftChild] != null && this.backingArray[rightChild] != null) {
            if (this.backingArray[leftChild].compareTo(this.backingArray[rightChild]) < 0) {
                smallestChild = leftChild;
            } else {
                smallestChild = rightChild;
            }
        } else if (this.backingArray[leftChild] == null) {
            smallestChild = rightChild;
        } else if (this.backingArray[rightChild] == null) {
            smallestChild = leftChild;
        }

        T temp = this.backingArray[smallestChild];
        this.backingArray[smallestChild] = this.backingArray[currIndex];
        this.backingArray[currIndex] = temp;


        buildHeap(smallestChild);

        return true;
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }

        if (this.size >= this.backingArray.length) {
            T[] temp = (T[]) new Comparable[2 * this.backingArray.length];
            for (int i = 0; i < this.backingArray.length; i++) {
                temp[i] = this.backingArray[i];
            }
            this.backingArray = temp;
        }

        this.backingArray[this.size + 1] = data;
        this.size++;

        for (int i = this.size; i > 1; i /= 2) {
            if (this.backingArray[i].compareTo(this.backingArray[i / 2]) < 0) {
                T temp = this.backingArray[i];
                this.backingArray[i] = this.backingArray[i / 2];
                this.backingArray[i / 2] = temp;
            }
        }

    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("heap is empty");
        }
        T data = this.backingArray[1];
        this.backingArray[1] = this.backingArray[this.size];
        this.backingArray[this.size] = null;
        this.size--;

        downHeap(1);

        return data;
    }

    /**
     *
     * @param currIndex the current index
     * @return true or false
     */
    private boolean downHeap(int currIndex) {
        int leftChild = currIndex * 2;
        int rightChild = currIndex * 2 + 1;
        int smallestChild = currIndex;

        if ((leftChild > this.backingArray.length || rightChild > this.backingArray.length)
                || (this.backingArray[leftChild] == null && this.backingArray[rightChild] == null)) {
            return false;
        } else if (this.backingArray[leftChild] != null && this.backingArray[rightChild] != null) {
            if (this.backingArray[leftChild].compareTo(this.backingArray[rightChild]) < 0) {
                smallestChild = leftChild;
            } else {
                smallestChild = rightChild;
            }
        } else if (this.backingArray[leftChild] == null) {
            smallestChild = rightChild;
        } else if (this.backingArray[rightChild] == null) {
            smallestChild = leftChild;
        }

        if (this.backingArray[currIndex].compareTo(this.backingArray[smallestChild]) > 0) {
            T temp = this.backingArray[currIndex];
            this.backingArray[currIndex] = this.backingArray[smallestChild];
            this.backingArray[smallestChild] = temp;
        }

        downHeap(smallestChild);

        return true;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("heap is empty");
        }

        return this.backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size <= 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
