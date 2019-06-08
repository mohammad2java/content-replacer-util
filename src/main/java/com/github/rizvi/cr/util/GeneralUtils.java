package com.github.rizvi.cr.util;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class GeneralUtils {

	@Autowired
	private CacheUtils cacheUtils;

	public String findDocDirPath() {
		String ret = System.getProperty("user.home");
		ret = cacheUtils.getPropValue("doc.path", ret);
		return ret;
	}

	public Collection<File> findFiles() {
		Collection<File> ret = null;
		File dir = new File(findDocDirPath());
		String tmpExtns = cacheUtils.getPropValue("doc.extns", "doc,docx");
		String[] extensions = StringUtils.split(tmpExtns, ",");
		ret = FileUtils.listFiles(dir, extensions, false);
		return ret;
	}
}
