package business.Analize;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static real.RabbitQueue.*;

@Component
public class topLayerAnalize implements Analizer{

    private Document document;

    public static void quickSort(List<Double> array, List<String> array2, int low, int high) {
        if (array.size() == 0)
            return;

        if (low >= high)
            return;

        int middle = low + (high - low) / 2;
        Double opora = array.get(middle);

        int i = low, j = high;
        while (i <= j) {
            while (array.get(i) < opora) {
                i++;
            }

            while (array.get(j) > opora) {
                j--;
            }

            if (i <= j) {
                Double temp = array.get(i);
                String temp2 = array2.get(i);

                array.set(i, array.get(j));
                array2.set(i, array2.get(j));

                array.set(j, temp);
                array2.set(j, temp2);

                i++;
                j--;
            }
        }

        if (low < j)
            quickSort(array, array2, low, j);

        if (high > i)
            quickSort(array, array2, i, high);
    }

    public static void quickSort(List<String> array, List<String> strArray1,List<String> strArray2,List<String> strArray3,List<String> strArray4, int low, int high) {
        if (array.size() == 0)
            return;

        if (low >= high)
            return;

        int middle = low + (high - low) / 2;
        Double opora = Double.parseDouble(array.get(middle));

        int i = low, j = high;
        while (i <= j) {
            while (Double.parseDouble(array.get(i)) < opora) {
                i++;
            }

            while (Double.parseDouble(array.get(j)) > opora) {
                j--;
            }

            if (i <= j) {
                Double temp = Double.parseDouble(array.get(i));
                String StrTemp1 = strArray1.get(i);
                String StrTemp2 = strArray2.get(i);
                String StrTemp3 = strArray3.get(i);
                String StrTemp4 = strArray4.get(i);

                array.set(i, array.get(j));
                strArray1.set(i, strArray1.get(j));
                strArray2.set(i, strArray2.get(j));
                strArray3.set(i, strArray3.get(j));
                strArray4.set(i, strArray4.get(j));

                array.set(j, String.valueOf(temp));
                strArray1.set(j, StrTemp1);
                strArray2.set(j, StrTemp2);
                strArray3.set(j, StrTemp3);
                strArray4.set(j, StrTemp4);

                i++;
                j--;
            }
        }

        if (low < j)
            quickSort(array, strArray1,strArray2,strArray3,strArray4, low, j);

        if (high > i)
            quickSort(array, strArray1,strArray2,strArray3,strArray4, i, high);
    }

    @Override
    public String analize(Update update, String type) {

        if(type.equals(DOC_MESSAGE_UPDATE)){

            document = update.getMessage().getDocument();

            return document.getFileName();
        }


        return null;


    }

    public List<List<String>> analyzeExelHomeWork(File file) throws IOException {

        FileInputStream fileInputStream1 = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream1);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        int pos = 0;
        int pos2 = 0;
        boolean first = true;
        List<String> fullNameList = new ArrayList<>();
        List<Double> percentageHomeworkList = new ArrayList<>();


        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if(pos2 == 0){
                    if(cell.toString().equals("Percentage Homework")){
                        pos2 = pos;
                        pos = 0;
                    }else
                        pos++;

                }else{

                    if(first){
                        fullNameList.add(cell.toString());
                        first = false;
                    }

                    if(pos == pos2){
                        percentageHomeworkList.add(Double.parseDouble(cell.toString()));
                        pos = 0;
                    }else
                        pos++;
                }
            }
            pos = 0;
            first = true;
        }

        fullNameList.remove(0);
        List<Integer> arr = new ArrayList<>();
        for(int i = 0; i < percentageHomeworkList.size(); i++){
            if(percentageHomeworkList.get(i) > 49.9){
                arr.add(i);
            }
        }
        int yy = 0;
        for(int elem : arr){
            fullNameList.remove(elem-yy);
            percentageHomeworkList.remove(elem-yy);
            yy++;
        }

        quickSort(percentageHomeworkList, fullNameList, 0, percentageHomeworkList.size()-1);

        List<String> list1 = new ArrayList<>();
        for(Double d : percentageHomeworkList){
            list1.add(d.toString());
        }


        List<List<String>> result_list = new ArrayList<>();
        result_list.add(fullNameList);
        result_list.add(list1);

        return result_list;

    }

    public static List<Double> averageScore(List<Double> classmark, List<Double> homeMark, List<Double> ExamMark) {

        List<Double> averageScore = new ArrayList<>();

        for (int i = 0; i < classmark.size(); i++) {
            if (ExamMark.get(i) == 904) {
                averageScore.add((classmark.get(i) + homeMark.get(i)) / 2);
            } else {
                averageScore.add((classmark.get(i) + homeMark.get(i) + ExamMark.get(i)) / 3);
            }
        }
        return averageScore;
    }

    public List<List<String>> analyzeExelAverageScore(File file) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        int pos_1 = 0;
        int pos_2 = 0;
        int pos_3 = 0;

        int pos_temp = 0;

        List<String> fullNameList = new ArrayList<>();

        List<Double> classMark = new ArrayList<>();
        List<Double> homeMark = new ArrayList<>();
        List<Double> ExamMark = new ArrayList<>();

        boolean data_check = true;

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                if(data_check) {
                    if(cell.getStringCellValue().equals("Homework")){
                        pos_2 = pos_temp;
                    }else if(cell.getStringCellValue().equals("Classroom")){
                        pos_1 = pos_temp;
                    } else if (cell.getStringCellValue().equals("Average score")) {
                        pos_3 = pos_temp;
                    }
                }else{

                    if(pos_temp == 0){
                        fullNameList.add(cell.getStringCellValue());
                    }else if(pos_temp == pos_1){
                        classMark.add(Double.valueOf(cell.toString()));
                    }else if(pos_temp == pos_2){
                        homeMark.add(Double.valueOf(cell.toString()));
                    }else if(pos_temp == pos_3){
                        if(cell.toString().equals("-")){
                            ExamMark.add(904.0);//error code
                        }else{
                            ExamMark.add(Double.valueOf(cell.toString()));
                        }

                    }
                }

                pos_temp++;
            }
            data_check = false;
            pos_temp = 0;
        }

        List<Double> avs = averageScore(classMark,homeMark,ExamMark);


        Iterator<Double> avsIterator = avs.iterator();
        Iterator<Double> classMarkIterator = classMark.iterator();
        Iterator<Double> homeMarkIterator = homeMark.iterator();
        Iterator<Double> examMarkIterator = ExamMark.iterator();
        Iterator<String> fullNameIterator = fullNameList.iterator();

        while (avsIterator.hasNext()) {
            double value = avsIterator.next();
            if (value > 5) {
                avsIterator.remove();
                classMarkIterator.next();
                classMarkIterator.remove();
                homeMarkIterator.next();
                homeMarkIterator.remove();
                examMarkIterator.next();
                examMarkIterator.remove();
                fullNameIterator.next();
                fullNameIterator.remove();
            }
        }



        List<String> strArr1 = new ArrayList<>();
        List<String> strArr2 = new ArrayList<>();
        List<String> strArr3 = new ArrayList<>();
        List<String> strArr4 = new ArrayList<>();


        double scale = Math.pow(10, 3);
        for(int i = 0; i < classMark.size(); i++){
            strArr1.add(classMark.get(i).toString());
            strArr2.add(homeMark.get(i).toString());
            strArr3.add(ExamMark.get(i).toString());
            strArr4.add(String.valueOf(Math.ceil(avs.get(i) * scale) / scale));
        }

        quickSort(strArr4,strArr1,strArr2,strArr3,fullNameList,0,classMark.size()-1);

        return List.of(fullNameList,strArr1,strArr2,strArr3,strArr4);

    }


    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}

