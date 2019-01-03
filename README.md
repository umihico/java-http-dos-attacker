
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
$ java HttpDosPenetrator 30 https://1jex0xaj69.execute-api.ap-northeast-1.amazonaws.com/default/java-dos-test-site
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
$java HttpDosPenetrator 30 http://localhost
Note: Some input files use unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
Target URL:
http://localhost
Attacking for '30' seconds...
passed 30 sec  'total' 4985 '200':2580 '301':307 '302':310 '502':9 '503':1518 '404':94 '429':73 'error':94
---Summary---
---status code '200'  total '2580' times  '1865' patterns body text---
---status code '200'  frequent body text '89' times---

'<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"><html>  <head>    <title>t.co / Twitter</title>    <link href="https://abs.twimg.com/favicons/favicon.ico" rel="shortcut icon" type="image/x-icon" />    <style type="text/css">      * { margin: 0; padding: 0; border: 0; }      body { background: #eee; color: #999; border-top: 5px solid #5C9FC0; font-fa///////////////////ct-us">Contact</a></li>      <a href="https://blog.twitter.com/">Blog</a>      <a href="http://status.twitter.com/">Status</a>      <a href="https://dev.twitter.com">API</a>      <a href="https://help.twitter.com/">Help</a>      <a href="https://twitter.com/jobs">Jobs</a>      <a href="https://twitter.com/tos">TOS</a>      <a href="https://twitter.com/privacy">Privacy</a>    </div>  </body></html>'

---status code '200'  frequent body text '88' times---

'<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html><head><meta http-equiv="content-type" content="text/html; charset=utf-8"><meta http-equiv="content-style-type" content="text/css"><meta http-equiv="content-script-type" content="text/javascript"><meta name="description" content="日本最大級のポータルサイト。検索、オークション、ニュース、メール、コミュニティ、ショッピング、など80以上のサービスを展開。あ/////////////////////about.yahoo.co.jp/docs/info/terms/">利用規約</a> - <a href="https://about.yahoo.co.jp/docs/pr/disclaimer.html">免責事項</a> - <a href="https://about.yahoo.co.jp/info/mediastatement/">メディアステートメント</a> - <a href="https://privacy.yahoo.co.jp/">プライバシー</a></td></tr><tr><td align="center">Copyright (C) 2019 Yahoo Japan Corporation. All Rights Reserved.</td></tr></table></td></tr></table></center></body></html>'

---status code '200'  frequent body text '86' times---

'<!DOCTYPE html><html lang="ja" xmlns:og="http://ogp.me/ns#" xmlns:fb="https://www.facebook.com/2008/fbml" class="" >  <head>          <script src="//www.scdn.co/webpack/jquery-2.1.3.min.f68c652ef2d054f99e40.js"></script>                <script>            var spweb = spweb || {};spweb.config = {  environment: {    staticUrl: 'https://www.scdn.co'  },  sso: {    host: 'https://accounts.spotify.com'///////////////////     var ftBasicCollapseBtn = $('#ft-basic-collapse-btn');      if (typeof ftBasicCollapseBtn.attr('aria-expanded') === 'undefined' || ftBasicCollapseBtn.attr('aria-expanded') === 'false') {        ftBasicCollapseBtn.click();      }    });  </script>    <script async src="//vt.myvisualiq.net/2/afTxMmlGwCNRJiC5Bd75ug%3D%3D/vt-150.js"></script></body><!-- This page was rendered using Twig --></html>'

---status code '301'  total '307' times  '3' patterns body text---
---status code '301'  frequent body text '163' times---

'<html><head><title>301 Moved Permanently</title></head><body bgcolor="white"><center><h1>301 Moved Permanently</h1></center><hr><center>nginx</center></body></html>'

---status code '301'  frequent body text '74' times---

'<html><head><title>301 Moved Permanently</title></head><body bgcolor="white"><center><h1>301 Moved Permanently</h1></center><hr><center> NWS </center></body></html>'

---status code '301'  frequent body text '70' times---

''

---status code '302'  total '310' times  '4' patterns body text---
---status code '302'  frequent body text '84' times---

''

---status code '302'  frequent body text '81' times---

'<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN"><html><head><title>302 Found</title></head><body bgcolor="white"><h1>302 Found</h1><p>The requested resource resides temporarily under a different URI.</p><hr/>Powered by Tengine</body></html>'

---status code '302'  frequent body text '75' times---

'<html><head><title>302 Found</title></head><body bgcolor="white"><center><h1>302 Found</h1></center><hr><center>JengineD/1.7.2.1</center></body></html>'

---status code '502'  total '9' times  '9' patterns body text---
---status code '502'  frequent body text '1' times---

'<!DOCTYPE html><html lang="en"><head><script>          var __SUPPORTS_TIMING_API = typeof performance === 'object' && !!performance.mark && !! performance.measure && !!performance.getEntriesByType;          function __perfMark(name) { __SUPPORTS_TIMING_API && performance.mark(name); };          var __firstLoaded = false;          function __markFirstPostVisible() {            if (__firstLoaded) { ///////////////////ac8c1a53ad7d66d6e8a.js"></script><script defer="" type="text/javascript" src="https://www.redditstatic.com/desktop2x/Legacy~CollectionCommentsPage~CommentsPage~Frontpage~PostCreation~PostDraft~SearchResults~Subreddit.51bcad75f0d2dbce29cf.js"></script><script defer="" type="text/javascript" src="https://www.redditstatic.com/desktop2x/Legacy~Frontpage.caff5537ddcb010d829a.js"></script></body></html>'

---status code '502'  frequent body text '1' times---

'<!DOCTYPE html><html lang="en"><head><script>          var __SUPPORTS_TIMING_API = typeof performance === 'object' && !!performance.mark && !! performance.measure && !!performance.getEntriesByType;          function __perfMark(name) { __SUPPORTS_TIMING_API && performance.mark(name); };          var __firstLoaded = false;          function __markFirstPostVisible() {            if (__firstLoaded) { ///////////////////ac8c1a53ad7d66d6e8a.js"></script><script defer="" type="text/javascript" src="https://www.redditstatic.com/desktop2x/Legacy~CollectionCommentsPage~CommentsPage~Frontpage~PostCreation~PostDraft~SearchResults~Subreddit.51bcad75f0d2dbce29cf.js"></script><script defer="" type="text/javascript" src="https://www.redditstatic.com/desktop2x/Legacy~Frontpage.caff5537ddcb010d829a.js"></script></body></html>'

---status code '502'  frequent body text '1' times---

'<!DOCTYPE html><html lang="en"><head><script>          var __SUPPORTS_TIMING_API = typeof performance === 'object' && !!performance.mark && !! performance.measure && !!performance.getEntriesByType;          function __perfMark(name) { __SUPPORTS_TIMING_API && performance.mark(name); };          var __firstLoaded = false;          function __markFirstPostVisible() {            if (__firstLoaded) { ///////////////////ac8c1a53ad7d66d6e8a.js"></script><script defer="" type="text/javascript" src="https://www.redditstatic.com/desktop2x/Legacy~CollectionCommentsPage~CommentsPage~Frontpage~PostCreation~PostDraft~SearchResults~Subreddit.51bcad75f0d2dbce29cf.js"></script><script defer="" type="text/javascript" src="https://www.redditstatic.com/desktop2x/Legacy~Frontpage.caff5537ddcb010d829a.js"></script></body></html>'

---status code '503'  total '1518' times  '1503' patterns body text---
---status code '503'  frequent body text '15' times---

'<!--        To discuss automated access to Amazon data please contact api-services-support@amazon.com.        For information about migrating to our APIs refer to our Marketplace APIs at https://developer.amazonservices.com/ref=rm_5_sv, or our Product Advertising API at https://affiliate-program.amazon.com/gp/advertising/api/detail/main.html/ref=rm_5_ac for advertising use cases.--><!doctype html>///////////////////ent wrong on our end. Please go back and try again or go to Amazon's home page."></a>  </div>  <a href="/dogsofamazon/ref=cs_503_d" target="_blank" rel="noopener noreferrer"><img id="d" alt="Dogs of Amazon"></a>  <script>document.getElementById("d").src = "https://images-na.ssl-images-amazon.com/images/G/01/error/" + (Math.floor(Math.random() * 43) + 1) + "._TTD_.jpg";</script></div></body></html>'

---status code '503'  frequent body text '2' times---

'<!DOCTYPE html>  <html lang="en-us"><head>  <meta http-equiv="content-type" content="text/html; charset=UTF-8">      <meta charset="utf-8">      <title>Yahoo</title>      <meta name="viewport" content="width=device-width,initial-scale=1,minimal-ui">      <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">      <style>  html {      height: 100%;  }  body {      background: #fafafc url(ht///////////////////<!-- Not Found on Server -->  <table>  <tbody><tr>      <td>      <img src="https://s.yimg.com/nn/img/yahoo-logo-201402200629.png" alt="Yahoo Logo">      <h1 style="margin-top:20px;">Will be right back...</h1>      <p id="message-1">Thank you for your patience.</p>      <p id="message-2">Our engineers are working quickly to resolve the issue.</p>      </td>  </tr>  </tbody></table>  </body></html>'

---status code '503'  frequent body text '1' times---

'<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><html><head><meta http-equiv="content-type" content="text/html; charset=utf-8"><meta name="viewport" content="initial-scale=1"><title>https://google.com.au/</title></head><body style="font-family: arial, sans-serif; background-color: #fff; color: #000; padding:20px; font-size:18px;" onload="e=document.getElementById('captcha');if(e){e.///////////////////t computer using the same IP address may be responsible.  <a href="//support.google.com/websearch/answer/86640">Learn more</a><br><br>Sometimes you may be asked to solve the CAPTCHA if you are using advanced terms that robots are known to use, or sending requests very quickly.</div>IP address: 13.231.139.118<br>Time: 2019-01-03T10:45:21Z<br>URL: https://google.com.au/<br></div></div></body></html>'

---status code '404'  total '94' times  '1' patterns body text---
---status code '404'  frequent body text '94' times---

'<html><head><title>404 Not Found</title></head><body bgcolor="white"><script>with(document)with(body)with(insertBefore(createElement("script"),firstChild))setAttribute("exparams","aplus&sm_uuid=",id="tb-beacon-aplus",src=(location>"https"?"//g":"//g")+".alicdn.com/alilog/mlog/aplus_v2.js")</script><center><h1>404 Not Found</h1></center><hr><center>Userver</center></body></html>'

---status code '429'  total '73' times  '10' patterns body text---
---status code '429'  frequent body text '11' times---

'<!doctype html><html>  <head>    <title>Too Many Requests</title>    <style>      body {          font: small verdana, arial, helvetica, sans-serif;          width: 600px;          margin: 0 auto;      }      h1 {          height: 40px;          background: transparent url(//www.redditstatic.com/reddit.com.header.png) no-repeat scroll top right;      }    </style>  </head>  <body>    <h1>whoa ther///////////////////t but are spoofing one via your browser's user agentstring: please change your user agent string to avoid seeing this messageagain.</p><p>please wait 2 second(s) and try again.</p>    <p>as a reminder to developers, we recommend that clients make no    more than <a href="http://github.com/reddit/reddit/wiki/API">one    request every two seconds</a> to avoid seeing this message.</p>  </body></html>'

---status code '429'  frequent body text '10' times---

'<!doctype html><html>  <head>    <title>Too Many Requests</title>    <style>      body {          font: small verdana, arial, helvetica, sans-serif;          width: 600px;          margin: 0 auto;      }      h1 {          height: 40px;          background: transparent url(//www.redditstatic.com/reddit.com.header.png) no-repeat scroll top right;      }    </style>  </head>  <body>    <h1>whoa ther///////////////////t but are spoofing one via your browser's user agentstring: please change your user agent string to avoid seeing this messageagain.</p><p>please wait 7 second(s) and try again.</p>    <p>as a reminder to developers, we recommend that clients make no    more than <a href="http://github.com/reddit/reddit/wiki/API">one    request every two seconds</a> to avoid seeing this message.</p>  </body></html>'

---status code '429'  frequent body text '10' times---

'<!doctype html><html>  <head>    <title>Too Many Requests</title>    <style>      body {          font: small verdana, arial, helvetica, sans-serif;          width: 600px;          margin: 0 auto;      }      h1 {          height: 40px;          background: transparent url(//www.redditstatic.com/reddit.com.header.png) no-repeat scroll top right;      }    </style>  </head>  <body>    <h1>whoa ther///////////////////t but are spoofing one via your browser's user agentstring: please change your user agent string to avoid seeing this messageagain.</p><p>please wait 4 second(s) and try again.</p>    <p>as a reminder to developers, we recommend that clients make no    more than <a href="http://github.com/reddit/reddit/wiki/API">one    request every two seconds</a> to avoid seeing this message.</p>  </body></html>'

---status code 'error'  total '94' times  '4' patterns body text---
---status code 'error'  frequent body text '82' times---

'java.lang.NullPointerException'

---status code 'error'  frequent body text '6' times---

'java.io.IOException: Premature EOF'

---status code 'error'  frequent body text '5' times---

'javax.net.ssl.SSLHandshakeException: Remote host closed connection during handshake'

---status code 'error'  frequent body text '1' times---

'java.net.SocketException: Connection reset'

---Summary end---

```
