import exceptions.FileParserException;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileBuild {

    private final String fileDataName = "airports.csv";

    public Map<String, ArrayList<Long>> fillMap() throws FileParserException {
        Map<String, ArrayList<Long>> data;
        try {
            RandomAccessFile file = new RandomAccessFile(fileDataName, "r");
            data = new HashMap<>();

            String line;
            long position = file.getFilePointer();
            while ((line = file.readLine()) != null) {
                var arrayList = data.get(rowData(line));
                if (arrayList == null) arrayList = new ArrayList<>();
                arrayList.add(position);
                data.put(rowData(line), arrayList);
                position = file.getFilePointer();
            }
            file.close();
        }
        catch (Exception exception) {
            throw new FileParserException("Ошибка при чтении файла airports.csv!");
        }
        return data;
    }

    public String getRow(Long index) throws FileParserException {
        try {
            RandomAccessFile file = new RandomAccessFile(fileDataName, "r");
            file.seek(index);
            String line = file.readLine();
            if (line.compareTo("") == 0){
                index++;
            }
            file.seek(index);
            line = file.readLine();
            file.close();
            return line;
        }
        catch (Exception exception) {
            throw new FileParserException("Ошибка при чтении файла!");
        }
    }

    private String rowData(String data) {
        Object[] cells = data.split(",");
        String cell = (String) cells[Airport.projectData.getColumnCount()];
        if (cell.charAt(0) == '"') cell = cell.substring(1, cell.length() - 1);
        return cell;
    }
}
