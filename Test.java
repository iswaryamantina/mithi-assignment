public class Test {
    
    public static void main(String[] args) {
        String pages[] = { "Page1.txt", "Page2.txt", "Page3.txt" };
        String excludePage = "exclude-words.txt";
        String index = "index.txt";

        Indexer indexer = new Indexer();
        try {
            indexer.readExcludedWords(excludePage);
            indexer.buildIndex(pages);
            indexer.writeIndex(index);
            System.out.println("Indexing Complete");
        } catch (Exception e) {
            System.out.println("An error occured while Indexing");
        }
    }
}