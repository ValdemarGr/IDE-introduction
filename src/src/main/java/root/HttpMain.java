package root;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import root.file.Worker;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class HttpMain {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/hello", new HelloHandler());
        server.createContext("/fetchFile", new FileHandler());
        server.setExecutor(null);
        server.start();
    }

    public static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            handleRequest(exchange);
        }

        static String getResponse() {
            return "Hello IMADAAAAA!!!";
        }

        static void handleRequest(HttpExchange exchange) throws IOException {
            Stream.of(1,2,3,4,5,6).map(i -> i * 2).filter(i -> i > 3).distinct().limit(3).forEach(System.out::println);
            String response = getResponse();
            exchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }

    public static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            handleRequest(exchange);
        }

        public static String getPath() {
            return "/home/valde/data";
        }

        static void handleRequest(HttpExchange exchange) throws IOException {
            Worker worker = new Worker(getPath());
            Optional<String> executedWorkload = worker.executeWorkload(integer -> integer == 42);
            executedWorkload.map( res -> {
                try {
                    exchange.sendResponseHeaders(200, res.length());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                OutputStream outputStream = exchange.getResponseBody();
                try {
                    outputStream.write(res.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return res;
            });
        }
    }
}
