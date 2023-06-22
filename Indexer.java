import java.util.*;
import java.io.*;

class Indexer {
    private Set<String> excludeWords;
    private Map<String, Set<Integer>> wordIndex;

    public Indexer() {
        excludeWords = new HashSet<>();
        wordIndex = new TreeMap<>();
    }

    public void readExcludedWords(String excludedWords) throws IOException {
        FileReader fr = new FileReader(excludedWords);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            excludeWords.add(line.trim());
        }
        br.close();
        fr.close();
    }

    public void buildIndex(String[] pages) throws IOException {
        for (int i = 0; i < pages.length; i++) {
            String currentPage = pages[i];
            FileReader fr = new FileReader(currentPage);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String words[] = line.split("\\s+");
                for (String word : words) {
                    word = word.toLowerCase();
                    if (!excludeWords.contains(word)) {
                        Set<Integer> page = wordIndex.getOrDefault(word, new HashSet<>());
                        page.add(i + 1);
                        wordIndex.put(word, page);
                    }
                }
            }
            br.close();
            fr.close();
        }
    }

    public void writeIndex(String index) throws IOException {
        FileWriter fw = new FileWriter(index, false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        pw.println("Word : Page Numbers\r\n" +
                "-------------------");
        for (Map.Entry<String, Set<Integer>> entry : wordIndex.entrySet()) {
            String word = entry.getKey();
            if (!word.matches("[a-z]+")) {
                if (!word.startsWith("\u00E2")) 
                {
                    continue;
                }
            }
            Set<Integer> pages = entry.getValue();
            pw.print(word + " : ");
            boolean isFirstPage = true;
            for (int page : pages) {
                if (!isFirstPage) {
                    pw.print(", ");
                }
                pw.print(String.valueOf(page));
                isFirstPage = false;
            }
            pw.println();
        }
        pw.close();
        bw.close();
        fw.close();
    }
}