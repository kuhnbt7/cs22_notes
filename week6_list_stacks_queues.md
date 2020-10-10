### List stacks and queues

#### Traversing a linked list (cont)

To traverse a linked list, we use a helper variable `trav`. Sometimes one helper variable is not enough, for instance if we are inserting a new character in a sorted linked list of characters. 

In this case, we use `trav` to find the right position in the list, but when it has, it has overshot the position where we want to insert the character. So we use a second helper variable `trail` which stays one behind `trav`.