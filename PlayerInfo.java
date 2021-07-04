public class PlayerInfo {
    private char iAm;//'s'か'c'のみ。ServerかClientか
    private String nickName;
   
    PlayerInfo(){}

    PlayerInfo(char iAmsorc){
         SetIAm(iAmsorc);
    }

    public void SetIAm(char iAmsorc){
        if(iAmsorc == 's' || iAmsorc == 'c'){
            this.iAm = iAmsorc; 
        }
        
    }
   
    public char GetIAm(){
        return iAm;

    }

    public void SetNickName(String name){
        nickName = name;
    }

    public String GetNickName(){
        return nickName;

    }

}
