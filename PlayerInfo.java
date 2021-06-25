import java.util.Arrays; 

public class PlayerInfo {
    private static int[] hands = new int[15];//15枚以上はとらないという設定（後で16枚以上の処理を書く）
    private int myHandsN = 0;//現在の手札の枚数
    private char iAm;//'s'か'c'のみ。ServerかClientか
   
    PlayerInfo(char iAmSorC){
         SetIAm(iAmSorC);
    }

    public void SetHands(int cardN){
        hands[myHandsN] = cardN;
        myHandsN++;
    }

    public int[] GetHands(){
        int[] ret = Arrays.copyOfRange(hands , 0 ,myHandsN);

        return ret;
    }

    public void SetIAm(char iAmSorC){
        if(iAmSorC == 's' || iAmSorC == 'c'){
            this.iAm = iAmSorC; 
        }
        
    }
   
    public char GetIAm(){
      
        return iAm;
    }
}
