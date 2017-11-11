# stpl
set-theoretic platform


## language

First idea:

We use  Zermelo–Fraenkel (ZF) set theory for natural numbers. Those can be instructions or values for a Turing machine.

But we also use a similar system, which can be distinguished, for the order of the instructions. 

So each set contains only two elements and they represent a pair of _line number_ and _value_.

ZF is simply this: S<sub>1</sub>(n) = n ∪ {n}   
The other could be: S<sub>2</sub>(n) = {n} 

And when we need the predecessor:  
P<sub>1</sub>(n) = n \ max(n) // max :  element of set with hightest cardinality
P<sub>2</sub>(n) = x : x ∈ n 

Then only 0 and 1 would be the same. Each element would contain the empty set or not. With the empty set it's ZF (a value) and without the empty set it's just for ordering.

`ZF = { {}, {{}}, {{}, {{}}}, {{}, {{}}, {{}, {{}}}} ... }`  
`LN = { {{{}}}, {{{{}}}}, {{{{{}}}}}, ... }`   
`CD = { { ln, zf } : ln ∈ LN; zf ∈ ZF }`   
`PG = { t : t ∈ ℙ(C); 0 < |t| < ∞ }`    

`CD` is the infinite set of all possible tuples. The Turing machine can load any element of `PG`, which is a non-empty, finite set of tuples. However, it is not guaranteed that the code can be executed. The grammar is easy, as it's just some instruction set.

The parser doesn't care what brackets are used. `([{<` are all the same. Anything else is just a comment (including letters and whitespace).

Example code:   
`{ [((())) , ((), (()), ((), (()))) ] , [ (((()))) , ((), (()), ((), (())), ((), (()), ((), (())))) ] }`

This would simply load `3, 4` as the program. `3` could be an instruction and `4` could be the argument. 

## runtime

It's a turing machine (probably multi track), but the values are sets and the instructions on the values are all set operations.
The first one will be implemented in Java and should actually use `java.util.Set`.

