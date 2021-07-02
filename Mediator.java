import java.net.ConnectException;

public class Mediator {
    //Mediatorは日本語で仲介者
    PlayerUI ui;
    PlayerInfo info;
    Connector connect;
    Message answerMessage;
    

    Mediator(){
        ui = new PlayerUI();
        info = new PlayerInfo();

       
       
    }

    public void StartConnection(){
        ui.Println("これから15点のゲームを初めます");
        char[] ansData = {'b','s'};
        char ans = ui.QA("対戦部屋を作りますか？探しますか？\n対戦部屋を作る(build)場合は「b」、探す(search)場合は「s」と入力してください。",ansData);
        System.out.println("test:"+ans);
        SetConnection(ans);
       
    }

    public void SetConnection(char makeSearch){

        if(makeSearch == 'b'){
            //server
            connect = new JabberServer();
            info.SetIAm('s');
            
        }else if(makeSearch == 's'){
            //client
            connect = new JabberClient();
            info.SetIAm('c');

        }else{
            System.out.println("makeSearchの値が"+makeSearch+"です。「make」「search」の引数が必要です。");
        }
    }

    public void StartClientWait(){
        Message mes = new Message();
        while(true){
            
            mes = connect.Wait();
            if(mes !=null){
                 //mesの種類を確認する処理
                GetMessage(mes);
                 //ui.Println(mes.txt);
                    //System.out.println("test:戻り値のmes"+mes);
                if(mes.type.equals("END")){
                    //javaでのstringの比較は==ではなく.equals(==はアドレスの比較になる)
                    //System.out.println("test:break");
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

    /*//これならPlayerInfoでstatic宣言の方がいいのかなー？
    //med.info.GetIAm();で取得できるのでコメントアウト
    public char GetIAm(){
        return info.GetIAm();
    }*/


    //ここからは通信についての処理
    public void SendToAll(String type, String txt){
        System.out.println("test:全員へ");
        //System.out.println(text);
        //ui.Println(text);
        char[] x = {'x'};//数合わせ
        SendToServer(type,txt,x);
        SendToClient(type,txt,x);

    }

   
    public void SendToAll(String type, String txt,char[] ansData){
        System.out.println("test:全員へ");
        //System.out.println(text);
        //ui.Println(text);
        SendToServer(type,txt,ansData);
        SendToClient(type,txt,ansData);

    }
      //"QUE"以外の時
    public void SendToServer(String type, String txt){
        char[] x = {'x'};//数合わせ
        SendToServer(type, txt,x);   
    }

    
    public void SendToServer(String type, String txt,char[] ansData){
        System.out.println("test:Serverへ");
        //System.out.println(text);
        Message mes = new Message();
        mes.type = type;
        mes.txt = txt;
        mes.ansData = ansData;
        
        if(info.GetIAm()=='s'){
            //表示の仕方
           System.out.println("test:ここは通った");
            GetMessage(mes);
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

   
    public void SendToClient(String type, String txt,char[] ansData){
        System.out.println("test:Clientへ");
        Message mes = new Message();
        mes.type = type;
        mes.txt = txt;
        mes.ansData = ansData;

        //System.out.println(text);
        //clientへ送信する処理
        if(info.GetIAm()=='s'){
            connect.Send(mes);
            
        }else if (info.GetIAm()=='c'){
            //表示の関数が必要
            
            GetMessage(mes);
        }else{
            System.out.println("error:iAmがs,cではありません");
        }
        
    }

   /* public void DiffSendToSC(Message serverMes,Message crientMes){
        SendToServer(serverMes);
        SendToClient(crientMes);
    }*/



    public Message GetAnserMessage(char fromSOrc){
        //clientからの時はwaitを呼び出す必要がある
        if(fromSOrc == 'c'){
            WaitServer();
        }

        return answerMessage;
    }

    public Message Wait(/*String text, */char sOrc){
        Message retMes;
        if(sOrc=='s'){
            retMes = WaitServer(/*text*/);
        }else if(sOrc=='c'){
            retMes = WaitCLient(/*text*/);
        }else{
            System.out.println("エラー:sOrcの値が"+sOrc+"です。");
            retMes = new Message();
            retMes.txt = "error";
        }

        return retMes;
    }

    public Message WaitServer(/*String text*/){
        System.out.println("test:ServerへWait命令");
        

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
        /*try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            System.out.println(e);
        }*/
        return retMes;
    }

    public Message WaitCLient(/*String text*/){
        System.out.println("test:ClientへWait命令");
       
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
        /*try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            System.out.println(e);
        }*/
     
        return retMes;
    }

    public void End(){
        //end処理は特別扱い
        System.out.println("test:end処理");
        
        SendToClient("END","-1");
    }

    //自分当てのmesを受け取った時に使う関数
    public void GetMessage(Message mes){
        switch(mes.type){
            case "TXT":
                ui.Println(mes.txt);
                break;
            case "QUE":
                char ans = ui.QA(mes.txt, mes.ansData);
                SendToServer("ANS", String.valueOf(ans));//Serverに送るというよりもGameRoomに送るという感覚

                break;
            case "ANS":
                //ANSが送られて来るのはServerだけの前提。Client側ではGameRoomをインスタンス化していないのでエラーになる
                System.out.println("test:ANSのmesが送られて来ました");
                answerMessage = mes;
                break;
            case "END":
                Close();
                break;
            default:
                System.out.println("error:mesのtypeが"+mes.type+"です。GetMessageのswitch文を確認してください");
        }
    }

    
}
