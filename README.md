
## 選定問題

**（問題４）
指定したURLに対し、指定した時間に渡りGETメソッドでHTTP要求を繰返し行なう
負荷テスト用プログラムをC++あるいはJavaにより作成せよ。可能な限り（実行
マシンの性能が許す限り）最大限の負荷を与えられるように設計せよ。また実行
した要求数、正常応答数、発生したエラー数とその内容をテキストレベルで分か
りやすく出力すること。**

## 動作環境  
+ Java 8  

## 実行方法
```
$ java HttpDosPenetrator SECONDS URL
```
例:30秒間指定url*にGETリクエストを送る場合
```
$ java HttpDosPenetrator 30 https://uy34uokjte.execute-api.us-west-2.amazonaws.com/default/java-http-attacker-test-site
```
\*urlは被攻撃用に作成したaws lambdaとaws gateway APIのurl。初期状態の"Hello from Lambda!"を応答する。  

## 実行結果

結果表示の仕様  
+ 実行時間中はステータスコード別にリクエストの完了件数をリアルタイムに表示する
+ (以下終了後の仕様)
+ リクエストが終了まで移行したジョブの全件数を表示
+ 全件数のうちステータスコード別に件数を表示
+ ステータスコード別にさらに共通のボディテキスト別に件数をカウント
+ ボディテキスト別に件数上位３件までボディを表示
+ ボディテキストの表示は最大８００文字とし、それ以上の場合は上から４００文字、下から４００文字を抽出する
+ ステータスコードを得られなかった例外のテキストは全テキストと件数を表示する  

実際の内容はResult.txtを参照すること

## 応用

HttpRequest.java 38行目
```
// url=new URL(new RandomUrlGenerator().genUrlRandomly());
```  
のコメントアウトを外すと、指定urlの代わりに世界でトップ５０のアクセス数を誇るサイトにランダムでリクエストする。
その結果、様々なレスポンスが得られそれぞれの挙動を調べることができる。  
実際の内容はVariousResult.txtを参照すること
