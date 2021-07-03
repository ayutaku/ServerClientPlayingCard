import java.io.*;
import java.net.*;
public class JabberServer extends Connector{
public static final int PORT = 8080; // ポート番号を設定する．
private BufferedReader in;
private PrintWriter out;
private ServerSocket s;
private Socket socket;

    /*JabberServer(){
        System.out.println("Server起動");
        try{
            StartConnect();
        }catch(IOException e){
            System.out.println(e);
        }
    
    }*/

    //public static void main(String[] args)

    //継承元のConnectorのコンストラクタで↓が呼ばれている
    @Override
    public void StartConnect()
    throws IOException {
        boolean socketConnect = false;
        /*ServerSocket*/ s = new ServerSocket(PORT); // ソケットを作成する
        System.out.println("Started: " + s);
            try {
                /*Socket*/ socket = s.accept(); // コネクション設定要求を待つ
                try {
                    System.out.println(
                    "Connection accepted: " + socket);
                    /*BufferedReader*/ in = 
                    new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream())); // データ受信用バッファの設定
                    /*PrintWriter*/ out =
                    new PrintWriter(
 new BufferedWriter(
 new OutputStreamWriter(
 socket.getOutputStream())), true); // 送信バッファ設定

 socketConnect = true;
 /*while (true) {
 String str = in.readLine(); // データの受信
 if(str.equals("END")) break;
 System.out.println(str);
 out.println(str); // データの送信
 }*/

String str = in.readLine();
System.out.println(str);
 out.println(str); // データの送信
 str = in.readLine(); // データの受信
 if(str.equals("END")){
     System.out.println(str);
 }

 } finally {
     //もしソケットの確立が正常でなくtry文の途中で終了した場合はソケットを閉じる
     //この文は本当に必要か？
     if(!socketConnect){
        System.out.println("closing...");
        socket.close();
     }

 }
 } finally {
     if(!socketConnect){
        s.close();
     }else{
        System.out.println("else文にいる");
        //Close();
     }
 
 }
 }

 @Override
 public void Send(Message mes){
     
    out.println(mes.type);
    System.out.println("test:送ったのは"+mes.type);
    out.println(mes.txt);
    System.out.println("test:送ったのは"+mes.txt);

    //BufferReaderではchar[]を読み込めないため、stringにして送る
    String ansStr="";
    for(int i=0;i<mes.ansData.length;i++){
        ansStr = ansStr + mes.ansData[i];
    }
    out.println(ansStr);
    System.out.println("test:送ったのは"+ansStr);
    
 }

 @Override
 public Message Wait(){
     Message mes = new Message();
     System.out.println("test:wait内");
     try{
        mes.type = in.readLine();
        mes.txt = in.readLine();
        String ansStr = in.readLine();

        for(int i=0;i<ansStr.length();i++){
            mes.ansData[i] = ansStr.charAt(i);
        }

        
        return mes;
     }catch(IOException e){
        Close();
        System.out.println(e);
        mes.txt = "error";
        return mes;
     }
    
 }

 //Closeしない限り通信可能なはず
 public void Close() {
    System.out.println("closing...");
    try{
        socket.close();
        s.close();
    }catch(IOException e){
        System.out.println(e);
    } 
}
    
    
 }
 



