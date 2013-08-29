Assignment 1 Problem Statement
=========

Write a program to find the "distance" between two text files as the angle between their word/frequency vectors (in radians). For each input file, a word-frequency vector is computed as follows:

1. A specified file is read in (count number of lines, words and distinct words)
2. It is converted into a list of alphanumeric words. Here a "word" is a sequence of consecutive alphanumeric characters.  Non-alphanumeric characters are treated as blanks. Case is not significant.
3. For each word, its frequency of occurrence is determined
4. The word/frequency lists are sorted alphabetically


The "distance" between two (word,frequency)-vectors is the angle between them.

If x = ((w1,f1), (w2,f2), ..., (wn,fn)) is the first vector for file_1 and y = ((w1',f'1), (w'2,f'2), ..., (w'm,f'm)) is the second vector for file_2, then the angle between them is defined as:

d(x,y) = arccos(inner_product(x,y) / (norm(x)*norm(y)))

where:

inner_product(x,y) = ∑ (fj * f'k) 

 - w is a word 
 - w==wj && w==w'k
 - for some j and k.
    
and

norm(x) = sqrt(inner_product(x,x))

Your job is to make this program as efficient as you can. You may use any of the built-in data structures in the Java API Docs. You will be graded on correctness and how efficient you make your program! You can use the Stopwatch is Sedgewick's Utility programs.

Here is a sample run:

```
bash-3.2$ java ComputeDistanceBetween mobydick.txt theprince.txt
File mobydick.txt: 17503 lines, 210028 words, 16834 distinct words
File theprince.txt: 4666 lines, 49714 words, 5216 distinct words
The distance between the documents is: 0.350215 radians
Time elapsed: 1.45 seconds
```

Note that if the distance is 0.0 radians, then the files are identical. Here is a test run on mobydick.txt.

```
bash-3.2$ java ComputeDistanceBetween mobydick.txt mobydick.txt
File mobydick.txt: 17503 lines, 210028 words, 16834 distinct words
File mobydick.txt: 17503 lines, 210028 words, 16834 distinct words
The distance between the documents is: 0.000000 radians
Time elapsed: 1.21 seconds
```

If the distance is π/2 radians, then the files have no words in common. 

If the user improperly runs the program from the command line, your program must tell the user the proper form expected. For example,

```
bash-3.2$ java ComputeDistanceBetween 
Usage: java ComputeDistanceBetween filename_1 filename_2
```

or 

```
bash-3.2$ java ComputeDistanceBetween mobydick.txt
Usage: java ComputeDistanceBetween filename_1 filename_2
```

or

```
bash-3.2$ java ComputeDistanceBetween mobydick.txt mobydick.txt mobydick.txt
Usage: java ComputeDistanceBetween filename_1 filename_2
```

If the user improperly enters a file name that does not exist  your program must respond as follows:

```
bash-3.2$ java ComputeDistanceBetween mobydick.txt prince.txt
File mobydick.txt: 17503 lines, 210028 words, 16834 distinct words
prince.txt is not found!
File prince.txt: 0 lines, 0 words, 0 distinct words
The distance between the documents is: NaN radians
Time elapsed: 0.86 seconds
```

###Grading Rubric

1. Detecting incorrect usage or file doesn't exist. (10 points)

2. Reading a file, removing punctuation, converting to a single case, isolating each word. (20 points)

3. Calculating frequency of each distinct word in a file and form word/frequency vector. (20 points)

4. Computing the dot product of each vector. (20 points)

5. Computing the angle in radians. (5 points)

6. Correct results: lines, words, distinct words and run-time. (10 points)

7. Efficiency. (15 points)
