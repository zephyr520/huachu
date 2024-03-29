package com.huachu.common.excel;

import com.huachu.common.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * @author Administrator
 * @DATE 2018/8/17
 */
public class ParserExcel {

    private static final Logger logger = LoggerFactory.getLogger(ParserExcel.class);

    private Workbook wb = null;

    private Sheet sheet = null;
    private Row row = null;
    /**
     * 第sheetnum个工作表
      */
    private int sheetNum = 0;

    private int rowNum = 0;
    private InputStream fis = null;
    private File file = null;

    public ParserExcel() {
    }

    public ParserExcel(File file) {
        this.file = file;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public void setSheetNum(int sheetNum) {
        this.sheetNum = sheetNum;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 读取excel文件获得Workbook对象
     */
    public void open(String fileName, InputStream fis) throws IOException {
        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (".xls".equals(suffix)) {
            wb = new HSSFWorkbook(new POIFSFileSystem(fis));
        } else if (".xlsx".equals(suffix)) {
            wb = new XSSFWorkbook(fis);
        }
        fis.close();
    }

    public void open() throws IOException {
        fis = new FileInputStream(file);
        String suffix = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
        if (".xls".equals(suffix)) {
            wb = new HSSFWorkbook(new POIFSFileSystem(fis));
        } else if (".xlsx".equals(suffix)) {
            wb = new XSSFWorkbook(fis);
        }
        fis.close();
    }

    /**
     * 返回sheet表数目
     *
     * @return int
     */
    public int getSheetCount() {
        int sheetCount = -1;
        sheetCount = wb.getNumberOfSheets();
        return sheetCount;
    }

    /**
     * sheetNum下的记录行数
     *
     * @return int
     */
    public int getRowCount() {
        if (wb == null)
            System.out.println("=============>WorkBook为空");
        Sheet sheet = wb.getSheetAt(this.sheetNum);
        int rowCount = -1;
        rowCount = sheet.getPhysicalNumberOfRows();
        return rowCount;
    }

    /**
     * 读取指定sheetNum的rowCount
     *
     * @param sheetNum
     * @return int
     */
    public int getRowCount(int sheetNum) {
        Sheet sheet = wb.getSheetAt(sheetNum);
        int rowCount = -1;
        rowCount = sheet.getLastRowNum();
        return rowCount;
    }

    /***
     * 获取当前行是否为空
     * @param lineNum
     * @return
     */
    public Row getRow(int lineNum) {
        Row row = wb.getSheetAt(this.sheetNum).getRow(lineNum);
        if (row != null) {
            int nullCount = 0;
            int cellCount = row.getLastCellNum();
            for (int i = 0; i <= cellCount; i++) {
                String strExcelLine = readStringExcelCell(lineNum, i);
                if (StringUtils.isEmpty(strExcelLine)) {
                    nullCount++;
                }
            }
            if (nullCount == cellCount + 1) {
                return null;
            }
            return row;
        } else {
            return null;
        }
    }

    /**
     * 得到指定行的内容
     *
     * @param lineNum
     * @return String[]
     */
    public String[] readExcelLine(int lineNum) {
        return readExcelLine(this.sheetNum, lineNum);
    }

    /**
     * 指定工作表和行数的内容
     *
     * @param sheetNum
     * @param lineNum
     * @return String[]
     */
    public String[] readExcelLine(int sheetNum, int lineNum) {
        if (sheetNum < 0 || lineNum < 0)
            return null;
        String[] strExcelLine = null;
        try {
            sheet = wb.getSheetAt(sheetNum);
            row = sheet.getRow(lineNum);

            int cellCount = row.getLastCellNum();
            strExcelLine = new String[cellCount + 1];
            for (int i = 0; i <= cellCount; i++) {
                strExcelLine[i] = readStringExcelCell(lineNum, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strExcelLine;
    }

    /**
     * 读取指定列的内容
     *
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int cellNum) {
        return readStringExcelCell(this.rowNum, cellNum);
    }

    /**
     * 指定行和列编号的内容
     *
     * @param rowNum
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int rowNum, int cellNum) {
        return readStringExcelCell(this.sheetNum, rowNum, cellNum);
    }

    /**
     * 指定工作表、行、列下的内容
     * @param sheetNum
     * @param rowNum
     * @param cellNum
     * @return String
     */
    public String readStringExcelCell(int sheetNum, int rowNum, int cellNum) {
        if (sheetNum < 0 || rowNum < 0)
            return "";
        String strExcelCell = "";
        try {
            sheet = wb.getSheetAt(sheetNum);
            row = sheet.getRow(rowNum);

            if (row.getCell(cellNum) != null) {

                // judge

                switch (row.getCell(cellNum).getCellType()) {
                    case Cell.CELL_TYPE_FORMULA:
                        strExcelCell = "FORMULA ";
                        break;
                    case Cell.CELL_TYPE_NUMERIC: {
                        DecimalFormat df = new DecimalFormat("0");
                        if (HSSFDateUtil.isCellDateFormatted(row.getCell(cellNum))) {
                            strExcelCell = HSSFDateUtil.getJavaDate(row.getCell(cellNum).getNumericCellValue()).toString();
                        } else {
                            strExcelCell = String.valueOf(df.format(row.getCell(cellNum).getNumericCellValue()));
                        }
                    }
                    break;
                    case Cell.CELL_TYPE_STRING:
                        strExcelCell = row.getCell(cellNum).getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_BLANK:
                        strExcelCell = "";
                        break;
                    default:
                        strExcelCell = "";
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("cellNum = " + cellNum + ", cell = " + row.getCell(cellNum));
            e.printStackTrace();
        }
        return strExcelCell;
    }
    // 主函数用于测试,一般用这里面的函数来进行操作

    public static void main(String args[]) {
        // 标示文件路径
        String filePath = "C:\\Users\\Administrator\\Desktop\\客户信息导入模板 (1).xlsx";
        File file = new File(filePath);
        ParserExcel readExcel = new ParserExcel(file);
        // 打开文件
        try {
            readExcel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 设置读取索引为0的工作表
        readExcel.setSheetNum(0);
        // 总行数
        int count = readExcel.getRowCount();
        Row row = readExcel.getRow(1);
        System.out.println("row.getPhysicalNumberOfCells() = " + row.getPhysicalNumberOfCells()
                + "row.getLastCellNum() = " + row.getLastCellNum());
        // 循环读取Excel文件中的内容
        System.out.println("总行数： " + count);
        for (int i = 1; i < count; i++) {
            String[] rows = readExcel.readExcelLine(i);
            for (int j = 0; j < rows.length; j++) {
                System.out.print(rows[j] + " ");
            }

            System.out.print("\n");
        }
    }
}
