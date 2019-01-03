import java.util.Random;
// javac RandomUrlGenerator.java & java RandomUrlGenerator
public class RandomUrlGenerator {
        // https://en.wikipedia.org/wiki/List_of_most_popular_websites
        static String baseHosts="google.com,youtube.com,facebook.com,baidu.com,wikipedia.org,reddit.com,yahoo.com,qq.com,taobao.com,google.co.in,amazon.com,tmall.com,twitter.com,sohu.com,instagram.com,vk.com,live.com,jd.com,sina.com.cn,weibo.com,yandex.ru,360.cn,google.co.jp,google.co.uk,list.tmall.com,google.ru,google.com.br,netflix.com,google.de,google.com.hk,pornhub.com,twitch.tv,google.fr,linkedin.com,yahoo.co.jp,t.co,csdn.net,microsoft.com,bing.com,office.com,ebay.com,alipay.com,xvideos.com,google.it,google.ca,mail.ru,ok.ru,google.es,pages.tmall.com,msn.com,google.com.tr,google.com.au,whatsapp.com,spotify.com,google.pl,google.co.id,xhamster.com,google.com.ar,xnxx.com,google.co.th,Naver.com,sogou.com,samsung.com,accuweather.com,goo.gl,sm.cn,googleweblight.com";
        public static void main(String[] args){
                // test
                System.out.println(genUrlRandomly());
        }
        public static String genUrlRandomly() {
                String urlString="https://"+genHostRandomly();
                // System.out.println(urlString);
                return urlString;
        }
        public static String genHostRandomly() {
                String[] splitedHosts=baseHosts.split(",",0);
                int randomIndex = new Random().nextInt(splitedHosts.length);
                return splitedHosts[randomIndex];
        }
}
