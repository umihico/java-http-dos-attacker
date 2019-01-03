
## 選定問題

**（問題４）  
指定したURLに対し、指定した時間に渡りGETメソッドでHTTP要求を繰返し行なう負荷テスト用プログラムをC++あるいはJavaにより作成せよ。可能な限り（実行マシンの性能が許す限り）最大限の負荷を与えられるように設計せよ。また実行した要求数、正常応答数、発生したエラー数とその内容をテキストレベルで分かりやすく出力すること。**

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

攻撃マシンスペック
Surface Pro 3, Windows 10 Pro, Core i5 4300U 1.9GHz, 8GB
被攻撃マシン
localhost IIS Windows
```
> java HttpDosPenetrator 30 http://127.0.0.1/
Target URL:
http://127.0.0.1/
Attacking for '30' seconds...
passed 30 sec  'total' 187628 '200':187628
---Summary---
---status code '200'  total '187628' times  '1' patterns body text---
---status code '200'  frequent body text '187628' times---

'<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"><html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" /><title>IIS Windows</title><style type="text/css"><!--body {     color:#000000;  background-color:#0072C6;       margin:0;}#container {   margin-left:auto;       margin-right:auto;      text-align:center;      }a img {    border:none;}--></style></head><body><div id="container"><a href="http://go.microsoft.com/fwlink/?linkid=66138&amp;clcid=0x409"><img src="iisstart.png" alt="IIS" width="960" height="600" /></a></div></body></html>'

---Summary end---
```  
攻撃マシンスペック
AWS EC2 Amazon Linux AMI t3.xlarge, 4vCPU, 8GB (Tokyo region)
被攻撃マシン
AWS lambda (Tokyo region)
```
$ java HttpDosPenetrator 30 https://1jex0xaj69.execute-api.ap-northeast-1.amazonaws.com/default/java-dos-test-site
Target URL:
https://1jex0xaj69.execute-api.ap-northeast-1.amazonaws.com/default/java-dos-test-site
Attacking for '30' seconds...
passed 30 sec  'total' 131224 '200':131224
---Summary---
---status code '200'  total '131224' times  '1' patterns body text---
---status code '200'  frequent body text '131224' times---

'"Hello from Lambda!"'

---Summary end---
```

攻撃マシンスペック  
AWS EC2 Amazon Linux AMI t3.xlarge, 4vCPU, 8GB (Tokyo region)  
被攻撃マシン  
[List of most popular websites　https://en.wikipedia.org/wiki/List_of_most_popular_websites](https://en.wikipedia.org/wiki/List_of_most_popular_websites)より  
\*HttpRequest.java 38行目の`// url=new URL(new RandomUrlGenerator().genUrlRandomly());`をコメントアウト解除により使用できるテスト用機能。様々なレスポンスが得られる。  

```
$ java HttpDosPenetrator 30 https://1jex0xaj69.execute-api.ap-northeast-1.amazonaws.com/default/java-dos-test-site
Target URL:
https://1jex0xaj69.execute-api.ap-northeast-1.amazonaws.com/default/java-dos-test-site
Attacking for '30' seconds...
passed 30 sec  'total' 4985 '200':2580 '301':307 '302':310 '502':9 '503':1518 '404':94 '429':73 'error':94
詳細内容はResult.txtを参照すること
```
