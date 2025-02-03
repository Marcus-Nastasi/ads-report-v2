package com.ads.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * The Google Ads Report API.
 *
 * <p>This version allows any Google user to log in with their Google account, and use the software.<p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.2
 * @since 2025
 * */
@SpringBootApplication
public class ReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}
}
