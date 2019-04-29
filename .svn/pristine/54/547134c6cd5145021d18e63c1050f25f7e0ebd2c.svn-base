package com.huachu.common.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.huachu.common.util.DateUtils;
import com.huachu.common.util.FileUtils;
import com.huachu.common.util.ReflectionUtils;
import com.huachu.common.util.StringUtils;

public class ExcelUtils {

	public static Workbook export(List<? extends Object> list, String[] headers, String splitStr, String[] columns) {
		// 创建excel文档,内存中保留 100 条数据，以免内存溢出
		SXSSFWorkbook wb = new SXSSFWorkbook(100);

		Font font = wb.createFont();
		font.setFontName("宋体");
		// 设置字体大小
		font.setFontHeightInPoints((short) 10);
		// 加粗
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		// 表头样式
		CellStyle cellStylehead = wb.createCellStyle();
		// 设置字体样式
		cellStylehead.setFont(font);
		// 水平对齐
		cellStylehead.setAlignment(CellStyle.ALIGN_CENTER);
		// 垂直对齐
		cellStylehead.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 自动换行
		cellStylehead.setWrapText(true);
		// 设置边框
		cellStylehead.setBorderTop(CellStyle.BORDER_THIN);
		cellStylehead.setBorderRight(CellStyle.BORDER_THIN);
		cellStylehead.setBorderBottom(CellStyle.BORDER_THIN);
		cellStylehead.setBorderLeft(CellStyle.BORDER_THIN);
		// 表体样式
		font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);
		CellStyle cellStyleBody = wb.createCellStyle();
		cellStyleBody.setFont(font);
		cellStyleBody.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyleBody.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// cellStyleBody.setWrapText(true);
		// 设置边框
		cellStyleBody.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleBody.setBorderLeft(CellStyle.BORDER_THIN);
		// 创建一个sheet
		Sheet sheet = wb.createSheet("sheet1");
		// 设置默认列宽
		sheet.setDefaultColumnWidth(15);
		// 写表头
		createHeader(wb, sheet, cellStylehead, headers, splitStr);
		// 写表体
		int beginRowNumber = headers.length;
		if (list != null && list.size() > 0) {
			int indexOf = 0;

			// 填充数据
			int rowCounter = 0;
			for (Object obj : list) {
				indexOf = 0;
				Row row = sheet.createRow(rowCounter++ + beginRowNumber);
				for (String columnName : columns) {
					//sheet.autoSizeColumn(indexOf);
					String[] columnNames = columnName.split("\\.");
					Object property = null;
					if (columnNames.length == 1) {
						property = ReflectionUtils.invokeGetterMethod(obj, columnName);
						Cell cell = row.createCell(indexOf++);
						cell.setCellValue(ObjectUtils.toString(property));
						cell.setCellStyle(cellStyleBody);
					} else {
						property = ReflectionUtils.getFieldValue(obj, columnNames[0]);
						if (property == null) {
							Cell cell = row.createCell(indexOf++);
							cell.setCellValue(StringUtils.EMPTY_STRING);
							cell.setCellStyle(cellStyleBody);
							continue;
						}

						for (int i = 1; i < columnNames.length; i++) {
							property = ReflectionUtils.getFieldValue(property, columnNames[i]);
						}

						Cell cell = row.createCell(indexOf++);
						cell.setCellValue(ObjectUtils.toString(property));
						cell.setCellStyle(cellStyleBody);
					}
				}
			}
		}

		return wb;
	}
	
	/**
	 * 合并单元格
	 */
	public static void addMergedRegion(Sheet sheet, int cellLine, int startRow, int endRow, SXSSFWorkbook workBook) {
		// 样式对象
		CellStyle style = workBook.createCellStyle();
		// 字体
		Font font = workBook.createFont();
		font.setFontName("宋体");
		// 设置字体大小
		font.setFontHeightInPoints((short) 10);
		// 垂直
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 水平
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);
		// 获取第一行的数据,以便后面进行比较
		String s_will = sheet.getRow(startRow).getCell(cellLine).getStringCellValue();

		int count = 0;
		boolean flag = false;
		for (int i = startRow + 1; i <= endRow; i++) {
			String s_current = sheet.getRow(i).getCell(0).getStringCellValue();
			if (s_will.equals(s_current)) {
				s_will = s_current;
				if (flag) {
					sheet.addMergedRegion(new CellRangeAddress(startRow - count, startRow, cellLine, cellLine));
					Row row = sheet.getRow(startRow - count);
					String cellValueTemp = sheet.getRow(startRow - count).getCell(0).getStringCellValue();
					Cell cell = row.createCell(0);
					// 跨单元格显示的数据
					cell.setCellValue(cellValueTemp);
					// 样式
					cell.setCellStyle(style);
					count = 0;
					flag = false;
				}
				startRow = i;
				count++;
			} else {
				flag = true;
				s_will = s_current;
			}
			// 由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
			if (i == endRow && count > 0) {
				sheet.addMergedRegion(new CellRangeAddress(endRow - count, endRow, cellLine, cellLine));
				String cellValueTemp = sheet.getRow(startRow - count).getCell(0).getStringCellValue();
				Row row = sheet.getRow(startRow - count);
				Cell cell = row.createCell(0);
				// 跨单元格显示的数据
				cell.setCellValue(cellValueTemp);
				cell.setCellStyle(style);
			}
		}
	}

	/**
	 * 创建excel表头
	 */
	public static void createHeader(SXSSFWorkbook wb, Sheet sheet, CellStyle cellStylehead, String[] headers,
			String splitStr) {
		// 遍历创建单元格
		for (int i = 0; i < headers.length; i++) {
			Row row = sheet.createRow(i);
			String[] header = headers[i].split(splitStr);
			for (int r = 0; r < header.length; r++) {
				Cell cell = row.createCell(r);
				cell.setCellValue(header[r]);
				cell.setCellStyle(cellStylehead);
			}
		}
		// 遍历合并单元格，如果是单表头则跳过
		if (headers.length > 1) {
			int[][][] mergeDatas = parseHeader(headers, splitStr);
			for (int i = 0; i < mergeDatas.length; i++) {
				int[][] mergeData = mergeDatas[i];
				for (int j = 0; j < mergeData.length; j++) {
					int[] merges = mergeData[j];
					int mergesR = merges[1] - merges[0];
					int mergesC = merges[3] - merges[2];
					if (mergesR != 0 || mergesC != 0) {
						// 合并单元格
						sheet.addMergedRegion(new CellRangeAddress(merges[0], merges[1], merges[2], merges[3]));
						// 合并单元格后重新设置单元格的样式
						setMergedStyle(merges[0], merges[1], merges[2], merges[3], sheet, cellStylehead);
					}
				}
			}
		}
	}

	/**
	 * 设置合并单元格的样式
	 */
	public static void setMergedStyle(int i, int j, int k, int l, Sheet sheet, CellStyle cellStylehead) {
		for (int mm = i; mm <= j; mm++) {
			Row row = sheet.getRow(mm);
			if (row == null) {
				row = sheet.createRow(mm);
			}
			for (int nn = k; nn <= l; nn++) {
				Cell cell = row.getCell(nn);
				if (cell == null) {
					cell = row.createCell(nn);
				}
				cell.setCellStyle(cellStylehead);
			}
		}
	}

	public static int[][][] parseHeader(String[] headers, String splitStr) {
		// 依据表头建立一个二维数组
		String[][] doubleAry = new String[headers.length][];
		// 依据表头建立一个三维数组，用来保存合并单元格所需要的数据，单元格所需要的数据有四个，依次是开始合并的行，结束合并的行，开始合并的列，结束合并的列
		// sheet.addMergedRegion(new
		// CellRangeAddress(merges[0],merges[1],merges[2],merges[3]));
		int[][][] rcs = new int[headers.length][][];
		// 遍历赋值
		for (int i = 0; i < headers.length; i++) {
			String[] header = headers[i].split(splitStr);
			doubleAry[i] = header;
			rcs[i] = new int[header.length][4];
		}
		// 遍历二维数组
		for (int i = 0; i < doubleAry.length; i++) {
			String[] sub = doubleAry[i];
			for (int j = 0; j < sub.length; j++) {
				int sum = 0;// 计算行标识
				// 计算i,j需要合并的行数
				// 如果单元格不为null
				if (!sub[j].equals("null")) {
					for (int m = i; m < doubleAry.length; m++) {
						String rs = doubleAry[m][j];
						if (rs.equals("null")) {
							// 把null重赋值为null2，防止在计算列时交错了
							doubleAry[m][j] = "null2";
							sum++;// 个数加1
						}
					}
				}
				// 赋合并的开始行，当前行
				rcs[i][j][0] = i;
				// 赋合并的结束行，当前行加上它下面为null的行数
				rcs[i][j][1] = i + sum;
				// 计算列标识
				int sum2 = 0;
				// 计算i,j需要合并的列数
				if (!sub[j].equals("null")) {
					for (int m = j + 1; m < doubleAry[i].length; m++) {
						String rs = doubleAry[i][m];
						if (rs.equals("null")) {
							sum2++;
						} else {
							break;// 一定要break，不然会算错
						}
					}
				}
				// 赋合并的开始列，当前列
				rcs[i][j][2] = j;
				// 赋合并的结束列，当前列加上它右边为null的列数
				rcs[i][j][3] = j + sum2;
			}
		}
		return rcs;
	}

	public static void export(HttpServletResponse response, String prefixName, String[] headers, List<? extends Object> list,
			String[] columns) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		String fileName = prefixName + DateUtils.YYYYMMDDHHMMSS.get().format(new Date()) + ".xlsx";
		try {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try {
			OutputStream out = response.getOutputStream();

			Workbook wb = export(list, headers, ",", columns);

			wb.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 创建Excel文件
	 * @param filePath
	 * @param fileName
	 * @param headers
	 * @param list
	 * @param columns
	 */
	public static String createExcel(String filePath, String fileName, String[] headers, List<? extends Object> list, String[] columns) {
		File file = new File(filePath + fileName);
		FileOutputStream fOut = null;
		try {
			file = FileUtils.createFile(file);
			fOut = new FileOutputStream(file);
			Workbook wb = export(list, headers, ",", columns);
			// 将创建的内容写到指定的Excel文件中
			wb.write(fOut);
			fOut.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (fOut != null) {
				try {
					fOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return fileName;
	}
}