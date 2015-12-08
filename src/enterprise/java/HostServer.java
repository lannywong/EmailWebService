//package enterprise.java;
//import com.sun.net.httpserver.HttpServer;
//import com.sun.jersey.api.container.httpserver.HttpServerFactory;
//import java.io.IOException;
//
///**
// * Created by Student on 11/7/2015.
// */
//public class HostServer {
//    public static void main(String[] args) throws IOException {
//        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
//        server.start();
//
//        System.out.println("Server running");
//        System.out.println("Visit: http://localhost:9998/");
//        System.out.println("Hit return to stop...");
//        System.in.read();
//        System.out.println("Stopping server");
//        server.stop(0);
//        System.out.println("Server stopped");
//    }
//}
