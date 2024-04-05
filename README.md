# Ocado Patryk_Twardosz_Java_Krakow

Implementation of recruitment task for Java Internship at Ocado Technology Kraków

Algorithm devides baskets of products by delivery method.

Main rules are:
- The more items in biggest basket, the better
- The less baskets after split, the better

Using those rules algoritm first finds the biggest avaliable basket and moves all corresponding items to it and repeats until original basket is empty.
When in original basket was present invalid item, exception is thrown.

## Prerequirements
- Java 17+
