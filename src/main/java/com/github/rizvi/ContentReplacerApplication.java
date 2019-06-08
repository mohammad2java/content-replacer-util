package com.github.rizvi;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.rizvi.cr.CmdInteracter;
import com.github.rizvi.cr.text.TextReplacer;
import com.github.rizvi.cr.text.TextScanner;
import com.github.rizvi.cr.util.GeneralUtils;

@SpringBootApplication
public class ContentReplacerApplication  implements CommandLineRunner{	
	
	private Logger log = Logger.getLogger(ContentReplacerApplication.class.getName());
	
	@Autowired
	private GeneralUtils generalUtils;
	@Autowired
	private TextScanner textScanner;
	@Autowired
	private CmdInteracter cmdInteracter;
	@Autowired
	private TextReplacer textReplacer;
	
	public static void main(String[] args) {
		SpringApplication.run(ContentReplacerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("######### content-replacer started ###############");
		Collection<File> findFiles = generalUtils.findFiles();
		 logSampleFile(findFiles);
		 Set<String> keys = textScanner.findReplacerKeys(findFiles);
		 log.info(keys.toString());
		 cmdInteracter.findKeysValues(keys);
		 textReplacer.replaceKeysToValues(findFiles);
		log.info("######### content-replacer finish #############");
		
	}

	private void logSampleFile(Collection<File> findFiles) {
		log.info("found sample files:"+String.valueOf(findFiles));
	}

}
