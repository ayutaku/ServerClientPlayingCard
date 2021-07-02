public class Message {
    //データの送受信のためのクラス
    public String type;//typeは"TXT","QUE","ANS","END"
    public String txt;
    public char[] ansData;
    Message(){
        type = "-1";
        txt = "-1";
        ansData  = new char[10];//質問の選択肢は10個までとする
    }


}
