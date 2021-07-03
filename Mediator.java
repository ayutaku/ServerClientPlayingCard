public class Mediator {
    //Mediatorは日本語で仲介者
    PlayerUI ui;
    PlayerInfo info;
    Connector connect;
    Message answerMessage;
    
    private int portN = 8080;

    Mediator(){
        ui = new PlayerUI();
        info = new PlayerInfo();
    }

    public void SetNickName(){
      
        ui.Println("これから15点のゲームを始めます。まずはあなたのニックネームを教えてください。(半角英数字のみでお願いします)");
        String name = ui.GetSCStr();
        info.SetNickName(name);
    }

    public void SetPortN(){
        ui.Println("次にポート番号を入力してください");
        String portNStr = ui.GetSCStr();

        //port番号が不適合の場合の処理
        portN = Integer.parseInt(portNStr);
    }

    public void StartConnection(){
        
        char[] ansData = {'b','s'};
        char ans = ui.QA("対戦部屋を作りますか？探しますか？\n対戦部屋を作る(build)場合は「b」、探す(search)場合は「s」と入力してください。",ansData);
       
        SetConnection(ans);
    }

    public void SetConnection(char makeSearch){
        if(makeSearch == 'b'){
            //Server側を選択
            connect = new JabberServer(portN);
            info.SetIAm('s');
            
        }else if(makeSearch == 's'){
            //Client側を選択
            connect = new JabberClient(portN);
            info.SetIAm('c');

        }else{
            System.out.println("makeSearchの値が"+makeSearch+"です。「make」「search」の引数が必要です。");
        }
    }

    //クライアントの場合はサーバーからの応答を待つ
    public void StartClientWait(){
        Message mes = new Message();
        while(true){
            mes = connect.Wait();
            if(mes !=null){
                GetMessage(mes);//自分当てのMessageを受け取った時に、mesの種類を確認し、適切な処理を行う関数

                if(mes.type.equals("END")){
                    //javaでのstringの比較は==ではなく.equals(==はアドレスの比較になる)
                    break;
                }
            }
        }
    }

    public void Close(){
        //ここにClose処理を書く
        ui.EndGame();
        connect.Close();
    }

    //自分当てのMessageを受け取った時に、mesの種類を確認し、適切な処理を行う関数
    public void GetMessage(Message mes){
        switch(mes.type){
            case "TXT":
                ui.Println(mes.txt);
                break;
            case "QUE":
                char ans = ui.QA(mes.txt, mes.ansData);
                SendToServer("ANS", String.valueOf(ans));//Serverに送るというよりもGameRoomに送るという感覚

                break;
            case "NAM":
                //ニックネーム取得処理
                SendToServer("ANS", info.GetNickName());
            case "ANS":
                answerMessage = mes;
                break;
            case "END":
                Close();
                break;
            default:
                System.out.println("error:mesのtypeが"+mes.type+"です。GetMessage()のswitch文を確認してください");
        }
    }

    //ここから先は通信の送受信についての関数群

    public void SendToAll(String type, String txt){
        //System.out.println("test:全員へ");
        char[] x = {'x'};//数合わせ
        SendToServer(type,txt,x);
        SendToClient(type,txt,x);

    }

   
    public void SendToAll(String type, String txt,char[] ansData){
        //System.out.println("test:全員へ");
        SendToServer(type,txt,ansData);
        SendToClient(type,txt,ansData);

    }
      //"QUE"以外の時
    public void SendToServer(String type, String txt){
        char[] x = {'x'};//数合わせ
        SendToServer(type, txt,x);   
    }

    
    public void SendToServer(String type, String txt,char[] ansData){
        //System.out.println("test:Serverへ");
        Message mes = new Message();
        mes.type = type;
        mes.txt = txt;
        mes.ansData = ansData;
        
        if(info.GetIAm()=='s'){
            //自分自身がサーバーで、サーバー宛にmesを送る→自分当てのmesを受け取ったと解釈
            GetMessage(mes);//自分当てのMessageを受け取った時に、mesの種類を確認し、適切な処理を行う関数
        }else if (info.GetIAm()=='c'){
            connect.Send(mes);
        }else{
            System.out.println("error:iAmがs,cではありません");
        }
    }

    public void SendToClient(String type, String txt){
        char[] x = {'x'};//数合わせ
        SendToClient(type, txt, x);
    }


   //SendToServer()と同じようなコードなので、SendToServer()を参照してください
   //同じようなコードなら、そもそも関数名でServerかClientかを判断するのでなく、引数でServerかClientかを判断できるようにするべきだった...
    public void SendToClient(String type, String txt,char[] ansData){
        //System.out.println("test:Clientへ");
        Message mes = new Message();
        mes.type = type;
        mes.txt = txt;
        mes.ansData = ansData;

        if(info.GetIAm()=='s'){
            connect.Send(mes);
            
        }else if (info.GetIAm()=='c'){
        
            GetMessage(mes);
        }else{
            System.out.println("error:iAmがs,cではありません");
        }
        
    }

    //異なるmesを同時に送る時に使おうと思っていたが、結局使わなかったのでコメントアウト
   /* public void DiffSendToSC(Message serverMes,Message crientMes){
        SendToServer(serverMes);
        SendToClient(crientMes);
    }*/

    //ANSのmessageを受け取った時に、その内容を取得するために呼び出す
    //Server側が使う前提で作ってあるので、Client側で使う場合は拡張が必要
    public Message GetAnserMessage(char fromSOrc){

        //clientからの時はwaitを呼び出す必要がある
        if(fromSOrc == 'c'){
            WaitServer();
        }

        return answerMessage;
    }

    public Message Wait(char sOrc){
        Message retMes;
        if(sOrc=='s'){
            retMes = WaitServer();
        }else if(sOrc=='c'){
            retMes = WaitCLient();
        }else{
            System.out.println("エラー:sOrcの値が"+sOrc+"です。");
            retMes = new Message();
            retMes.txt = "error";
        }

        return retMes;
    }

    public Message WaitServer(){
       // System.out.println("test:ServerへWait命令");
        Message retMes;

        if(info.GetIAm()=='s'){
            retMes = connect.Wait();
            GetMessage(retMes);
        }else if (info.GetIAm()=='c'){
            //サーバーに対してクライアントが待てと命令することはないという前提
            System.out.println("error:サーバーに対してクライアントが待てと命令することはないという前提");
            retMes = new Message();
            retMes.txt = "error";
        }else{
            System.out.println("error:iAmがs,cではありません");
            retMes = new Message();
            retMes.txt = "error";
        }
        
        return retMes;
    }

    public Message WaitCLient(){
        //System.out.println("test:ClientへWait命令");
       
        Message retMes;

        if(info.GetIAm()=='s'){
           //サーバーからクライアントへ待機命令
           System.out.println("test:サーバーからクライアントへ待機命令");
           retMes = new Message();
           retMes.txt = "error";

        }else if (info.GetIAm()=='c'){
            
            retMes = connect.Wait();
            GetMessage(retMes);
        }else{
            System.out.println("error:iAmがs,cではありません");
            retMes = new Message();
            retMes.txt = "error";
        }
       
        return retMes;
    }

    public void End(){
        //end処理は特別扱い。これはENDを送信するためのコード
        //System.out.println("test:end処理");
        SendToClient("END","-1");
        SendToServer("END","-1");
    }

    

    
}
