import java.util.Scanner;

public class PlayerUI {
  
    Scanner sc;
    public static void main(String[] args){
       
    }

    PlayerUI(){
        sc = new Scanner(System.in);
    }

   

    public void EndGame(){//最後にこれを呼び出す
        
        //ここでscをクローズ
        sc.close();


    }
    
    public void Println(String text){
        //将来UI変更することがあるかもしれないので一応
        System.out.println(text);
        System.out.println();//見づらいので一行余分に開ける
    }

    public char QA(String question, char[] ansData){
        //毎回scをnewするのは冗長かもしれないが、static宣言の関係で現状このように書いている
        //Scanner sc = new Scanner(System.in);
        System.out.println(question);
        String ansStr =sc.next();
        //System.out.println(ansStr+"が入力されました");
        //sc.close();//プログラムの途中でcloseすると、System.in自体がとじてしまい、そのあとscanできなくなる
        System.out.println();//改行を入れたい

        
        int returnN = CheckAns(ansStr.charAt(0), ansData);//charAt(x)でx番目の文字をcharで返す
        return ansData[returnN];
    }

    public int CheckAns(char playerAns ,char[] ansData){
        for(int i = 0; i<ansData.length;i++){
            //System.out.println(ansData.length);
            if(playerAns==ansData[i] ){
                //System.out.println(playerAns);
                return i;
            }
        }

       

        Println("エラーです。もう一度入力してください。");
        //Scanner sc = new Scanner(System.in);
        String ansStr =sc.next();
        System.out.println(ansStr+"が入力されました");
        //sc.close();
    
        return CheckAns(ansStr.charAt(0), ansData);
    }

    public String GetSCStr(){
        String ret = sc.next();
        System.out.println();
        return ret;
    }

    //GameRoomで処理するため使わなくなった
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
