package codility.countingElements;

/**
 Write a function:
 int solution(int A[], int N);
 that, given a non-empty zero-indexed array A of N integers, returns the minimal positive integer (greater than 0) that does not occur in A.
For example, given:
 A[0] = 1
 A[1] = 3
 A[2] = 6
 A[3] = 4
 A[4] = 1
 A[5] = 2
 the function should return 5.

 Assume that:
 N is an integer within the range [1..100,000];
 each element of array A is an integer within the range [−2,147,483,648..2,147,483,647].
 Complexity:
 expected worst-case time complexity is O(N);
 expected worst-case space complexity is O(N), beyond input storage (not counting the storage required for input arguments).
 Elements of input arrays can be modified.
 */
public class MissingInteger {

    public int solution(int[] A) {
        int min=A[0];
        for(int i=0; i<A.length ; i++){
            if(A[i]>0){
                min=A[i];
                break;
            }
        }
        int[] cleaned = new int[A.length];
        int cnt=0;
        for(int i=0; i<A.length ; i++){
            if(A[i]>0)cleaned[cnt++]=A[i];
            if(min>A[i]&&A[i]>0)min=A[i];
        }
        if(min>1)return 1;
        else{
            int[] shortened= new int[cnt];
            for(int i=0; i<cnt ; i++){
                shortened[i] = cleaned[i++];
            }
            min=2;
            for(int i=0; i<shortened.length; i++){
                if(!isInArray(shortened, min))return min;
                min++;
            }
        }


        return min;
    }

    public boolean isInArray(int[] arr, int num){
        for(int j=0; j<arr.length; j++){
            if(num==arr[j])return true;
        }
        return false;
    }
    public static void main(String[] args) {
        int[]niz = {-4,1,2,3,6,5,9,1000000000,4};
        MissingInteger permCheck = new MissingInteger();
        System.out.println(permCheck.solution(niz));
//        System.out.println(permCheck.isInArray(niz,-4));
    }
}
