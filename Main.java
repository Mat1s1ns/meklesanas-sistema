import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("START");

            Database.createTable();
            Database.insertData();

            List<String> products = Database.getAllProducts();

            Trie trie = new Trie();

            for (String product : products) {
                String[] words = product.toLowerCase().split(" ");
                for (String word : words) {
                    trie.insert(word);
                }
            }

            System.out.println("Trie built");

            Server.start(trie);

            System.out.println("Server started");

            System.in.read();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}