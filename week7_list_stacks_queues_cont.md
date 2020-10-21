## Week 7 - List, Stacks and Queues, continued

### Stack ADT

- Stack is also a sequence, in which:
    - items can only be added/removed at the top
    - you can only access the item that is currently at the top

Operations:
- push: add an item to the top of the stack
- pop: remove item at top of stack
- peek: get the item at the top but don't remove it
- isEmpty
- isFull

First version of Stack interface:
```
public interface Stack(){
    boolean push(Object obj);
    Object pop();
    Object peek();
    boolean isEmpty();
    boolean isFull();
}
```
push() returns false if the stack is full, true otherwise.

Implementation:
```
public class ArrayStack implements Stack{
    private Object[] items;
    private int top; // index of top item

    public ArrayStack(int maxSize){
        items = new Object[maxSize];
        top = -1;
    }
}
```
Items are added from left to right - so push and pop don't require any shifting.

Recall that if we have an ArrayStack of type Object and we store a String in it, then if we pop the String and access it we have to type cast it in order to save it as a String and not an object.

We'd like to be able to limit a collection to one type (like String).

We can do this by using a _generic_ interface and class.

We include a type variable _T_ in the header. It's used as a placeholder for the actual type of the items.

```
public interface Stack <T>{
    boolean push(T item);
    T pop();
    T peek();
    boolean isEmpty();
    boolean isFull();
}
```

Then when we create an ArrayStack, we specify the type of items that we intend to store in the stack.

(paused at 18:28)
