public class Test {
    public static void main(String[] args){
       Deck d = new Deck();
       PlayerUI ui = new PlayerUI();
       PlayerInfo info = new PlayerInfo('s');

       System.out.println("GetACardのテスト");
       int cardN;
       for(int i=0; i<7;i++){
        cardN = d.GetACard();
        System.out.println(cardN);
        info.SetHands(cardN);
       }

       ui.PrintHand(info.GetHands());

       
    }
}
