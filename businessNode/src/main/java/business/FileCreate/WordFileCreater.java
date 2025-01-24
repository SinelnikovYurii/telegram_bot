package business.FileCreate;




import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WordFileCreater {

    public void generateHomeworkAnalyseWordFile(List<List<String>> list) {

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        run.setFontSize(16);
        run.setBold(true);

        List<String> ll1 = list.get(0);
        List<String> ll2 = list.get(1);

        run.setText("Students who have fallen behind in their studies:");
        run.addBreak();

        for (int i = 0; i < ll1.size(); i++) {
            String str = ll1.get(i) + ": " + ll2.get(i);
            run.setText(str);
            run.addBreak();


        }

        try (FileOutputStream out = new FileOutputStream("Home Work Analyze.docx")) {
            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void generateAverageScoreWordFile(List<List<String>> list) {

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();

        run.setFontSize(16);
        run.setBold(true);

        List<String> list1 = list.get(0);
        List<String> list2 = list.get(1);
        List<String> list3 = list.get(2);
        List<String> list4 = list.get(3);
        List<String> list5 = list.get(4);

        run.setText("Students with low GPA:");
        run.addBreak();

        for (int i = 0; i < list1.size(); i++) {
            String str = list1.get(i) + ": " + list2.get(i) + "   " + list3.get(i) + "   " + list4.get(i) + "   " + list5.get(i);
            run.setText(str);
            run.addBreak();


        }

        try (FileOutputStream out = new FileOutputStream("Average Score Analyze.docx")) {
            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}


