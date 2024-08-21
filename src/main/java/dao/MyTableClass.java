package dao;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class MyTableClass {
	PDDocument document;
	PDPageContentStream contentStream;
	private int[] colWidths;
	private int cellHeight;
	private int yPosition;
	private int xPosition;
	private int colPositon;
	private int xInitialPosition;
	private float fontSize;
	private PDFont font;
	private Color fontColor;
	public MyTableClass(PDDocument document, PDPageContentStream contentStream) {
		this.document = document;
		this.contentStream = contentStream;
	}
	public void setTable(int[] colWidths, int cellHeight, int yPosition, int xPosition) {
		this.colWidths = colWidths;
		this.cellHeight = cellHeight;
		this.yPosition = yPosition;
		this.xPosition = xPosition;
		xInitialPosition = xPosition;
	}
	public void setTableFont(PDFont font, float fontSize, Color fontColor) {
		this.font = font;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
	}
	
	public void addCell(String text, Color fillColor) throws IOException {
		contentStream.setStrokingColor(1f);
		if (fillColor != null) {
			contentStream.setNonStrokingColor(fillColor);
			contentStream.setNonStrokingColor(Color.WHITE);
			contentStream.addRect(xPosition, yPosition, colWidths[colPositon], cellHeight);
			contentStream.fillAndStroke();
			contentStream.beginText();
			contentStream.setNonStrokingColor(fillColor);
			
			if(colPositon == 5) {
				float fontWidth = font.getStringWidth(text)/1000 * fontSize;
				contentStream.newLineAtOffset(xPosition + colWidths[colPositon] - fontWidth, yPosition + 10);
			}else {
				contentStream.newLineAtOffset(xPosition +5 , yPosition + 10);
			}
			
			contentStream.showText(text);
			contentStream.endText();
			 xPosition = xPosition + colWidths[colPositon];
			 colPositon++;
			 
			 if(colPositon == colWidths.length) {
				 colPositon = 0;
				 xPosition = xInitialPosition;
				 yPosition -= cellHeight;
				 
			 }
		}else {
			contentStream.setNonStrokingColor(Color.white);
		
		}
	}
}
