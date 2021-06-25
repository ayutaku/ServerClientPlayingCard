public class PlayerController {
    PlayerUI ui;
    PlayerInfo info;
    Connector connect;
    

    PlayerController(){
        ui = new PlayerUI();
        ui.Println("これから15点のゲームを初めます");
        String[] ansData = {"make","search"};
        String ans = ui.QA("対戦部屋を作りますか？探しますか？\n対戦部屋を作る場合は「make」、探す場合は「search」と入力してください。",ansData);
        System.out.println("test:"+ans);
        SetConnection(ans);
        //kokokara
        //依存関係を考えよう

    }

    public void SetConnection(String makeSearch){

        if(makeSearch == "make"){
            //server
            connect = new JabberServer();
            
        }else if(makeSearch == "search"){
            //client
            connect = new JabberClient();
        }else{
            System.out.println("makeSearchの値が"+makeSearch+"です。「make」「search」の引数が必要です。");
        }

        
    }


    
}
