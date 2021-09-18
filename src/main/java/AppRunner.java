import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class AppRunner {

    public static void main(String[] args) throws IOException {
        System.out.print("Введите название файла: ");
        String fileName;
        fileName = args[0];
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        var allSymbols = new HashMap<String, Integer>();

        String line;
        int countSym = 0;
        double info = 0;

        while ((line = br.readLine()) != null) {
            char[] chars = line.toCharArray();

            for (char aChar : chars) {
                countSym++;
                if (!allSymbols.containsKey(String.valueOf(aChar))) {
                    allSymbols.put(String.valueOf(aChar), 1);
                } else {
                    allSymbols.replace(String.valueOf(aChar), allSymbols.get(String.valueOf(aChar)) + 1);
                }
            }
        }
        DecimalFormat numberFormat = new DecimalFormat("0.000000");

        File file = new File("result.txt");
        boolean newFile = file.createNewFile();
        if (!newFile) {
            throw new RuntimeException("file not created");
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        Map<String, Integer> sortByValue = sortByValue(allSymbols);
        String res;
        for (Map.Entry<String, Integer> map : sortByValue.entrySet()) {
            double freq = (double) map.getValue() / countSym;

            if (map.getKey().equals(" "))
                res = "space" + " : " + map.getValue() + " : " + numberFormat.format(freq);
            else
                res = map.getKey() + " : " + map.getValue() + " : " + numberFormat.format(freq);

            bufferedWriter.write(res + "\n");
            System.out.println(res);
            info += (freq * log2(freq));
            bufferedWriter.flush();
        }
        System.out.println(Math.log1p(625));
        bufferedWriter.write("countSym = " + countSym + "\n");
        bufferedWriter.write("info = " + info * -1);
        bufferedWriter.flush();

        System.out.println("countSym = " + countSym);
        System.out.println("info = " + info * -1);
    }

    static Map<String, Integer> sortByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private static double log2(double num) {
        return Math.log(num) / Math.log(2);
    }
}
