import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Deck {
    //Deckは日本語で山札
    private int[] deck = new int[13*4];
    private int cardN = 0;

    

    Deck(){
        FirstSet();
        //Test();
        Shuffle();
        //Test();

    }

    private void FirstSet(){
        for(int i = 0; i<4;i++){
            for(int j=0;j<13;j++){
                deck[cardN] = j+1;
                cardN++;
            }
        }

        cardN = 0;
    }


    //山札のカードを全て出力させるテスト用の関数
    /*private void Test(){
        for(int i = 0; i<13*4;i++){
            System.out.println(deck[i]);
        }
    }*/


    public void Shuffle() {
        Random r = ThreadLocalRandom.current();
        for (int i = deck.length - 1; i > 0; i--) {
            int index = r.nextInt(i + 1);
           
            //swap
            int tmp = deck[index];
            deck[index] = deck[i];
            deck[i] = tmp;
        }
    }

    public int DrawACard(){
        cardN++;//次のカードを指し示すようにする
        if(cardN>= 13*4){
            System.out.println("山札のカードがすべてなくなりました。");
            return -1;
        }else{
            return deck[cardN-1];
        }
        
        
    }



    


}
