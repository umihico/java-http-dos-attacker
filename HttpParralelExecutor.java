import java.util.concurrent.CompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Future;
import java.util.HashMap;
// javac HttpParralelExecutor.java & java HttpParralelExecutor
public class HttpParralelExecutor {
        private CompletionService<HashMap<String,String> > doneRequestQueue;
        private BlockingQueue jobQueue;
        private Integer attackSeconds;
        private String targetUrl;
        private ExecutorService backgroundExecutorService;

        public HttpParralelExecutor(int attackSeconds,String targetUrl) {
                this.attackSeconds=attackSeconds;
                this.targetUrl=targetUrl;
                this.jobQueue=new LinkedBlockingQueue();
                this.backgroundExecutorService =genThreadPoolExecutor();
                this.doneRequestQueue=new ExecutorCompletionService<HashMap<String,String> >(backgroundExecutorService);
        }
        public ExecutorService genThreadPoolExecutor(){
                int vcpu = Runtime.getRuntime().availableProcessors();
                int threadMinNum= vcpu*100;
                return new ThreadPoolExecutor(threadMinNum, Integer.MAX_VALUE,
                                              60L, TimeUnit.SECONDS,
                                              this.jobQueue,new ThreadFactory() {
                        public Thread newThread(Runnable r) {
                                Thread t = Executors.defaultThreadFactory().newThread(r);
                                t.setDaemon(true);
                                return t;
                        }
                });
        }
        public CompletionService<HashMap<String,String> > getDoneRequestQueue(){
                return this.doneRequestQueue;
        }


        public Future startInBackground(){
                // System.out.println("startInBackground!!");
                ExecutorService execBackground = Executors.newSingleThreadExecutor();
                Future<Long> future = execBackground.submit(new backgroundJobProducer());
                execBackground.shutdown();
                return future;
        }
        public class backgroundJobProducer implements Callable<Long> {
                @Override
                public Long call() throws Exception {
                        int vcpu = Runtime.getRuntime().availableProcessors();
                        int jobQueueMaxSize=vcpu*20;
                        long endTime = System.currentTimeMillis()+(attackSeconds*1000);
                        long sumbitCnt = 0l;

                        while (true) {
                                if (System.currentTimeMillis()<endTime) {
                                        // doneRequestQueue.submit cause deadlock if inside queue has limitation when  without this.
                                        if (jobQueue.size()<jobQueueMaxSize) {
                                                sumbitCnt++;
                                                doneRequestQueue.submit(new HttpRequest(targetUrl));
                                        } else {
                                                Thread.sleep(1);
                                        }
                                } else {
                                        break;
                                }
                        }
                        backgroundExecutorService.shutdown();
                        return sumbitCnt;
                }
        }
}
