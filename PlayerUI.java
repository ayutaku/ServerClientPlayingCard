import java.util.Scanner;

public class PlayerUI {
  
    Scanner sc;


    PlayerUI(){
        sc = new Scanner(System.in);
    }

    
    public void Println(String text){
        //将来UI変更するため
        System.out.println(text);
        System.out.println();//見づらいので一行余分に開ける
    }

    public char QA(String question, char[] ansData){
        System.out.println(question);
        String ansStr =sc.next();
        
        System.out.println();//改行を入れた

        
        int returnN = CheckAns(ansStr.charAt(0), ansData);//charAt(x)でx番目の文字をcharで返す
        return ansData[returnN];
    }

    //入力された文字が適切かを判断する関数
    private int CheckAns(char playerAns ,char[] ansData){
        for(int i = 0; i<ansData.length;i++){
            //System.out.println(ansData.length);
            if(playerAns==ansData[i] ){
                //System.out.println(playerAns);
                return i;
            }
        }
        Println("エラーです。もう一度入力してください。");
        String ansStr =sc.next();
     
        return CheckAns(ansStr.charAt(0), ansData);
    }

    public String GetSCStr(){
        String ret = sc.next();
        System.out.println();
        return ret;
    }

    //最後にこれを呼び出す
    public void EndGame(){
        //ここでscをクローズ
        sc.close();
    }

   

    //GameRoomで処理するため使わなくなった。1,11,12,13をA,J,Q,Kに変換する関数
    /*public void PrintHand(int[] hands){
        System.out.print("あなたの手札は");
        if(hands.length == 0){
            System.out.println("ありません。");
        }else{
            for(int i = 0 ; i < hands.length; i++){
                switch(hands[i]){
                    case 1:
                        System.out.print("A ");
                        break;
                    case 11:
                        System.out.print("J ");
                        break;
                    case 12:
                        System.out.print("Q ");
                        break;
                    case 13:
                        System.out.print("K ");
                        break;
                    default:
                        System.out.print(hands[i]+" ");
                        break;
                }
                
            }
            System.out.println("です。");
        }
       
       
    }*/

}
