public class GameRoom {
    Deck deck;
    //ConnectionManager conM;
    Mediator med;

    private int[] sHands = new int[15];
    private int[] cHands = new int[15];


    GameRoom(Mediator argMed){
        deck = new Deck();
        //conM = new ConnectionManager();
        med = argMed;

        System.out.print("test:GameRoomのコンストラクタ内");
        
        GameStart();
    }

    public void GameStart(){
        //Message mes = new Message();
        System.out.println("GameRoomのGameStart()");
        med.SendToAll("TXT","これから15点のゲームを始めます。15点は子→親の順にトランプを任意の数だけ引き、15点に近い人が勝利するゲームです。尚、15点を超えた場合は負けとなります。両者とも15点を超えるか、同じ点数の場合は引き分けです。");
        med.SendToAll("TXT","まずは子からカードを引きます");

        //この辺は関数で呼び出し
        /*String cReturn = med.Wait("カードを引いてください",'c');
        String sReturn = med.Wait("対戦相手がカードを引いています",'s');

        System.out.println(cReturn);
        System.out.println(sReturn);

        med.TextToAll("次に親がカードを引きます");
        cReturn = med.WaitCLient("対戦相手がカードを引いています");
        sReturn = med.WaitServer("カードを引いてください");

        System.out.println(cReturn);
        System.out.println(sReturn);

        med.TextToAll("両者カードを引きました");
        med.TextToAll("結果は以下の通りです\n");

        Result();
        if(OneMore()){
            GameStart();
        }else{
            med.TextToAll("ゲームを終了します");
            med.End();
        }*/

        //test
        med.End();
        

    }

    /*public void Result(){
        //ここに結果の演算を書く
        med.TextToAll("結果を送信");
    }

    public Boolean OneMore(){
        String sR = med.WaitServer("もう一度対戦しますか？");
        String cR = med.WaitCLient("もう一度対戦しますか？");
        System.out.println(sR);
        System.out.println(cR);
        
        if(sR.equals("Yes")&& cR.equals("Yes")){
            return true;
        }else{
            return false;
        }
        
    }*/

    //カードを引く処理


}


    
