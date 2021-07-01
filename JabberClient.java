import java.io.*;
import java.net.*;
public class JabberClient extends Connector{
    public static final int PORT = 8080; // ポート番号を設定する．
    private BufferedReader in;
    private PrintWriter out;
    //private ServerSocket s;
    private Socket socket;
    
 //public static void main(String[] args)
 public void StartConnect()
 throws IOException {
    boolean socketConnect = false;
 InetAddress addr =
 InetAddress.getByName("localhost"); // IP アドレスへの変換
 System.out.println("addr = " + addr);
 socket =
 new Socket(addr, PORT); // ソケットの生成
 try {
 System.out.println("socket = " + socket);
  in = 
 new BufferedReader(
 new InputStreamReader(
 socket.getInputStream())); // データ受信用バッファの設定
  out =
 new PrintWriter(
 new BufferedWriter(
 new OutputStreamWriter(
 socket.getOutputStream())), true); // 送信バッファ設定


 socketConnect = true;
 
 out.println("コネクション確立のテスト"); // データ送信
 String str = in.readLine(); // データ受信
 System.out.println(str);
 
 out.println("END");



 
 } finally {
     if(!socketConnect){
        System.out.println("closing...");
        socket.close();
     }else{
        System.out.println("else文にいる");
        //Close();
     }
 
 }
 }

 @Override
 public void Send(String mes){
    out.println(mes);
 }

 @Override
 public String Wait(){
     String mes;
     System.out.println("test:wait内");
     try{
        mes = in.readLine();
        System.out.println("test:mesは"+mes);
        return mes;
     }catch(IOException e){
        Close();
        System.out.println(e);
        return "error";
     }
    
 }


 public void Close() {
    System.out.println("closing...");
    try{
        System.out.println("Close()内");
        socket.close();
        
    }catch(IOException e){
        System.out.println(e);
    } 
}

}
