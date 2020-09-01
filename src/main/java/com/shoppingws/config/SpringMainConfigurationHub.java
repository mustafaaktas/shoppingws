package com.shoppingws.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@Import({
	//SpringWebMvcConfiguration.class,
	SpringDataSourceConfiguration.class,
	//SpringEhcacheConfiguration.class,
	SpringMultipartResolverConfiguration.class,
	SpringSecurityConfiguration.class
})
@EnableWebSecurity
@EnableCaching(proxyTargetClass = true)
@EnableAspectJAutoProxy
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration
public class SpringMainConfigurationHub {}
