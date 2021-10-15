# トランプ対戦ゲーム
## 1 概要
TCP通信を使って、P2Pのトランプカードゲーム(15点というゲーム)を制作  
ルールは子→親の順にトランプを任意の数だけ引き、15点に近い人が勝利するゲーム  
尚、15点を超えた場合は負けとなる。両者とも15点を超えるか、同じ点数の場合は引き分けとなる  

## 2 全体の流れ
ニックネーム、ポート番号、Server/Clientを選択し、通信開始  
Serverで対戦部屋をインスタンス化し、ゲーム進行  
複数回対戦可能  

![image](https://user-images.githubusercontent.com/51013828/137435923-852b590d-c2f1-49ae-9e82-fc138b664c96.png)


## 3 動作デモ
##### Server/Client共通部分
![image](https://user-images.githubusercontent.com/51013828/137436091-6684faac-aa26-4362-b512-08231c51f1ea.png)

##### Clientのドロー
・Server側での挙動  
![image](https://user-images.githubusercontent.com/51013828/137436280-01b7d14f-ab89-4a24-b90e-ce30261c1dfd.png)

・Client側での挙動  
![image](https://user-images.githubusercontent.com/51013828/137436326-9ec1aa84-6dce-411d-a39e-7d2851472221.png)

＊この後にServer側のドローがあるが、同じ挙動であるため割愛

##### 結果ともう一度対戦
・Server側での挙動  
![image](https://user-images.githubusercontent.com/51013828/137436443-61133996-652c-4bcc-bb96-54439f160da2.png)


・Client側での挙動  
![image](https://user-images.githubusercontent.com/51013828/137436466-4d35cc33-28d1-4210-bddc-baa7911a1ad4.png)

## 4 クラス図
![image](https://user-images.githubusercontent.com/51013828/137436525-e4485a27-90ff-4eae-b08f-c7bdd43c4545.png)

## 5 工夫した点
##### ユーザー目線
通信開始前の名前、ポート番号、Server/Clientの登録やゲーム終了後のもう一度対戦など、ゲーム外の機能も充実させた点。

ゲーム内での表示を工夫した。例えば、ゲーム内で自分と相手のニックネームが呼ばれたり、トランプの1,11,12,13はそれぞれA,J,Q,Kの表示にした点など。

ポート番号や選択肢が不適なものが入力された時のエラー処理をした点。

##### プログラマー目線
通信クラスと対戦部屋クラスの間で相互依存しないようにするために、UMLのデザインパターンを参考に仲介者クラスを用意した点。これにより、通信クラスは通信関連のことのみ、対戦部屋クラスはゲーム進行のことのみのように、各クラスの役割が明確になった。

UI(インターフェース)→仲介者、通信 (コントローラー)→ 対戦部屋、山札、手札(エンティティ)のような依存関係を意識して開発した点。

将来のUI拡張を意識して、出力はUIクラスのみで行い、他クラスではSystem.out.prntln()などの出力処理をしないようにした点。

継承、抽象クラス、Setter、Getterなどのオブジェクト指向を活用し、情報の隠蔽などのメリットを利用した点。




