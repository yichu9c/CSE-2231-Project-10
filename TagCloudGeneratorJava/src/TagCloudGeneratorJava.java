import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class TagCloudGeneratorJava {

    /**
     * String for the list of possible separators (except for ").
     */
    private static final String SEPARATORSTRING = ". ,:;'{][}|/><?!`~1234567890"
            + "@#$%^&*()-_=+\" ";

    /**
     * Returns a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second.
     *
     */
    private static class IntegerLT implements Comparator<String> {

        Map<String, Integer> compareMap;

        IntegerLT(Map<String, Integer> compareMap) {
            this.compareMap = compareMap;
        }

        @Override
        public int compare(String str1, String str2) {
            int firstInt = this.compareMap.get(str1);
            int secondInt = this.compareMap.get(str2);
            if (secondInt > firstInt) {
                return 1;
            } else if (firstInt > secondInt) {
                return -1;
            } else {
                return str2.compareTo(str1);
            }
        }
    }

    /**
     * Returns a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second. Used to
     * compare each word alphabetically.
     *
     */
    public static class StringLT implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {

            int answer = o1.compareTo(o2);
            return answer;
        }
    }

    /**
     * Reads in a file and enters the words and corresponding counts into a tree
     * map.
     *
     * @param input
     *            the input stream.
     *
     * @param nWords
     *            the number of words the user wants outputted.
     * @requires requires that the input is open.
     * @return a TreeMap that has nWords entries that are sorted by counts.
     * @throws IOException
     */
    public static TreeMap<String, Integer> readFile(BufferedReader input,
            int nWords) throws IOException {
        assert input.ready() : "The input must be open";

        Map<String, Integer> wordMap = new HashMap<>();

        String toBeAdded = "";
        //use a try here because there may be an issue reading the line
        try {
            String line = input.readLine();

            //if the line is null that means that file has reached its end.
            while (line != null) {
                line = line.toLowerCase();
                //iterate through each character
                for (int i = 0; i < line.length(); i++) {

                    //if the character is not a separator add it to String toBeAdded
                    if (!SEPARATORSTRING
                            .contains(Character.toString(line.charAt(i)))) {

                        toBeAdded += Character.toString(line.charAt(i));
                        //if the character is a separator
                    } else if (SEPARATORSTRING
                            .contains(Character.toString(line.charAt(i)))) {

                        //if wordMap contains the word increment its corresponding value
                        if (toBeAdded != "" && wordMap.containsKey(toBeAdded)) {
                            int value = wordMap.get(toBeAdded);
                            value++;
                            wordMap.put(toBeAdded, value);
                            //if the wordMap doesn't have it add it with value 1
                        } else if (toBeAdded != ""
                                && !wordMap.containsKey(toBeAdded)) {
                            wordMap.put(toBeAdded, 1);
                        }
                        //reset toBeAdded to add more words
                        toBeAdded = "";
                    }
                }
                //If a word proceeds with the end of file then we need to check
                if (toBeAdded != "" && wordMap.containsKey(toBeAdded)) {
                    int value = wordMap.get(toBeAdded);
                    value++;
                    wordMap.put(toBeAdded, value);
                } else if (toBeAdded != "" && !wordMap.containsKey(toBeAdded)) {
                    wordMap.put(toBeAdded, 1);
                }

                line = input.readLine();
            }

            //If a word proceeds with the end of file then we need to check
            if (toBeAdded != "" && wordMap.containsKey(toBeAdded)) {
                int value = wordMap.get(toBeAdded);
                value++;
                wordMap.put(toBeAdded, value);
            } else if (toBeAdded != "" && !wordMap.containsKey(toBeAdded)) {
                wordMap.put(toBeAdded, 1);
            }

        } catch (IOException a) {
            System.err.print("Error opening reading from the file");
        }
        //Create a new comparator and treeMap that takes the comparator
        IntegerLT c = new IntegerLT(wordMap);
        TreeMap<String, Integer> countSortedMap = new TreeMap<>(c);
        countSortedMap.putAll(wordMap);

        //Makes a new map that will hold nWords amount of words and counts
        TreeMap<String, Integer> truncatedMap = new TreeMap<>(c);
        Set<Map.Entry<String, Integer>> pairSetTwo = countSortedMap.entrySet();
        //Iterate nWord times and add it to the truncated map.
        int i = 0;
        while (i < nWords && pairSetTwo.size() > 0) {

            Map.Entry<String, Integer> entry = countSortedMap.firstEntry();
            truncatedMap.put(entry.getKey(), entry.getValue());
            pairSetTwo.remove(entry);
            i++;
        }
        return truncatedMap;
    }

    /**
     * @param inputMap,
     *            the truncated map that is ready to be sorted alphabetically
     * @param comparator,
     *            the comparator that is used, in this case StringLT
     * @return a the truncated map that is sorted alphabetically.
     */
    public static TreeMap<String, Integer> createSortedMap(
            TreeMap<String, Integer> inputMap, Comparator comparator) {
        //Created new TreeMap with ordering defined by the comparator.
        TreeMap<String, Integer> result = new TreeMap<>(comparator);
        //Adds all the entries from truncated map into this map which then sorts
        // it alphabetically.
        result.putAll(inputMap);

        return result;
    }

    /**
     * Return a font size that corresponds to the given count of a word.
     *
     * @param oldValue,
     *            the given count of a word in the text
     * @param largest
     *            the most counts within the sorting machine
     * @param smallest
     *            the least count within the sorting machine
     * @return returns bit of html code that has a font size that corresponds
     *
     *         the count of the word in the text.
     */
    private static String fontSize(int oldValue, int largest, int smallest) {

        int answer = (((oldValue - smallest) * (48 - 11))
                / (largest - smallest)) + 11;

        return "f" + answer;
    }

    /**
     * Outputs the header for the index HTML file.
     *
     * @param output
     *            The output file stream
     * @param nWords
     *            the number of words the user chose to have outputed
     * @param fileName
     *            The name of the file the user desired
     * @requires out.is_open
     * @ensures output file has the header for the index HTML file
     */
    public static void outputHeader(PrintWriter output, String fileName,
            int nWords) {
        /*
         * Outputs the beginning code of the index HTML file to the output file
         * stream
         */
        output.println("<html>");
        output.println("   <head>");
        output.println("      <title>Top " + nWords + " words in " + fileName
                + "</title>");
        output.println(
                "      <link href=\"http://web.cse.ohio-state.edu/software/2231"
                        + "/web-sw2/assignments/projects/tag-cloud-generator/data/"
                        + "tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        output.println("      <link href=\"tagcloud.css\" rel=\"stylesheet\" "
                + "type=\"text/css\">");
        output.println("   </head>");
        output.println("   <body>");
        output.println(
                "      <h2>Top " + nWords + " words in " + fileName + "</h2>");
        output.println("      <hr>");
        output.println("      <div class=\"cdiv\">");
        output.println("         <p class=\"cbox\">");

    }

    /**
     * Outputs the footer for the index HTML file.
     *
     * @param out
     *            The output file stream
     * @requires out.is_open
     * @ensures output file has the closing braces for the index HTML file
     */
    public static void outputFooter(PrintWriter output) {
        /*
         * Outputs the closing code of the index HTML file to the output file
         * stream
         */
        output.println("         </p>");
        output.println("      </div>");
        output.println("   </body>");
        output.print("</html>");
    }

    /**
     * Outputs the words and corresponding counts to the table in the index HTML
     * file.
     *
     * @param counts
     *            The map of the words and their corresponding occurrences in
     *            the input file.
     * @param out
     *            The output file stream
     * @requires out.is_open
     * @ensures output file has the code to output the table of words and counts
     *          in the HTML file.
     */
    public static void outputCounts(PrintWriter output,
            TreeMap<String, Integer> wordCount, int largest, int smallest) {

        Set<Map.Entry<String, Integer>> entries = wordCount.entrySet();

        /*
         * This queue has all the words within in the map
         */
        for (Map.Entry<String, Integer> entry : entries) {

            int value = entry.getValue();

            String fontSize = fontSize(value, largest, smallest);
            output.println("            <span style=\"cursor:default\" class=\""
                    + fontSize + "\" title=\"count: " + value + "\">"
                    + entry.getKey() + "</span>");
        }
    }

    /**
     * @param value
     *            String is going to be tested whether is a valid int.
     * @param defaultVal
     *            returned if not valid, used so that loop can run continuously.
     * @return -1 if not a valid input, else it returns int value of string.
     *
     */
    public static int tryParseInt(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
        }
        return defaultVal;
    }

    public static void main(String[] args) throws IOException {
        //Get the file name
        System.out.print("Enter location and name of input file: ");
        Scanner input = new Scanner(System.in);
        String inputFile = input.nextLine();
        //Get the output file name
        System.out.println();
        System.out.print("Enter name of output file: ");
        String outputFile = input.nextLine();
        //Get the number of words the user wants outputted.
        System.out.println();
        System.out.print("How many words would you like: ");
        String n = input.nextLine();
        //Used to verify if the number of words is a valid input.
        int nWords = tryParseInt(n, -1);

        while (nWords == -1) {
            System.out.print("Not a valid input enter another: ");
            n = input.nextLine();
            nWords = tryParseInt(n, -1);

        }
        //closes the scanner.
        input.close();

        //Try catch when opening an input stream.
        BufferedReader inputStream;
        try {
            inputStream = new BufferedReader(new FileReader(inputFile));

        } catch (IOException e) {
            System.err.println("Error opening file");
            return;
        }
        //Creates a map that is sorted by counts.
        TreeMap<String, Integer> wordCount = readFile(inputStream, nWords);

        int largest = wordCount.firstEntry().getValue();
        int smallest = wordCount.lastEntry().getValue();

        StringLT stringComparator = new StringLT();
        //Creates a map that is sorted Alphabetically.
        TreeMap<String, Integer> mapSortedAlphabetically = createSortedMap(
                wordCount, stringComparator);
        //Try catch when closing the input stream.
        try {
            inputStream.close();
        } catch (IOException e) {
            System.err.println("Error could not close input stream.");
        }

        PrintWriter output = null;
        //Try catch when opening the output stream.
        try {
            output = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFile)));
        } catch (IOException e) {
            System.err.println("Error opening the printWriter");
        }

        outputHeader(output, inputFile, nWords);
        outputCounts(output, mapSortedAlphabetically, largest, smallest);
        outputFooter(output);

        output.close();
    }
}
