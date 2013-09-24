Binary Decision Tree
=========

This utility evaluates and displays a binary decision tree.

Input is taken in the form of a string:
  - (A v B) 
  - (! (! M))
  - (B v ((! D) v (! (! C))))
  - (((P v Q) ^ (R v S)) ^ ((T v U) ^ ((V v W) ^ (X v Y))))

###Usage:
Display and evaluate a tree:
  java ExpressionEvaluator exp.txt

Display the standard and normalized trees:
  java ExpressionDisplayer exp.txt 
  
A tree that uses the ExpressionEvaluator expects that the first 26 lines of your input file will be atoms with associated values. 
  A true
  B false
  ...

Atom's must be capitalized. You must include all 26 letters even if they are not in any expressions 
  
A tree that directly represents the input string will always be displayed unless an error is encountered in the string

A normalized tree will only be displayed if you run ExpressionDisplayer

##Data Structures Used
The primary data structure is a tree, with node classes that comprise the tree. This implementation lends itself easily to recursive solutions to all of the tasks that this utility performs. Atom values are stored in a simple array.

##Known Issues
The normalized tree is not produced correctly under certain conditions. They are difficult to replicate, but it seems to have something to do with large trees that have chained AND's above an OR.

If you discover the issue submit a pull request!

This readme is formatted in GitHub flavored markdown! View on GitHub for the best look and feel.
