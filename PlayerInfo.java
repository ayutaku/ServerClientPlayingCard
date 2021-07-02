import java.util.Arrays; 

public class PlayerInfo {
    //もともとはplayerInfoで手札を管理する予定だったが、GameRoomで一括管理に変更した
    //private static int[] hands = new int[15];//15枚以上はとらないという設定（後で16枚以上の処理を書く）
    //private int myHandsN = 0;//現在の手札の枚数
    private char iAm;//'s'か'c'のみ。ServerかClientか
   
    PlayerInfo(){}

    PlayerInfo(char iAmsorc){
         SetIAm(iAmsorc);
    }

    /*public void SetHands(int cardN){
        hands[myHandsN] = cardN;
        myHandsN++;
    }

    public int[] GetHands(){
        int[] ret = Arrays.copyOfRange(hands , 0 ,myHandsN);

        return ret;
    }*/

    public void SetIAm(char iAmsorc){
        if(iAmsorc == 's' || iAmsorc == 'c'){
            this.iAm = iAmsorc; 
        }
        
    }
   
    public char GetIAm(){
      
        return iAm;
    }
}
