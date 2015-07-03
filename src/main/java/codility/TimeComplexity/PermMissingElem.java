package codility.TimeComplexity;

/**
 * A zero-indexed array A consisting of N different integers is given. The array contains integers in the range [1..(N + 1)], which means that exactly one element is missing.

 Your goal is to find that missing element.

 Write a function:

 class Solution { public int solution(int[] A); }

 that, given a zero-indexed array A, returns the value of the missing element.

 For example, given array A such that:

 A[0] = 2
 A[1] = 3
 A[2] = 1
 A[3] = 5
 the function should return 4, as it is the missing element.

 Assume that:

 N is an integer within the range [0..100,000];
 the elements of A are all distinct;
 each element of array A is an integer within the range [1..(N + 1)].
 Complexity:

 expected worst-case time complexity is O(N);
 expected worst-case space complexity is O(1), beyond input storage (not counting the storage required for input arguments).
 Elements of input arrays can be modified.
 */
public class PermMissingElem {


    public int PermMissingElem(int[] A) {
        int min=100000000;
        int fullSum=0;
        int originalSum=0;
        for(int i=0; i<A.length ; i++){
            if(A[i]<min)min=A[i];
        }
        System.out.println("min="+min);
        for(int i=0; i<A.length ; i++){
            originalSum+=A[i];
        }
        for(int i=0; i<A.length+1 ; i++){
            fullSum+=min++;
        }

        System.out.println("originalSum="+originalSum);
        System.out.println("fullSum="+fullSum);
        return fullSum-originalSum;
    }

    public static void main(String[] args) {
        PermMissingElem codillity1 = new PermMissingElem();
        int [] niz =  {4,5};
        System.out.println(codillity1.PermMissingElem(niz));
    }
/*
* empty_and_single
empty list and single element	1.341 s	WRONG ANSWER
got 100000 expected 1
missing_first_or_last
the first or the last element is missing	1.329 s	WRONG ANSWER
got 7 expected 1
single
single element	1.347 s	WRONG ANSWER
got 3 expected 1
double
two elements	1.339 s	WRONG ANSWER
got 4 expected 1
simple
simple test	1.327 s	OK
Performance tests
medium1
medium test, length = ~10,000	1.360 s	OK
medium2
medium test, length = ~10,000	1.347 s	OK
large_range
range sequence, length = ~100,000	1.455 s	WRONG ANSWER
got 50001 expected 1
large1
large test, length = ~100,000	1.571 s	OK
large2
large test, length = ~100,000	1.483 s	OK
* */


}
