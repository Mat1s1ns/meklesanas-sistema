import java.util.*;

public class Trie {

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;
    }

    private TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;

        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }

        node.isEndOfWord = true;
    }

    public List<String> autocomplete(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode node = root;

        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return results;
            }
            node = node.children.get(c);
        }

        findWords(node, prefix, results);
        return results;
    }

    private void findWords(TrieNode node, String current, List<String> results) {
        if (node.isEndOfWord) {
            results.add(current);
        }

        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            findWords(entry.getValue(), current + entry.getKey(), results);
        }
    }
}