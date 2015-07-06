package codility.countingElements;

/*
* A non-empty zero-indexed array A consisting of N integers is given.
A permutation is a sequence containing each element from 1 to N once, and only once.
For example, array A such that:
    A[0] = 4
    A[1] = 1
    A[2] = 3
    A[3] = 2
is a permutation, but array A such that:
    A[0] = 4
    A[1] = 1
    A[2] = 3
is not a permutation, because value 2 is missing.
The goal is to check whether array A is a permutation.

Write a function:
class Solution { public int solution(int[] A); }

that, given a zero-indexed array A, returns 1 if array A is a permutation and 0 if it is not.
For example, given array A such that:
    A[0] = 4
    A[1] = 1
    A[2] = 3
    A[3] = 2
the function should return 1.

Given array A such that:

    A[0] = 4
    A[1] = 1
    A[2] = 3
the function should return 0.

Assume that:

N is an integer within the range [1..100,000];
each element of array A is an integer within the range [1..1,000,000,000].
Complexity:

expected worst-case time complexity is O(N);
expected worst-case space complexity is O(N), beyond input storage (not counting the storage required for input arguments).
Elements of input arrays can be modified.
* */
public class PermCheck {

    public int solution(int[] A) {
        long min=A[0];
        for(int i=0; i<A.length ; i++){
           if(min>A[i])min=A[i];
        }
        System.out.println(min);
        int sumAll = 0;
        int sum = 0;
        for(int i=0; i<A.length ; i++){
            sumAll+=min++;
            sum+=A[i];
        }
        System.out.println(min+", "+sumAll+","+sum);
        int result = (sumAll!=sum)?0:1;
        return result;
    }

    public static void main(String[] args) {
        int[]niz = {4,3,1000000000};
        PermCheck permCheck = new PermCheck();
        System.out.println(permCheck.solution(niz));
    }

    /*
    Correctness tests
extreme_min_max
single element with minimal/maximal value	1.351 s	WRONG ANSWER
got 1 expected 0
single
single element	1.342 s	WRONG ANSWER
got 1 expected 0
double
two elements	1.334 s	WRONG ANSWER
got 1 expected 0
antiSum1
total sum is correct, but it is not a permutation, N <= 10	1.334 s	WRONG ANSWER
got 1 expected 0
small_permutation
permutation + one element occurs twice, N = ~100	1.341 s	OK
Performance tests
medium_permutation
permutation + few elements occur twice, N = ~10,000	1.341 s	OK
antiSum2
total sum is correct, but it is not a permutation, N = ~100,000	1.370 s	WRONG ANSWER
got 1 expected 0
large_permutation
permutation + one element occurs three times, N = ~100,000	1.583 s	OK
large_range
sequence 1, 2, ..., N, N = ~100,000	1.577 s	OK
extreme_values
all the same values, N = ~100,000	1.351 s	OK
    * */
}
