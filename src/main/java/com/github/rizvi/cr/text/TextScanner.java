package com.github.rizvi.cr.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

@Component
public class TextScanner {

	public Set<String> findReplacerKeys(Collection<File> findFiles) {
		Set<String> ret = new HashSet<>();
		
		for (File file :findFiles) {
			findPlaceholderKeys(file,ret);
		}
		
		return ret;
	}

	private void findPlaceholderKeys(File file, Set<String> ret) {
		String fileToString = null;
		try {
			XWPFDocument doc = new XWPFDocument(new FileInputStream(file));
			XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(doc);
			fileToString = xwpfWordExtractor.getText();
			xwpfWordExtractor.close();
		} catch (IOException e) {
			throw new RuntimeException("ReadFileToStringException-"+e);
		}
		 if (StringUtils.isNotBlank(fileToString)) {
			 extractKeysFromString(ret,fileToString);	 
		 }
	}

	private void extractKeysFromString(Set<String> ret, String fileToString) {
		String[] keys = StringUtils.substringsBetween(fileToString, "${", "}");
		if (keys != null)
		ret.addAll(Arrays.asList(keys));
	}

}
