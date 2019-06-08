package com.github.rizvi.cr.text;

import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.github.rizvi.cr.util.CacheUtils;

@Component
public class TextReplacer {
	
	
	@Autowired
	private ApplicationContext context;
	@Autowired
	private CacheUtils cacheUtils;
	private DocumentProcessor processor;

	Logger log = Logger.getLogger(TextReplacer.class.getName());
	
	public void replaceKeysToValues(Collection<File> findFiles) {

		for (File file :findFiles) {
			String beanName = findBeanByFileType(file);
			if (StringUtils.isNotBlank(beanName)) {
				processor = (DocumentProcessor) context.getBean(beanName); 
				processor.process(file);	
			}
			else {
				log.warning("DocumentProcessorNotFound");
			}

		}
		
		
	}

	private String findBeanByFileType(File file) {
		String extens = FilenameUtils.getExtension(String.valueOf(file));
		if (StringUtils.contains(cacheUtils.getPropValue("skip.extns", "doc"), extens)) {
			return "";
		}
		return cacheUtils.getPropValue(extens, "wordDocumentProcessor");
	}

}
