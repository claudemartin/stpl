# stpl
set-theoretic platform


## language

First idea:

We use  Zermelo–Fraenkel (ZF) set theory for natural numbers. Those can be instructions or values for a Turing machine.

But we also use a similar system, which can be distinguished, for the order of the instructions. 

So each set contains only two elements and they represent a pair of _line number_ and _value_.

ZF is simply this: `S(n) = n ∪ {n}`   
The other could be: `S(n) = {n}` 

Then only 0 and 1 would be the same. 2 would contain the empty set or not. With the empty set it's ZF (a value) and without the empty set it's just for ordering.

The language doesn't care what brackets are used. `([{<` are all the same. Anything else is just a comment (including command and whitespace.

Example code:   
`{ [((())) , ((), (()), ((), (()))) ] , [[(((()))) , ((), (()), ((), (())), ((), (()), ((), (())))) ]] }`

## runtime

It's a turing machine (probably multi track), but the values are sets and the instructions on the values are all set operatsion.
The first one will be implemented in Java and should actually use `java.util.Set`.

