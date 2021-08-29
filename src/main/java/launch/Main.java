package launch;

import org.apache.catalina.startup.Tomcat;


public class Main {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        String webPort = "8080";

        tomcat.setPort(Integer.parseInt(webPort));

        tomcat.start();
        tomcat.getServer().await();
    }
}
