# Advent of Code 2020 in Java
https://adventofcode.com/2020

* Be quicker than last year
* Do stupid stuff: not production code

### Solutions

#### Day 19
I was contemplating using Parboiled (see day 2) again today but then it struck me that the syntax rules are very
simple, with only sequences and choices.  As a bit of a joke I tried expanding out rule 0 fully into a single regular
expression.  I used the super-useful [StringSubstitutor](https://commons.apache.org/proper/commons-text/javadocs/api-release/org/apache/commons/text/StringSubstitutor.html)
from [Apache Commons Text](https://commons.apache.org/proper/commons-text/) library.  It was quite easy to load a
`HashMap` with the rules: replace all of the rule number references in each rule with variables:
`12 | 16 -> ${12} | ${16}`, and then remove all whitespace.  For part 1 the resulting regular expression was over 4000
characters and I didn't expect it to work at all, but it ran in 14ms.  In part 2 rules reference themselves.  Recursive
parse-rules can usually be converted into rules that use repetition:

```
Rule 8: 42 | 42 8
=> 42
=> 42 42
=> 42 42 42
(42)+
```

Rule 8 can be converted very easily.  But rule 11 is a bit more tricky:

```
Rule 11: 42 31 | 42 11 31
=> 42 31
=> 42 42 31 31
=> 42 42 42 42 31 31 31 31
=> 42 42 42 42 42 31 31 31 31 31
```

It can be represented by a sequence of `42`s then the same number of `31`s.  This could be done via backreferences (I
think) but I would have to calculate the group number, which is not impossible, but instead I decided to cheat slightly.
And the puzzle input almost invites it by suggesting to only write a program that will handle the input, not any case.
So I replaced rule 11 with this:
 
`42 31 | 42 42 31 31 | 42 42 42 42 31 31 31 31`

(spaces added for easy reading).  Of course, I had no idea whether 1 or 100 levels would be enough.  Instead of
hardcoding it, I generated the regex using a parameter, then used JUnit 5's `@ParameterizedTest` to run the part 2
problem with sequential parameters.  It was only 4 levels before the output stopped increasing.  The generated regex
for part 2 is 30,009 characters.

#### Day 18
Left-to-right expression parsing in part1?  No need to construct a parser, I can tokenize and do the calculation in a
single pass.  There are only single digits in the input and tests, so the parsing was kept to a minimum: I removed all
the spaces and split by character.  Part 2 was a bit more complex, and different enough that I didn't end up reusing any
code from part 1.  The reverse-precendence of multiply/addition foiled any plans of passing the entire input through an
expression engine.  I'd already used Parboiled in Day 2, so I wasn't going to repeat it but it would have been a good
option for this puzzle.  Instead I decided to hand-code a bottom-up parser evaluator `StringBuilder`'s, splitting,
regular-expressions (first time I've used Regex in AoC-2020).  I compute the inner-most bracketed part of the
expression and replace the evaluated section in the StringBuilder, then move outwards, as shown below.  It's a bit
unrefined but it was fun to see the expression being reduced in a similar way to how a human would solve it.

```
8*6+(5*(3+2*8+2+7+4)+(7+5*6)*9)
8*6+(5*(3+2*8+2+7+4)+72*9)
8*6+(5*105+72*9)
8*6+7965
```

#### Day 17
This is quite similar to day 11 and I could have used a similar solution, but I've done something better.  At first,
the problem seems quite complicated, but the infinite grid acutally makes it simpler.  The first leap I made is that I
only need to store active cubes.  Using a `Set` of Cubes.  Each cycle, I just iterate the enabled cubes and copy any
the pass the enabled-rule to a second `Set`.  Any neighbours of the cube in question that aren't enabled (in the original
`Set`), I then apply the inactive-rule after getting their neighbours, and add any that pass the rule to the second
`Set`.  The second `Set` is overwritten to be main Set, and is repeated for 6 cycles.

For part 2 I was expecting something like day-11 part 2, but instead the grid is expanded to 4-dimensions.  My algorithm
works for part 2 as well, I've just used a different Cube model.  This solution does not scale well.  Part 2 takes
14s on my laptop; the same cubes are processed more than once on each cycle which may be the problem, or the algorithm
may be O(n4) for part 2.

The example steps for part 1 seems to produce a different result than I expect.  Before any cycles:
```
z=0
.#.
..#
###
```

After 1 cycle:
```
z=0
#.#
.##
.#.
```

Surely the bottom-right cube has two enabled neighbours and should remain enabled?  Nevertheless, the solution produces
the right answer.


#### Day 16
This was my favourite puzzle so far.  It's a process of a number of different elimination rules.  I created a data
structure, it's a bit clumsy - it's a `Map` of `Set`s, where key=field name, value=a set of the possible fields
(numbers) on a ticket that this could be valid for.  The first (which is all of part 1), is to apply the rules to every
field on the ticket, and tickets that contain field values that don't pass any of the rules can be discarded.  The
second elimination is to eliminate any field-name->field-number mappings that don't pass their corresponding rules.  The
third elimination is to check whether there are any fields that only have 1 possible field number left, and eliminate
that field number from every other field mapping.  Now at this point I was expecting to still have to deal with some
unresolved field mappings, and to have to do some inferencing using a tree search.  But actually, these 3 eliminations
were enough to reduce the possible mappings to a one for each field.

#### Day 12
Bit of a vanilla solution, today, there is nothing particularly insightful or interesting to say!

#### Day 11
This is a nice one.  It is deceptively straightforward, but the implementation details, especially in part 2, benefit
from some finer grained unit tests than just the provided test inputs, because there are lots of moving parts that can
go wrong.  As ever, I've over-engineered this solution to make it more fun.  The rules about flipping occupied to
unoccupied status are a good match for a decision-table.  The fine [multi-matcher](https://github.com/richardstartin/multi-matcher)
library calls itself a rules engine, but I've used it before for a decision-table problem and it worked well.  I like
that it's strongly typed unlike most other rules engines I've seen in Java, and I really like that attributes ("facts" 
in some other rules engines) aren't restricted to being Java Bean properties, but are Java 8 `Function`s of the input.

So, to create our rules engine ("classifier", in multi-matcher speak), we need to create some data (attributes) and some
rules ("constraints") to act on those attributes.  The attributes need to be simple data types, and rules are simple
operators (equals, greater-than, ...).  A "not-equals" would be very useful, as would a few more advanced classifier
rules, or some examples of adding custom rules.  So, our attributes are as follows.  The first two return ints and the
rest booleans:

```
.withAttribute("thisSeat", this::thisSeat)
.withAttribute("countOccupied", this::countOccupied))
.withAttribute("above", this::aboveOccupied)
.withAttribute("below", this::belowOccupied)
.withAttribute("left", this::leftOccupied)
.withAttribute("right", this::rightOccupied)
.withAttribute("aboveLeft", this::aboveLeftOccupied)
.withAttribute("aboveRight", this::aboveRightOccupied)
.withAttribute("belowLeft", this::belowLeftOccupied)
.withAttribute("belowRight", this::belowRightOccupied)
```

... and some rules to act on the data.

```
MatchingConstraint.<String, Integer>named("becomes empty")
                .eq("thisSeat", SeatingArea.occupied)
                .ge("countOccupied", visibleOccupied)
                .classification(SeatingArea.empty)
                .build(),

MatchingConstraint.<String, Integer>named("becomes occupied")
                .eq("thisSeat", SeatingArea.empty)
                .eq("above", false)
                .eq("below", false)
                .eq("left", false)
                .eq("right", false)
                .eq("aboveLeft", false)
                .eq("aboveRight", false)
                .eq("belowLeft", false)
                .eq("belowRight", false)
                .classification(SeatingArea.occupied)
```

For part 2, the rules are a bit different.  I refactored so the attributes `Functions` to be abstract methods, and
wrote separate implementations for `leftOccupied` and friends.  To make things more interesting, I decided to store the
seating area as a 1D array using row-major ordering and it was this that caused most of my bugs :-)  The solution has
quite a nice design, although there are algorithms and duplication that I wouldn't normally leave if it were 
production code.

#### Day 10
You have to read the puzzle carefully for part 1 because there is some behaviour at the beginning and end of the sequence
that isn't obvious.  In part 1, the only valid chain that uses every adapter is in ascending order.  I sort the list of
numbers and counted the number of differences between subsequent elements that is 1 or 3.  Part 2 is an interesting
puzzle.  I did this on day 15, and I'd seen a meme on Reddit that implied that a recursive-tree algorithm was the wrong
way to solve it, so I'd already came at this with a preconception, and I spent too much time thinking of a non-tree
solution.  But actually, a recursive tree solution is a good solution and is O(n) (excluding the sort).

Using the first example from the puzzle, our tree looks like this:

[![](https://mermaid.ink/img/eyJjb2RlIjoiZ3JhcGggTFJcbiAgICBTKChDaGFyZ2luZyBvdXRsZXQ6MCApKSAtLT4gQVxuICAgIEFbMV0gLS0-IEJcbiAgICBCWzRdIC0tPiBDXG4gICAgQls0XSAtLT4gRFxuICAgIEJbNF0gLS0-IEVcbiAgICBDWzVdIC0tPiBEXG4gICAgQ1s1XSAtLT4gRVxuICAgIERbNl0gLS0-IEVcbiAgICBFWzddIC0tPiBGXG4gICAgRlsxMF0gLS0-IEdcbiAgICBGWzEwXSAtLT4gSFxuICAgIEdbMTFdIC0tPiBIXG4gICAgSFsxMl0gLS0-IElcbiAgICBJWzE1XSAtLT4gSlxuICAgIEpbMTZdIC0tPiBLXG4gICAgS1sxOV0gLS0-IExbeW91ciBkZXZpY2U6IDIyXVxuICAgIHN0eWxlIFMgZmlsbDojOTllZTk5XG4gICAgc3R5bGUgTCBmaWxsOiM5OWVlOTlcblxuIiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)](https://mermaid-js.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoiZ3JhcGggTFJcbiAgICBTKChDaGFyZ2luZyBvdXRsZXQ6MCApKSAtLT4gQVxuICAgIEFbMV0gLS0-IEJcbiAgICBCWzRdIC0tPiBDXG4gICAgQls0XSAtLT4gRFxuICAgIEJbNF0gLS0-IEVcbiAgICBDWzVdIC0tPiBEXG4gICAgQ1s1XSAtLT4gRVxuICAgIERbNl0gLS0-IEVcbiAgICBFWzddIC0tPiBGXG4gICAgRlsxMF0gLS0-IEdcbiAgICBGWzEwXSAtLT4gSFxuICAgIEdbMTFdIC0tPiBIXG4gICAgSFsxMl0gLS0-IElcbiAgICBJWzE1XSAtLT4gSlxuICAgIEpbMTZdIC0tPiBLXG4gICAgS1sxOV0gLS0-IExbeW91ciBkZXZpY2U6IDIyXVxuICAgIHN0eWxlIFMgZmlsbDojOTllZTk5XG4gICAgc3R5bGUgTCBmaWxsOiM5OWVlOTlcblxuIiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZX0)

Basically, a path, but with branches that skip nodes.  The part-2 algorithm is a fairly simple depth-first recursion,
that counts the number of paths that reach the end-node.  This is ok for the test input, but is extremely slow (never
finishes) for the solution input for day-10, which has 102 numbers.  I used some caching (I can't really call it a
memoize as it's not done functionally) to add a dynamic programming optimization into the recursive tree.  The cache is
keyed on the previous node's rating but is stored on the node, so effectively the key is previous-rating and
current-rating.  It stores the result of traversing through the subnodes of a particular node.  Interestingly, my first
iteration only keyed on the current node (not combined with the previous rating) and all of the test data and the input
data passed.  I don't know if that was because the organisers have tried not to make the input data too complicated or
whether my input was just like that.

#### Day 9
For part 1, I used the sliding-windowing feature from the excellent [Proton Pack](https://github.com/ProtonMail/proton-pack)
library.  I've used a window of size 25 (5 for the example test) and compared the sum of the window'ed with the
subsequent number in the sequence.  For part 2, initially I wrote a brute-force algorithm.  It would take a sum of
every possible combination of window-size and position.  Despite the brute-force, it still took only 2s to find the
answer, but nevertheless I don't usually like to use this technique (especially as Day8 was also a brute-force), so I've
written a faster solution using the prefix-sum data structure.  I've ran both solutions 100 times to compare run times.
The prefix-sum algorithm is 219x faster. ![Speeds compared](/screenshots/day9.png)

#### Day 8
This took me back to AoC 2019 and the IntCode runtime which was great fun.  This language is much simpler however.  I've 
done nothing clever, stupid, nor I have I used any new libraries today.  In part 1, my program updates a set of "seen"
instructions, and upon each instruction will check and throw a `InfiniteLoopException` if it encounters an instruction
that has been ran before.  For part 2, I refactored my `ProgramRunner` class so it would handle both parts.  It's a
brute force algorithm, that, one at a time, swaps a JMP and NOP instruction and rerunning the program until an
`InfiniteLoopException` is not thrown.  This is possibly the first time I've ever used the new Pattern Matching
`instanceof` experimental feature in Java 14 in actual code.  I've also used the new switch expression, although my
IntelliJ formatting rules seem to have messed that up.

#### Day 7
This is a nice parsing and tree/recursion problem rolled into one (although my solution uses neither a tree structure or
a recursive algorithm).  The parse is slightly more tricky than at first glance; there are four variants of the
"contain" part of the sentence, and the colours can be made up of multiple words.
```
light blue bags contain 1 bright yellow bag.
blue bags contain 3 yellow bags.
blue bags contain 1 yellow bag, 5 green bags.
blue bags contain no other bags.
```
I used Commons Lang's StringUtils to chop up the line and extract the numbers and colours.

The first part is to count the number of ancestors of a node (a bag).  A node can have multiple parents but there are no
circular dependencies.  The second part is to count the children, where each level multiplies the previous level.
The concept is simple, but it's very easy to cause yourself a headache with the multiply, especially at the top and
bottom of the tree.

#### Day 6
Super-succinct Stream/reduction while being reasonably readable solutions for both parts, today.  For part 1, I've used
the same Guava double-line splitter technique as in Day 4.  Then I took each multiline group, removed all the whitespace
and split into distinct characters, then count the elements.  Part 2 is similar, but we need to preserve characters
per-line, I mapped each line (person) in the group to a set of its characters, then reduced the sets into a single set
using an intersection.  The count is the number of characters common to every person in the group.  I was expecting a
tough one today, but the difficulty has regressed!

#### Day 5
The difficulty level has taken a step up today.  It's the first problem that the algorithm can be implemented at face-
value, as described, and that also has a shorter and simpler solution.  At first I tried cheekily submitting the highest
ID based on being in the back-most row and right-most column but they hadn't left that gap open.  With a boarding
pass ID, I noticed they follow the same counting pattern as a binary number: `FFFF,FFFB,FFBF,FFBB,...` is equivalent to
`0000,0001,0010,0011,...`.  Similarly, the column section `LLL,LLR,LRL,LRR,...` is equivalent to `000,001,010,011,...`.
The row/column numbers are 0-127 and 0-7 and after the calculation is a continuous sequence of integers from 11 to 850.
My solution is to split the row and the column part of the String, then convert the 2 Strings into base-2 numbers, then
do the calculation described in the problem.  Part 1 is an unreadable oversized stream pipeline.

For part 2, because we know the ID of every seat, we can just sum them, then compare them to the sum of seat IDs of the
seats we've seen in the input.  The difference is our seat number.


#### Day 4
Part 1 is a bit of a parsing problem.  We have to handle passport records that may be split across multiple lines, and
that fields can be in any order.  I'm still avoiding regular-expressions of course, and I'm not going to repeat the
over-engineering parser effort from day 2.  The double-newline is an easy way of splitting passport records, then I used
Guava's mighty-useful [Map Splitter](https://guava.dev/releases/30.0-jre/api/docs/com/google/common/base/Splitter.MapSplitter.html)
to create a Java Map of key/value pairs.  Super-easy parsing with a not-too-horrible overly long stream statement.  The
validation requirements, although quite wordy in the problem, is really just everything is required except for
country-ID.

Part 2 reads like a Bean Validation example from a textbook.  I did try to find a nice alternative, but in the end I've
done it with Hibernate Validator.  Validation, and testing validation, is not interesting (sorry, validator engineers)
and this part did feel a little bit like doing work.  I used Apache Commons BeanUtils to populate a bean instance from
the Map that was parsed.  For the height field, which can be in both inches or centimetres, I wanted to use a type with
a length and a unit, so I tried out the Units and Measures library
[Indriya](https://unitsofmeasurement.github.io/indriya/).  It has a `QuantityFormat` that works along the same lines as
a `SimpleDateFormat`.  It will parse `150 cm` but definitely not `150cm`.  Even after reformatting the input, it then
did not handle inches as a unit(!).  I reverted to using a `String` type and wrote a custom validator for that field.
This solution is over-engineering again of course, but it was still interesting to wire up Commons-BeanUtils and 
Hibernate Validator without Spring involved.

#### Day 3
There are 2 insights that can solve this problem:
* The repeating pattern is a sign that you can calculate cycled/wrapped horizontal positions using the modulus operator
* In part 1, the position can be calculated using the line number, width of input, and right-move size
  * In part 2, need the down-move size in addition to the above.

In this problem I came across the old checked exception inside a lambda problem.  Here I've tried the library
[Durian](https://github.com/diffplug/durian) which has a few nice ways of dealing with it.  Suppressing is fine here :)

In part 2, the input needed to be reset and re-read.  I usually use Spring's `Resource` as an abstraction so I can read
the input as a String (`ByteArrayResource`) for unit tests or as a classpath file (`ClasspathResource`).  I've replaced
the Resource with Guava's `CharSource` which is re-readable.  

#### Day 2
It would be easy to use regex's to extract the min, max, character and password from the input file but today's solution
is stupidly over-engineered.  I've used the excellent [Parboiled](https://github.com/sirthias/parboiled) to generate a
parser for the grammar.  Parboiled can generate ASTs, and here, for part 1, I've used Parboiled to generate a list of
`PasswordEntry` objects that have an `isValidPart1` method with a simple less-than/greater expression.  For part 2, the
same parser works just as well, but there is slightly a different `isValidPart2` method.

#### Day 1
In part 1, I used the fine [combinatoricslib3](https://github.com/dpaukov/combinatoricslib3) to generate
2-combination sets of the input numbers.  Then I filtered it for the sum (of the 2-entry set) to be 2020, then
multiply the matching combination.  For part 2, I decided the same method could be used with a small change: I added a
size parameter (=2 for part-1, =3 for part-2) and changed the multiply lambda to a reduce operation that will compute
the product of the matching combination.
