package dao;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class MyTextClass {
	PDDocument document; 
	PDPageContentStream contentStream;
	public MyTextClass(PDDocument document, PDPageContentStream contentStream) {
		this.document = document;
		this.contentStream = contentStream;
	}
	public void addSingleLineText(String text, int xPosition,int yPosition, PDFont font, float fontSize, Color color) throws IOException {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.setNonStrokingColor(color);
		contentStream.newLineAtOffset(xPosition, yPosition);
		contentStream.showText(text);
		contentStream.endText();
		contentStream.moveTo(0,0);
	}
	void addMultiLineText(String[] textArray,float leading,int xPosition,int yPosition,PDFont font, float fontSize, Color color) throws IOException {
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.setNonStrokingColor(color);
		contentStream.newLineAtOffset(xPosition,yPosition );
		for(String text : textArray) {
			contentStream.showText(text);
			contentStream.newLine();
		}
		contentStream.endText();
		contentStream.moveTo(0,0);
	}
	public float getTextWidth(String text,PDFont font, float fontSize) throws IOException {
		return font.getStringWidth(text)/1000* fontSize;
	}
}
