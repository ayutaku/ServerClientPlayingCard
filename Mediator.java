import java.net.ConnectException;

public class Mediator {
    //Mediatorは日本語で仲介者
    PlayerUI ui;
    PlayerInfo info;
    Connector connect;
    
    

    Mediator(){
        ui = new PlayerUI();
        info = new PlayerInfo();

       
       
    }

    public void StartConnection(){
        ui.Println("これから15点のゲームを初めます");
        String[] ansData = {"make","search"};
        String ans = ui.QA("対戦部屋を作りますか？探しますか？\n対戦部屋を作る場合は「make」、探す場合は「search」と入力してください。",ansData);
        System.out.println("test:"+ans);
        SetConnection(ans);
       
    }

    public void SetConnection(String makeSearch){

        if(makeSearch == "make"){
            //server
            connect = new JabberServer();
            info.SetIAm('s');
            
        }else if(makeSearch == "search"){
            //client
            connect = new JabberClient();
            info.SetIAm('c');

        }else{
            System.out.println("makeSearchの値が"+makeSearch+"です。「make」「search」の引数が必要です。");
        }
    }

    public void StartClientWait(){
        String mes;
        while(true){
            mes = connect.Wait();
            //mesの種類を確認する処理
            ui.Println(mes);
            if(mes =="END"){
                break;
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
   
    public void TextToAll(String text){
        System.out.println("全員へ");
        //System.out.println(text);
        //ui.Println(text);
        TextToServer(text);
        TextToClient(text);

    }

    public void TextToServer(String text){
        System.out.println("Serverへ");
        //System.out.println(text);
        if(info.GetIAm()=='s'){
            ui.Println(text);
        }else if (info.GetIAm()=='c'){
            connect.Send(text);
        }else{
            System.out.println("error:iAmがs,cではありません");
        }
        
    }

    public void TextToClient(String text){
        System.out.println("Clientへ");
        //System.out.println(text);
        //clientへ送信する処理
        if(info.GetIAm()=='s'){
            connect.Send(text);
        }else if (info.GetIAm()=='c'){
            ui.Println(text);
        }else{
            System.out.println("error:iAmがs,cではありません");
        }
        
    }

    public void DiffTextToSC(String serverText,String crientText){
        TextToServer(serverText);
        TextToClient(crientText);
    }

    public String Wait(String text, char sOrc){
        String retStr;
        if(sOrc=='s'){
            retStr = WaitServer(text);
        }else if(sOrc=='c'){
            retStr = WaitCLient(text);
        }else{
            System.out.println("エラー:sOrcの値が"+sOrc+"です。");
            retStr = "error";
        }

        return retStr;
    }

    public String WaitServer(String text){
        System.out.println("Serverへ");
        System.out.println(text);

        String ret = "-1";

        if(info.GetIAm()=='s'){
            ret = connect.Wait();
        }else if (info.GetIAm()=='c'){
            //サーバーに対してクライアントが待てと命令することはないという前提
            System.out.println("error:サーバーに対してクライアントが待てと命令することはないという前提");
        }else{
            System.out.println("error:iAmがs,cではありません");
        }
        /*try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            System.out.println(e);
        }*/
        return ret;
    }

    public String WaitCLient(String text){
        System.out.println("Clientへ");
        System.out.println(text);

        String ret = "-1";

        if(info.GetIAm()=='s'){
           //サーバーからクライアントへ待機命令
           System.out.println("test:サーバーからクライアントへ待機命令");
        }else if (info.GetIAm()=='c'){
            
            ret = connect.Wait();
        }else{
            System.out.println("error:iAmがs,cではありません");
        }
        /*try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            System.out.println(e);
        }*/
     
        return ret;
    }

    public void End(){
        //end処理は特別扱い
        System.out.println("end処理");
        TextToClient("END");
    }

    
}
