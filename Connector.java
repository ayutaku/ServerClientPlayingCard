import java.io.IOException;

public abstract class Connector {
    public final int PORT;
    Connector(){
        PORT = 8080;
        //System.out.println("test:起動");
        try{
            StartConnect();
        }catch(IOException e){
            System.out.println(e);
            
        }
    }

    Connector(int portN){
        PORT = portN;
        //System.out.println("test:起動");
        try{
            StartConnect();
        }catch(IOException e){
            System.out.println(e);
            
        }
    }

    protected abstract void StartConnect() throws IOException;

    public abstract void Send(Message mes);
    public abstract Message Wait();
    public abstract void Close();
}
