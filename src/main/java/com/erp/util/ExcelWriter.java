package com.erp.util;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ResourceUtils;

import com.erp.dto.sale.SaleHeaderList;

public class ExcelWriter {
	static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
	public static void exportSaleList(HttpServletResponse response, List<SaleHeaderList> saleHeaderList, BigDecimal totalCredit,
			BigDecimal totalDebit, BigDecimal total) {
		Date date = new Date();
		String fileName = "SaleHeaderList_"+df.format(date)+".xlsx";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
		response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
		OutputStream outputStream = null;
		try {
			File file = ResourceUtils.getFile("classpath:ExcelTemplate/SaleHeaderList.xlsx");
			OPCPackage pkg = OPCPackage.open(file);
			outputStream = response.getOutputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(pkg);
			
			XSSFSheet  sheet = workbook.getSheetAt(0);		
			Integer rowIndex = 2;
			
			XSSFCellStyle currencyCellStyle = getCellStyle(workbook, "currency",null);
			XSSFCellStyle dateCellStype = getCellStyle(workbook, "date",null);
			XSSFCellStyle currencyBoldCellStype = getCellStyle(workbook, "currency", "bold");
			XSSFCellStyle boldCellStype = getCellStyle(workbook, null,"bold");
			
			for (SaleHeaderList sale : saleHeaderList) {
				Row row = sheet.createRow(rowIndex++);
				createCellWithString(row.createCell(0), sale.getId().toString(), null);
				createCellWithString(row.createCell(1), sale.getCustomerName(), null);
				createCellWithDate(row.createCell(2),sale.getOrderDate(),dateCellStype);
				
				createCellWithCurrency(row.createCell(3),sale.getDebit(), currencyCellStyle);
				createCellWithCurrency(row.createCell(4),sale.getCredit(), currencyCellStyle);
				createCellWithCurrency(row.createCell(5),sale.getTotalAmount(), currencyCellStyle);
			}
			Row row = sheet.createRow(rowIndex);

			createCellWithString(row.createCell(2),"Total", boldCellStype);
			createCellWithCurrency(row.createCell(3),totalDebit, currencyBoldCellStype);
			createCellWithCurrency(row.createCell(4),totalCredit, currencyBoldCellStype);
			createCellWithCurrency(row.createCell(5),total, currencyBoldCellStype);
		
			workbook.write(outputStream);
			response.flushBuffer();

			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (outputStream != null) {
				try {
					response.getOutputStream().flush();
					response.getOutputStream().close();
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	
		}
	}

	private static void createCellWithDate(Cell cell,Date date, CellStyle cellStyle) {
		cell.setCellValue(date);
		if(cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
	}
	
	private static void createCellWithCurrency(Cell cell,BigDecimal value, CellStyle cellStyle) {
		cell.setCellValue(value.doubleValue());
		if(cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
	}
	
	private static void createCellWithString(Cell cell,String value, CellStyle cellStyle) {
		cell.setCellValue(value.toString());
		if(cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
	}
	
	private static XSSFCellStyle getCellStyle(XSSFWorkbook workbook, String type , String fontWeight) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFDataFormat df = workbook.createDataFormat();

		// Type
		if(type != null) {
			if(type.equalsIgnoreCase("date")) {
				cellStyle.setDataFormat(df.getFormat("mm/dd/yyyy"));
			} else if(type.equalsIgnoreCase("currency")) {
				cellStyle.setDataFormat(df.getFormat("#,##0.00"));		
			}	
		}
		
		// font weight
		if(fontWeight != null && fontWeight.equalsIgnoreCase("bold")) {
			 XSSFFont font= workbook.createFont();
			 font.setBold(true);
			cellStyle.setFont(font);
		}
		return cellStyle;
	}

}
