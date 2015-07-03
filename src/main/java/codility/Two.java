package codility;

import java.util.ArrayList;

/**
 *   A =
 */
public class Two {

    public int[] solution(int[] A) {
        int[] result={} ;
        ArrayList<Integer> flipped = new ArrayList<>();
        int number=0;
        for(int i=0 ;  i<A.length ;i++){
            number+=A[i]*(-2)^i;
        }
        number = -number;

        for(int i=A.length ;  i>0 ;i--){
            if(A[i]==0)A[i]=1;
            else A[i]=0;
        }
        return result;

    }

    public static void main(String[] args) {
        int[]niz9 = {1,0,0,1,1};
        int[]niz_9 = {1,1,0,1};
        Two one = new Two();
        System.out.println(one.solution(niz9));
    }
}
