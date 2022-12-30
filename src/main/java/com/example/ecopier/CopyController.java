package com.example.ecopier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CopyController {


    @FXML
    private Label firstNum;

    @FXML
    private Label lastNum;

    @FXML
    private Label filesQuan;

    @FXML
    private TextField textField;

    File ethalon;
    File copyList;
    int fileCounter = 0;
    String number = "";

    String month;
    List<String> edrpou = new ArrayList<>();



    @FXML
    protected void browseEthalon(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("D:\\"));
        fc.setTitle("Select file");
        ethalon = fc.showOpenDialog(null);
    }

    @FXML
    protected void browseCopyList(ActionEvent event){
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("D:\\"));
        fc.setTitle("Select file");
        copyList = fc.showOpenDialog(null);
    }

    @FXML
    protected void copy(ActionEvent event) throws IOException, InvalidFormatException {
        month = textField.getText();
        XSSFWorkbook workbook = new XSSFWorkbook(copyList);
        XSSFSheet sheet = workbook.getSheetAt(1);
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                CellType cellType = cell.getCellTypeEnum();

                switch (cellType) {
                    case _NONE:
                        System.out.print("");
                        System.out.print("\t");
                        break;
                    case STRING:
                        number =number + cell.getStringCellValue() + "\t";
                        break;
                }
            }
        }
        String correctNum = number.replace('\t', '_');
        Pattern pattern = Pattern.compile("\\d*[_]");
        Matcher matcher = pattern.matcher(correctNum);
        while (matcher.find()) {
            edrpou.add(correctNum.substring(matcher.start(), matcher.end()));
        }
        firstNum.setText(String.valueOf(edrpou.get(0)));
        lastNum.setText(String.valueOf(edrpou.get(edrpou.size()-1)));
        fileCounter = edrpou.size();
        filesQuan.setText(String.valueOf(fileCounter));

        try {
            File destFile = null;
            for (int i = 0; i <= edrpou.size() - 1; i++) {
                String individNum = edrpou.get(i);
                String rightName = "D://companies//1_pmg_"+individNum+month+".xls";
                destFile = new File(rightName);
                FileUtils.copyFile(ethalon, destFile);
                fileCounter ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}