# Tricky Tree 
 
Is a small educational project to cover basic computer science topics such a custom tree creation with balancing feature. 

## Tree requirements
* Tree with an arbitrary depth
* Nodes could have Leafs (0  - N)
* Each Leaf has a non-zero integer weight parameter
* Leafs represent as an one-way linked list 

## Functional requirements
* For each node sort the leaf's list avoiding use of built-in sorting methods
* For each node sum of leaf's weight should be less or equals constant _W_
* Extra leafs from previous nodes should be moved to the next one
* On the last node extra leafs should be discarded.


##Example
Tree's node has three child nodes (a1, a2, a3) and also has four leafs b1, b2, b3, b4 with corresponding weights: 1 -> b1, 2 -> b2, 3 -> b3, 4 -> b4.

Constant W is equals 3


Start order: b2, b4, b3, b1

Final order: current node Leafs (b1, b2), node a1 should hold Leaf b3 and Leaf b4 should be omitted.

