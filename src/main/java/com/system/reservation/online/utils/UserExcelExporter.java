package com.system.reservation.online.utils;

import com.system.reservation.online.entity.Transaction;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class UserExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Transaction> transactions;

    public UserExcelExporter(List<Transaction> transactions) {
        this.transactions = transactions;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Transactions");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Transaction ID", style);
        createCell(row, 1, "Ordering Date", style);
        createCell(row, 2, "Pickup Date", style);
        createCell(row, 3, "Full Name", style);
        createCell(row, 4, "Email Address", style);
        createCell(row, 5, "Item Name", style);
        createCell(row, 6, "Item Size", style);
        createCell(row, 7, "Item Price", style);
        createCell(row, 8, "Status", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Transaction transaction : transactions) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, transaction.getId().toString(), style);
            createCell(row, columnCount++, transaction.getOrderingDate().toString(), style);
            createCell(row, columnCount++, transaction.getReceivedDate().toString(), style);
            createCell(row, columnCount++, transaction.getUser().getName().toString(), style);
            createCell(row, columnCount++, transaction.getUser().getEmail().toString(), style);
            createCell(row, columnCount++, transaction.getItem().getName().toString(), style);
            createCell(row, columnCount++, transaction.getItem().getSize().toString(), style);
            createCell(row, columnCount++, transaction.getItem().getPrice().toString(), style);
            createCell(row, columnCount++, transaction.getRemarks().toString(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}