package com.yc.springcloud.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;

@SpringBootApplication
@EnableDiscoveryClient // 允许发现客户端
public class RibbonServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(RibbonServerApplication.class, args);
	}

	/**
	 * IRule是根据特定算法从服务列表中选取一个要访问的服务。SpringCloud自带7中算法
	 * 1.RoundRobinRule--轮询
	 * 2.RandomRule--随机
	 * 3.AvailabilityFilteringRule --会先过滤掉由于多次访问故障处于断路器跳闸状态的服务，还有并发的连接数量超过阈值的服务，然后对于剩余的服务列表按照轮询的策略进行访问
	 * 4.WeightedResponseTimeRule--根据平均响应时间计算所有服务的权重，响应时间越快服务权重越大被选中的概率越大。刚启动时如果同统计信息不足，则使用轮询的策略，等统计信息足够会切换到自身规则
	 * 5.RetryRule-- 先按照轮询的策略获取服务，如果获取服务失败则在指定的时间内会进行重试，获取可用的服务
	 * 6.BestAvailableRule --会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量小的服务
	 * 7.ZoneAvoidanceRule -- 默认规则，复合判断Server所在区域的性能和Server的可用行选择服务器。
	 * @return
	 */
	@Bean
	public IRule ribbonRule(){
		return new RoundRobinRule();
	}

	/**
     * Spring Cloud的commons模块提供了一个@LoadBalanced注解，方便我们对RestTemplate添加一个LoadBalancerClient，
     * 以实现客户端负载均衡。通过源码可以发现这是一个标记注解
     * @return
     */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
