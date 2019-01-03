import java.util.concurrent.Executors;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.Queue;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Map.Entry;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Comparator;
// javac ResultPrinter.java & java ResultPrinter
public class ResultPrinter {
        private CompletionService<HashMap<String,String> > doneRequestQueue;
        private long endTime;
        private long startTime;
        private long lastPrintTime;
        private HashMap<String,HashMap<String,Long> > statusCodeCountDict;
        public ResultPrinter(long attackSeconds,String targetUrl,CompletionService<HashMap<String,String> > doneRequestQueue){
                System.out.println("Target URL:");
                System.out.println(targetUrl);
                this.startTime = System.currentTimeMillis();
                this.endTime = this.startTime+(attackSeconds*1000);
                System.out.println("Attacking for '"+attackSeconds+"' seconds...");
                this.doneRequestQueue=doneRequestQueue;
                this.lastPrintTime=0;

        }
        public void printRealtimeStatus() throws Exception {
                this.statusCodeCountDict= new HashMap<String,HashMap<String,Long> >();
                long total = 0l;
                while (true) {
                        if (System.currentTimeMillis()>this.endTime) {
                                // System.out.println("Printer break.");
                                break;
                        }
                        total++;
                        // Future<HashMap<String,String>> future = this.doneRequestQueue.poll(10,TimeUnit.SECONDS);
                        Future<HashMap<String,String> > future = this.doneRequestQueue.take();
                        HashMap<String,String> newResult = future.get();
                        String statusCode=newResult.get("statusCode");
                        String detail=newResult.get("detail");
                        incrementSameStatusCount(statusCode,detail);
                        if (System.currentTimeMillis()>lastPrintTime+200) {
                                lastPrintTime=System.currentTimeMillis();
                                printRealtimeStatusFunc(total);
                        }
                }
                printRealtimeStatusFunc(total);
        }
        public void incrementSameStatusCount(String statusCode, String detail){
                if (statusCodeCountDict.containsKey(statusCode)) {
                        HashMap<String,Long> detailsDict= statusCodeCountDict.get(statusCode);
                        if (detailsDict.containsKey(detail)) {
                                detailsDict.put(detail,detailsDict.get(detail)+1);
                        } else {
                                detailsDict.put(detail,1l);
                        }
                        detailsDict.put("sum",detailsDict.get("sum")+1);
                } else {
                        HashMap<String,Long> detailsDict= new HashMap<String,Long>();
                        detailsDict.put(detail,1l);
                        detailsDict.put("sum",1l);
                        statusCodeCountDict.put(statusCode,detailsDict);
                }

        }
        public void printRealtimeStatusFunc(long total){
                // System.out.println("resultPrinter "+i+" :"+newResult);
                System.out.print("\r");
                System.out.print("passed "+(System.currentTimeMillis()-this.startTime)/1000 + " sec  'total' "+ total + " ");
                for (String key : statusCodeCountDict.keySet()) {
                        System.out.print("'"+key+"':" +((HashMap)statusCodeCountDict.get( key)).get( "sum" )+" ");
                }

        }
        public void printSummary(long requestedNum){
                System.out.println("");// new line to escape carriage return line
                System.out.println("---Summary---");
                for (String statusCode : statusCodeCountDict.keySet()) {
                        HashMap<String,Long> detailsDict=statusCodeCountDict.get(statusCode);
                        Long totalTimes=detailsDict.get("sum");
                        detailsDict.remove("sum");
                        int keySize=detailsDict.keySet().size();
                        System.out.println("---status code '" + statusCode + "'  total '"+totalTimes+"' times  '"+ keySize+"' patterns body text---");
                        int displayLimitNum=3;
                        if (statusCode=="error") {
                                displayLimitNum=999;
                        }
                        Map<String, Long> sortedMap = detailsDict.entrySet().stream()
                                                      .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                                      .limit(displayLimitNum)
                                                      .collect(Collectors.toMap(
                                                                       Map.Entry::getKey, Map.Entry::getValue, (e1, e2)->e1, LinkedHashMap::new));
                        for(Map.Entry<String, Long> entry : sortedMap.entrySet()) {
                                System.out.println("---status code '" + statusCode + "'  frequent body text '"+ entry.getValue()+"' times---");
                                System.out.println("");
                                System.out.println("'"+haedtailResponse(entry.getKey())+"'");
                                System.out.println("");

                        }

                }
                System.out.println("---Summary end---");

        }
        public String haedtailResponse(String responseText){
                if (responseText.length()<=800) {
                        return responseText;
                } else {
                        return haedResponse(responseText)+"///////////////////"+tailResponse(responseText);
                }
        }
        public String haedResponse(String responseText){
                return responseText.substring(0,Math.min(responseText.length(), 400));
        }
        public String tailResponse(String responseText){
                return responseText.substring(Math.max(0, responseText.length()-400),responseText.length());
        }
}
