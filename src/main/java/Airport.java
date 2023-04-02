import exceptions.FileParserException;
import exceptions.ParameterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Airport {

    public static ProjectData projectData;
    public static FileBuild fileParser = new FileBuild();

    public static void mainLoop(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int columnCount;

        try {
            columnCount = checkArgs(args);
        }
        catch (ParameterException parameterException) {
            System.out.println(parameterException.getMessage());
            return;
        }

        projectData = new ProjectData();
        projectData.setColumnCount(columnCount);
        try {
            projectData.setSearchData(fileParser.fillMap());
        }
        catch (FileParserException fileParserException){
            System.out.println(fileParserException.getMessage());
            return;
        }

        ArrayList<Object> keys = new ArrayList<>(projectData.getSearchData().keySet());
        ArrayList<String> stringKeys = new ArrayList<>();
        for (Object key : keys) {
            stringKeys.add((String)key);
        }
        Collections.sort(stringKeys);

        Information.enterHello();
        String request = scanner.nextLine();

        while (request.compareTo("!quit") != 0) {
            long time = System.currentTimeMillis();
            long rowsCount = 0;

            ArrayList<Object> answerRequest = RequestJob.findRequiredRows(request, stringKeys);

            if (answerRequest.size() < 1) {
                Information.enterEmptyAnswer();
                Information.enterHello();
                request = scanner.nextLine();
                continue;
            }
            answerRequest = RequestJob.sortArray(answerRequest);
            time = System.currentTimeMillis() - time;

            for (Object i : answerRequest) {
                try {
                    for (long index : projectData.getSearchData().get(i)){
                        Information.enterAnswer(new Object[]{i,
                                fileParser.getRow(index)
                        });
                        rowsCount++;
                    }
                }
                catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
            Information.enterSearchData(time, rowsCount);
            Information.enterHello();
            request = scanner.nextLine();
        }
    }

    private static int checkArgs(String[] args) throws ParameterException {
        if (args.length != 1) {
            throw new ParameterException("Некорректное количество параметров!");
        }
        int columnCount;
        try {
            columnCount = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException numberFormatException) {
            throw new ParameterException("Параметр должен представлять числовое значение!");
        }
        return columnCount;
    }
}
