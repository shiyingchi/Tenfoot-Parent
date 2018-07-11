package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SpringBootSmallApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(SpringBootSmallApplication.class, args);
		System.out.println("ヾ(◍°∇°◍)ﾉﾞ    Tenfoot-Small启动成功      ヾ(◍°∇°◍)ﾉ");
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

		return builder.sources(SpringBootSmallApplication.class);


	}
}
