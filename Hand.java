import java.util.Arrays; 

public class Hand {
    private int[] hands = new int[15];//15枚以上はとらないという設定（後で16枚以上の処理を書く）
    private int myHandsN = 0;//現在の手札の枚数

    

    public void SetHands(int cardN){
        hands[myHandsN] = cardN;
        myHandsN++;
    }

    public int[] GetHands(){
        int[] ret = Arrays.copyOfRange(hands , 0 ,myHandsN);

        return ret;
    }

    public int GetSum(){
        int sum = 0;
        for(int i = 0 ; i < hands.length; i++){
            sum = sum + hands[i];
             
         }

         return sum;
    }


}
