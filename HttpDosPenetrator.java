import java.util.concurrent.CompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.List;
import java.util.concurrent.Future;
// javac HttpDosPenetrator.java & java HttpDosPenetrator 30 https://uy34uokjte.execute-api.us-west-2.amazonaws.com/default/java-http-attacker-test-site
public class HttpDosPenetrator {
        public static void main(String[] args){
                Integer attackSeconds=Integer.parseInt(args[0]);
                String targetUrl=args[1];
        }
        public void start(Integer attackSeconds,String targetUrl) throws Exception {
                HttpParralelExecutor httpParralelExecutor = new HttpParralelExecutor(attackSeconds,targetUrl);
                ResultPrinter resultPrinter=new ResultPrinter(attackSeconds,targetUrl,httpParralelExecutor.getDoneRequestQueue());
                Future<Long> futureRequestedNum=httpParralelExecutor.startInBackground();
                resultPrinter.printRealtimeStatus();
                long requestedNum=futureRequestedNum.get();
                resultPrinter.printSummary(requestedNum);

        }
}
