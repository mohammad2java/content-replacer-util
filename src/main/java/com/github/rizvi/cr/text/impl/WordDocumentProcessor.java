package com.github.rizvi.cr.text.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.rizvi.cr.text.DocumentProcessor;
import com.github.rizvi.cr.util.CacheUtils;

@Component
public class WordDocumentProcessor implements DocumentProcessor {

	@Autowired
	private CacheUtils cacheUtils;
	
	Logger log  =  Logger.getLogger(WordDocumentProcessor.class.getName());

	@Override
	public void process(File file) {
		XWPFDocument doc = null;
		try {
			doc = new XWPFDocument(new FileInputStream(file));
		} catch (IOException e) {
			throw new RuntimeException("OpeningDocumentException+ " + e);
		}
		for (XWPFTable table :doc.getTables()) {
			for(XWPFTableRow row : table.getRows()) {
				for (XWPFTableCell cell :row.getTableCells()) {
					List<XWPFParagraph> paragraphs = cell.getParagraphs();
					extracted(paragraphs);	
				}
			}
		}
		String outFileName = String.format("%s/%s.docx",cacheUtils.getPropValue("out.file.path", System.getProperty("user.dir")),cacheUtils.getPropValue("new.file.name", "RFC"));
		try {
			doc.write(new FileOutputStream(outFileName));
		} catch (IOException e) {
			throw new RuntimeException("SavingDocumentException+ " + e);
		}
		log.info("file created-"+outFileName);
	}

	private void extracted(List<XWPFParagraph> paragraphs) {
		for (XWPFParagraph p : paragraphs) {
			List<XWPFRun> runs = p.getRuns();
			if (runs != null) {
				for (XWPFRun r : runs) {
					String text = r.getText(0);
					if (StringUtils.contains(text, "${") && StringUtils.contains(text, "}")) {
						String key = StringUtils.substringBetween(text, "${", "}");
						String val = cacheUtils.getPropValue(key, text);
						log.info(String.format("Key=%s,val=%s,text=%s", key,val,text));
						text = text.replace(String.format("${%s}", key), val);
						r.setText(text, 0);
					}
				}
			}
		}
	}
}
