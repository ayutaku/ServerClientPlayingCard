public class Main {

    public static void main(String[] args){
        Mediator med = new Mediator();
        med.SetNickName();
        med.SetPortN();
        med.StartConnection();//コネクション確立する

        //サーバーかクライアントかで処理が変わる
        //サーバーの場合はGameRoomをインスタンス化し、GameStart()に進行が移る
        //クライアントの場合はサーバーでインスタンス化されたGameRoomからの応答を待つ
        if(med.info.GetIAm() == 's'){
            //Server
            //ここからの進行はGameRoomに移します。GameRoom.javaのGameStart()を参照してください
            GameRoom gr = new GameRoom(med);
            gr.GameStart();

        }else if(med.info.GetIAm()=='c'){
            //Client
            med.StartClientWait();
        }else{
            System.out.println("error:私はサーバーでもクライアントでもありません");
        }

        //med.Close();//"END"を受け取った時にcloseしているのでコメントアウト
     
    }



    
}
