public class Main {

    public static void main(String[] args){
        Mediator med = new Mediator();
        med.StartConnection();

        System.out.println("コネクション確立！！！");

        if(med.info.GetIAm() == 's'){
            System.out.println("test:私はサーバー");
            //ここからGameの進行はGameRoomに移します
            GameRoom gr = new GameRoom(med);

        }else if(med.info.GetIAm()=='c'){
            System.out.println("test:私はクライアント");
            med.StartClientWait();
        }else{
            System.out.println("error:私は何者でもありません");
        }



        med.Close();
     
    }



    
}
