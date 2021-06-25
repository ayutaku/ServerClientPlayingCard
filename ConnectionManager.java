import java.sql.Struct;

public class ConnectionManager {
    public void TextToAll(String text){
        System.out.println("全員へ");
        System.out.println(text);
    }

    public void TextToServer(String text){
        System.out.println("Serverへ");
        System.out.println(text);
    }

    public void TextToClient(String text){
        System.out.println("Clientへ");
        System.out.println(text);
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
        /*try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            System.out.println(e);
        }*/
        return "Serverからの返信";
    }

    public String WaitCLient(String text){
        System.out.println("Clientへ");
        System.out.println(text);
        /*try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            System.out.println(e);
        }*/
     
        return "Clientからの返信";
    }

    public void End(){
        //end処理は特別扱い
        System.out.println("end処理");
    }
}
