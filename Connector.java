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

    public abstract void Send(String mes);
    public abstract String Wait();

    public abstract void Close();

    


    

}
