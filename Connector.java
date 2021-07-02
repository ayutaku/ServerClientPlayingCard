import java.io.IOException;


public abstract class Connector {
  

    Connector(){
        System.out.println("起動");
        try{
            StartConnect();
        }catch(IOException e){
            System.out.println(e);
            
        }
    }

    
    //本当はprivateにしたいけど、継承するためにはpublicにしないとできない？
    public abstract void StartConnect() throws IOException;

    public abstract void Send(Message mes);
    public abstract Message Wait();

    public abstract void Close();

    


    

}
