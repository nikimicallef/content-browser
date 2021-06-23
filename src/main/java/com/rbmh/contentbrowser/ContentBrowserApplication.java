package com.rbmh.contentbrowser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.cloudyrock.spring.v5.EnableMongock;


@SpringBootApplication
@EnableMongock
public class ContentBrowserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentBrowserApplication.class, args);
	}

}
