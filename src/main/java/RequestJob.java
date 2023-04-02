import java.math.BigDecimal;
import java.util.ArrayList;

public class RequestJob {

    public static ArrayList<Object> findRequiredRows(String template, ArrayList<String> lines) {
        ArrayList<Object> correctStrings = new ArrayList<>();
        template = template.toLowerCase();
        int start = binarySearch(template, lines);
        for (int i = start; i < lines.size(); i++) {
            String line = lines.get(i);
            String prefix = line.toLowerCase().substring(0, Math.min(template.length(), line.length()));
            if (template.compareTo(prefix) == 0) {
                correctStrings.add(line);
            }
            else {
                break;
            }
        }
        return correctStrings;
    }

    public static ArrayList<Object> sortArray(ArrayList<Object> list) {
        boolean isNumber = true;
        try {
            BigDecimal x = new BigDecimal(list.get(0).toString());
        }
        catch (Exception exception) {
            isNumber = false;
        }
        if (isNumber) {
            list.sort(((o1, o2) -> {
                o1 = new BigDecimal(o1.toString());
                o2 = new BigDecimal(o2.toString());
                return ((BigDecimal)o1).compareTo((BigDecimal)o2);
            }));
        }
        else {
            list.sort(((o1, o2) -> {
                o1 = o1.toString().toLowerCase();
                o2 = o2.toString().toLowerCase();
                return ((String)o1).compareTo((String)o2);
            }));
        }
        return list;
    }

    private static int binarySearch(String template, ArrayList<String> lines) {
        template = template.toLowerCase();
        int left = -1, right = lines.size(), middle = (left + right) / 2;
        while (right - left > 1) {
            String line = lines.get(middle).toLowerCase();
            if (template.compareTo(line) > 0) {
                left = middle;
            }
            else right = middle;
            middle = (left + right) / 2;
        }
        return right;
    }
}
