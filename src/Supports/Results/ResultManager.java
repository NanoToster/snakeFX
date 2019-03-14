package Supports.Results;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ResultManager {
    private List<Result> resultList;

    public ResultManager(ResultSet resultSet) throws SQLException {
        this.resultList = new ArrayList<>();
        while (resultSet.next()) {
            this.resultList.add(new Result(resultSet.getString(2),
                    resultSet.getInt(3)));
        }
        sortResults();
    }

    private void sortResults() {
//        Collections.sort(resultList, new Comparator (){
//            public int compare(Result o1, Result o2) {
//                return o1.compareTo(o2);
//            }
//        });
    }

}
