# Print dependency files in order
==========================
@author Fei Dong

## Requirement:
We annotate the top of our JavaScript files with directives like "require('otherfile.js'); require('otherfile2.js');" to indicate dependencies between JS files. Write code that print out script file name in the order that if A require on B, print A before B. 

Please focus on finding the proper order, not on parsing the require statements or loading files. Assume you are given input: 
    - dependencies: a map from file name to an array of the names of files

          A
        /  \
       B <- C
Map : A -> B,C
          C->B
You should print in the order A, C, B

## Implementation
Algorithm: topology sort
Graph.java

## Run 
$./printDependency.sh

### Sample input:

```myclass jquery jquery.cookie underscore base
jquery base
jquery.cookie jquery base
underscore jquery base
```
### Output after sorting:
>myclass jquery.cookie underscore jquery base

## Test
Unit test: GraphTest.java

## Reference:
[1] http://en.wikipedia.org/wiki/Topological_sorting
