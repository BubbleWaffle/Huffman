import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "file.txt";
        String text = readFile(fileName);
        String compresed_text = compress(text);
        writeFile("new_file.txt", compresed_text);
    }

    /*--A function that reads data from a file and displays it in the console.--*/
    private static String readFile(String fileName) throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
        }
        System.out.print("File content:\n");
        System.out.print(text);
        return text.toString();
    }

    /*--File compression function.--*/
    public static String compress(String content) {
        String text = content; //File content.

        /*--HashMap counting the number of character.--*/
        Map<Character, Integer> counter = new HashMap<>();
        for (char c : text.toCharArray()) {
            counter.put(c, counter.getOrDefault(c, 0) + 1);
        }
        System.out.print("\nCharacters:\n");

        for (Character key : counter.keySet()) {
            System.out.print("[" + key + "]->" + counter.get(key) + " | ");
        }

        /*--A PriorityQueue that stores and sets the data in order of priority.--*/
        PriorityQueue<huffmanTree> heap = new PriorityQueue<>((t1, t2) -> t1.frequency - t2.frequency);
        for (Map.Entry<Character, Integer> entry : counter.entrySet()) {
            heap.offer(new huffmanTree(entry.getKey(), entry.getValue(), null, null));
        }

        /*--The loop takes the 2 smallest values from the "heap" and sets them again.--*/
        while (heap.size() > 1) {
            huffmanTree t1 = heap.poll();
            huffmanTree t2 = heap.poll();
            heap.offer(new huffmanTree('\0', t1.frequency + t2.frequency, t1, t2));
        }

        /*--Dictionary of the code.--*/
        Map<Character, String> dictionary = new HashMap<>();

        huffmanCode(heap.peek(), new StringBuilder(), dictionary);
        System.out.print("\nDictionary:\n");

        for (Character key : dictionary.keySet()) {
            System.out.print("[" + key + "]->" + dictionary.get(key) + " | ");
        }

        /*--Compression.--*/
        StringBuilder compressedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            compressedText.append(dictionary.get(c));
        }
        System.out.print("\nCompressed file:\n");
        System.out.print(compressedText);
        return compressedText.toString();
    }

    /*--A function that creates a code for a character (works recursively).--*/
    private static void huffmanCode(huffmanTree tree, StringBuilder prefix,
                                    Map<Character, String> codes) {
        if (tree.isLeaf()) {
            codes.put(tree.character, prefix.toString());
            return;
        }
        huffmanCode(tree.left, prefix.append('0'), codes);
        prefix.deleteCharAt(prefix.length() - 1);
        huffmanCode(tree.right, prefix.append('1'), codes);
        prefix.deleteCharAt(prefix.length() - 1);
    }

    /*--Save to file.--*/
    private static void writeFile(String fileName, String text) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(text);
        }
    }
}