// java Test
public class Test {
        public static void main(String[] args) throws Exception {
                Integer attackSeconds=30;
                String targetUrl="https://uy34uokjte.execute-api.us-west-2.amazonaws.com/default/java-http-attacker-test-site";
                new HttpDosPenetrator().start(attackSeconds,targetUrl);
        }

}
