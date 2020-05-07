package com.yc.springcloud.ribbon.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/client")
public class ConsumerController {
	@Autowired
	private  RestTemplate restTemplate;

	/**
	 * 面向服务器调用
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	@RequestMapping("/hello")
	public String helloConsumer() throws ExecutionException, InterruptedException {
		return restTemplate.getForEntity("http://client-service/hello",String.class).getBody();
	}
}
