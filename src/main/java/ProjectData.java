import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProjectData {

    private int columnCount;

    private Map<String, ArrayList<Long>> searchData = new HashMap<>();

    public void setColumnCount(int columnCount){
        this.columnCount = columnCount - 1;
    }

    public int getColumnCount(){
        return this.columnCount;
    }

    public void setSearchData(Map<String, ArrayList<Long>> searchData){
        this.searchData = searchData;
    }

    public Map<String, ArrayList<Long>> getSearchData(){
        return this.searchData;
    }
}
