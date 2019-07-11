package com.example.bootopen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RestController
@SpringBootApplication
@MapperScan("com.example.bootopen.mapper")
public class BootopenApplication {
	/**
	 * 用户首页，用于登录之后给用户看一些信息
	 * 信息可以从数据库中取，这边作为demo，暂时写死
	 * @return
	 */
	@RequestMapping("/home")
	public String hello(){
		return "hello spring boot!";
	}

	public static void main(String[] args) {
		SpringApplication.run(BootopenApplication.class, args);
	}

}
