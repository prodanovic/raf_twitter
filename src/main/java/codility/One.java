package codility;

/**
 *   A[0] = 5
 A[1] = 5
 A[2] = 1
 A[3] = 7
 A[4] = 2
 A[5] = 3
 A[6] = 5
 */
public class One {

    public int solution(int X, int[] A) {
        int index = -1;
        boolean first = true;
        boolean second = true;
        int count=0;

        for(int i=0; i<A.length ; i++){
            if(A[i]==X){
                if(first){
                    count+=1;
                }
                else{
                    first = false;
                }
            }
            else{
                if(second){

                }
                else{

                }
            }

        }
        return index;
    }

    public static void main(String[] args) {
        int[]niz = {5,5,1,7,2,3,5};
        One one = new One();
        System.out.println(one.solution(5,niz));
    }
}
