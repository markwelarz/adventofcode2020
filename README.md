# Advent of Code 2020 in Java
https://adventofcode.com/2020

* Be quicker than last year
* Do stupid stuff: not production code

### Solutions

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
