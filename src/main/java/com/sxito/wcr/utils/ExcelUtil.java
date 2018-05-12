package com.sxito.wcr.utils;


import org.apache.poi.hssf.usermodel.*;

import java.io.IOException;

public class ExcelUtil {
    /***
     * 创建表头
     * @param workbook
     * @param sheet
     */
    private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet)
    {
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2, 12*256);
        sheet.setColumnWidth(3, 17*256);

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
//        style.setAlignment(HSSFCellStyle.ALI);
        style.setFont(font);

        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("金额");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("描述");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("日期");
        cell.setCellStyle(style);
    }
    public static void main(String[] args){

    }
}
