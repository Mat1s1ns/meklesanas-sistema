import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

public class Server {

    public static void start(Trie trie) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/search", (HttpExchange exchange) -> {

            String query = exchange.getRequestURI().getQuery();
            String q = "";

            if (query != null && query.startsWith("q=")) {
                q = query.substring(2).toLowerCase();
            }

            List<String> results = trie.autocomplete(q);

            String json = "[";
            int limit = Math.min(10, results.size());

            for (int i = 0; i < limit; i++) {
                json += "\"" + results.get(i) + "\"";
                if (i < limit - 1) json += ",";
            }
            json += "]";

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            exchange.sendResponseHeaders(200, json.length());

            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes());
            os.close();
        });

        server.start();
        System.out.println("Server running on http://localhost:8080/search");
    }
}