public class GameRoom {
    Deck deck;
    ConnectionManager conM;

    private int[] sHands = new int[15];
    private int[] cHands = new int[15];


    GameRoom(){
        deck = new Deck();
        conM = new ConnectionManager();
        
        GameStart();
    }

    public void GameStart(){
        conM.TextToAll("これから15点のゲームを始めます。\n15点は子→親の順にトランプを任意の数だけ引き、15点に近い人が勝利するゲームです。尚、15点を超えた場合は負けとなります。両者とも15点を超えるか、同じ点数の場合は引き分けです。");
        conM.TextToAll("まずは子からカードを引きます");

        //この辺は関数で呼び出し
        String cReturn = conM.Wait("カードを引いてください",'c');
        String sReturn = conM.Wait("対戦相手がカードを引いています",'s');

        System.out.println(cReturn);
        System.out.println(sReturn);

        conM.TextToAll("次に親がカードを引きます");
        cReturn = conM.WaitCLient("対戦相手がカードを引いています");
        sReturn = conM.WaitServer("カードを引いてください");

        System.out.println(cReturn);
        System.out.println(sReturn);

        conM.TextToAll("両者カードを引きました");
        conM.TextToAll("結果は以下の通りです\n");

        Result();
        if(OneMore()){
            GameStart();
        }else{
            conM.TextToAll("ゲームを終了します");
            conM.End();
        }
        

    }

    public void Result(){
        //ここに結果の演算を書く
        conM.TextToAll("結果を送信");
    }

    public Boolean OneMore(){
        String sR = conM.WaitServer("もう一度対戦しますか？");
        String cR = conM.WaitCLient("もう一度対戦しますか？");
        System.out.println(sR);
        System.out.println(cR);
        
        if(sR.equals("Yes")&& cR.equals("Yes")){
            return true;
        }else{
            return false;
        }
        
    }

    //カードを引く処理


}


    
