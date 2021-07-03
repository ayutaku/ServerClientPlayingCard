public class GameRoom {
    Deck deck;
    //ConnectionManager conM;
    Mediator med;

    private Hand sHands;// = new Hand();
    private Hand cHands;// = new Hand();


    GameRoom(Mediator argMed){
        deck = new Deck();
        //conM = new ConnectionManager();
        med = argMed;

        System.out.print("test:GameRoomのコンストラクタ内");
        
        GameStart();
    }

    public void GameStart(){
        //Message mes = new Message();
        sHands = new Hand();
        cHands = new Hand();
        System.out.println("GameRoomのGameStart()");
        med.SendToAll("TXT","これから15点のゲームを始めます。15点は子→親の順にトランプを任意の数だけ引き、15点に近い人が勝利するゲームです。尚、15点を超えた場合は負けとなります。両者とも15点を超えるか、同じ点数の場合は引き分けです。");
        med.SendToAll("TXT","まずは子からカードを引きます");

        DrawPhase('c');

        med.SendToAll("TXT","次に親がカードを引きます");

        DrawPhase('s');

        med.SendToAll("TXT","両者カードを引きました");

        med.SendToAll("TXT","結果は以下の通りです");
        
        Result();

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

        Result();*/
        if(OneMore()){
            med.SendToAll("TXT", "もう一度ゲームを始めます。尚、山札はシャッフルされません");
            GameStart();
        }else{
            med.SendToAll("TXT","ゲームを終了します");
            med.End();
        }

        //test
        //med.End();
        

    }

    //カードを引くフェーズ
    private void DrawPhase(char sOrc){
        String q = "カードを引きますか？引く場合は「y」,引かない場合は「n」を入力してください。手札を確認(check)したい場合は「c」を入力してください";
        char[] ansData = {'y','n','c'};
        Message ansMes;
        char ansMesCh;

        while(true){
            if(sOrc == 's'){
                med.SendToServer("QUE", q, ansData);
            }else if(sOrc == 'c'){
                med.SendToClient("QUE", q, ansData);
            }else{
                System.out.println("error:sOrcの値が"+sOrc+"です");
            }
            
            
            ansMes = med.GetAnserMessage(sOrc);//相手がクライアントの場合は応答を待つ処理も含まれる


            ansMesCh =ansMes.txt.charAt(0);
            if(ansMesCh == 'y'){
                System.out.println("test:カードを引く処理");
                //カードを引いて、引いたカードを送る
                DrawACard(sOrc);

            }else if(ansMesCh == 'n'){
                break;//カードを引くのをやめる
            }else if(ansMesCh == 'c'){
                //持っているカード見せる処理。
                System.out.println("test:カードを確認する処理");
                CheckCard(sOrc);
            }else{
                System.out.println("ansMesChの値が"+ansMesCh+"です");
            }
        }
        
        

    }

    public void DrawACard(char sOrc){
         //カードを引いて、引いたカードを送る
        //s,cは関数名ではなく、引数で渡せればよかった
        int drawCard = deck.DrawACard();
        if(sOrc == 's'){
            sHands.SetHands(drawCard);
            med.SendToServer("TXT", "あなたが引いたカードは"+ToAJQK(drawCard)+"です。");
        }else if(sOrc == 'c'){
            cHands.SetHands(drawCard);
            med.SendToClient("TXT", "あなたが引いたカードは"+ToAJQK(drawCard)+"です。");
        }else{
            System.out.println("error:sOrcの値が"+sOrc+"です");
        }
    }

    public String ToAJQK(int cardN){
        switch(cardN){
            case 1:
                return "A";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return String.valueOf(cardN);
        }
    }

    public void CheckCard(char sOrc){
        int[] hands = new int[15];
        if(sOrc == 's'){
            hands = sHands.GetHands();
        }else if(sOrc == 'c'){
            hands = cHands.GetHands();
        }else{
            System.out.println("error:sOrcの値が"+sOrc+"です");
        }

        String ret = "あなたの手札は";
       
   
        System.out.println("test:hands.lengthは"+hands.length);
        if(hands.length == 0){
            ret = ret + "ありません。カードを引きましょう。";
        }else{
            /*for(int i = 0 ; i < hands.length; i++){
               ret = ret + ToAJQK(hands[i]) + " ";
                
            }*/
            ret = ret + ToAJQKStr(hands);
            ret = ret + "です。";
        }

        if(sOrc == 's'){
            med.SendToServer("TXT", ret);
        }else if(sOrc == 'c'){
            med.SendToClient("TXT", ret);
        }else{
            System.out.println("error:sOrcの値が"+sOrc+"です");
        }
       
       
    }

    public String ToAJQKStr(int[] hands){
        String ret = "";
        for(int i = 0 ; i < hands.length; i++){
            ret = ret + ToAJQK(hands[i]) + " ";
             
         }
         
         return ret;

    }



    
  

    public void Result(){
        //ここに結果の演算を書く
        int sSum = sHands.GetSum();
        int cSum = cHands.GetSum();

        final int SWIN = 0;
        final int CWIN = 1;
        final int DRAW = 2;

        int result = -1;

        //勝敗の演算
        if(sSum > 15 && cSum > 15){
            result = DRAW;
        }else if(sSum > 15){
            result = CWIN;
        }else if(cSum > 15){
            result = SWIN;
        }else if(sSum == cSum){
            result = DRAW;
        }else if(sSum > cSum){
            result = SWIN;
        }else if(sSum < cSum){
            result = CWIN;
        }else{
            result = -1;
            System.out.println("error:勝敗を決められません。条件分岐を確認してください");
        }

        System.out.println("test:resultは"+result);

        //結果の送信
        med.SendToAll("TXT", "親の手札は" + ToAJQKStr(sHands.GetHands())+" 合計は"+sSum);
        med.SendToAll("TXT", "子の手札は" + ToAJQKStr(cHands.GetHands())+" 合計は"+cSum);
        if(result == DRAW){
            med.SendToAll("TXT", "よって、引き分けです。");
        }else if(result == SWIN){
            med.SendToAll("TXT", "親の勝ちです。");
        }else if(result == CWIN){
            med.SendToAll("TXT", "子の勝ちです。");
        }else{
            System.out.println("error:resultの条件分岐を確認してください");
        }

        
    }

    public Boolean OneMore(){
        String q = "もう一度対戦しますか？";
        char[] ansData = {'y','n'};
        med.SendToServer("QUE",q,ansData);

        Message ansMes = med.GetAnserMessage('s');
        if(ansMes.txt.equals("y")){
            med.SendToServer("TXT", "了解しました。対戦相手の応答を待っています。");
            
            med.SendToClient("QUE",q,ansData);
            ansMes = med.GetAnserMessage('c');

            if(ansMes.txt.equals("y")){
                return true;
            }
        }
        
        return false;
        
    }

    


}


    
