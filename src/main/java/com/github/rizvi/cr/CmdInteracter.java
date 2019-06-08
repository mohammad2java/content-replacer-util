package com.github.rizvi.cr;

import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.rizvi.cr.util.CacheUtils;

@Component
public class CmdInteracter {
	
	@Autowired
	private CacheUtils cacheUtils;
	
	Logger log = Logger.getLogger(CmdInteracter.class.getName());

	public void findKeysValues(Set<String> keys) {
		Scanner scan = new Scanner(System.in);
		log.info("Enter filname-");
		String val = scan.nextLine();
		cacheUtils.put("new.file.name", val);
		for (String key:keys) {
			log.info("Enter value for-"+key);
			String value = scan.nextLine();
			cacheUtils.put(key, value);
		}
		scan.close();
	}

}
