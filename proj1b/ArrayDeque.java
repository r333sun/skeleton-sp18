/**
 * @author
 * @create 2020-11-26 21:52
 */
public class ArrayDeque<T> implements Deque<T>{

    private static final double FACTOR = 0.25;
    private int size;
    public T[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        this.size = 0;
        this.items =(T[]) new Object[8];
        this.nextFirst = this.items.length/2 - 1;
        this.nextLast = this.items.length/2;
    }

    public void addFirst(T item){
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        this.size++;
        if(size == items.length){
            resize(size << 1);
        }
    }
    public void addLast(T item){
        items[nextLast] = item;
        nextLast = addOne(nextLast);
        this.size++;
        if(size == items.length){
            resize(size << 1);
        }
    }

    public boolean isEmpty(){
        return size == 0? true:false;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        int start = addOne(nextFirst);
        for (int i = 0; i < size; i++) {
            System.out.print(items[start] + " ");
            start = addOne(start);
        }
    }

    public T removeFirst(){
        nextFirst = (nextFirst + 1)%items.length;
        T res = items[nextFirst];
        items[nextFirst] = null;
        size--;
        if(((double)size / items.length <= FACTOR) && (items.length >= 16)){
            resize(items.length >> 1);
        }
        return res;
    }

    public T removeLast(){
        nextLast = (nextLast - 1 + items.length)% items.length;
        T res = items[nextLast];
        items[nextLast] = null;
        size--;
        if(((double)size / items.length <= FACTOR) && (items.length >= 16)){
            resize(items.length >> 1);
        }
        return res;
    }

    public T get(int index){
        if(index > size || index <0){
            return null;
        }else{
            return items[(nextFirst + 1 + index)%items.length];
        }
    }

    private void resize(int newSize){
        T[] newArray =(T[]) new Object[newSize];
        int newLast = 0;
        for (int i = 0; i < size; i++) {
            newArray[newLast] = get(i);
            newLast++;
        }
        nextFirst = newSize-1;
        nextLast = newLast;
        items = newArray;
    }

    private int minusOne(int index){
        if(index == 0){
            return items.length - 1;
        }
        return index-1;
    }

    private int addOne(int index){
        if(index == (items.length-1)){
            return 0;
        }
        return index+1;
    }


}
