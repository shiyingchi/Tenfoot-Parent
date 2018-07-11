package com.project.common.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.project.common.shiro.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;


@Configuration
//@EnableCaching
public class ShiroConfig {
//	@Bean
//	public EhCacheManager getEhCacheManager() {
//		EhCacheManager em = new EhCacheManager();
//		em.setCacheManagerConfigFile("classpath:config/ehcache.xml");
//		return em;
//	}
//
//	/**
//	 * FilterRegistrationBean
//	 * @return
//	 */
//	@Bean
//	public FilterRegistrationBean filterRegistrationBean() {
//		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilterFactoryBean"));
//		filterRegistration.setEnabled(true);
//		filterRegistration.addUrlPatterns("/*");
//		filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
//		return filterRegistration;
//	}
//
//	/**
//	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
//	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
//	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
//	 *
//	 Filter Chain定义说明
//	 1、一个URL可以配置多个Filter，使用逗号分隔
//	 2、当设置多个过滤器时，全部验证通过，才视为通过
//	 3、部分过滤器可指定参数，如perms，roles
//	 *
//	 */
//	@Bean
//	ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
//		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//		shiroFilterFactoryBean.setSecurityManager(securityManager);
//		shiroFilterFactoryBean.setLoginUrl("/login");
//		shiroFilterFactoryBean.setSuccessUrl("/index");
//		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//		filterChainDefinitionMap.put("/css/**", "anon");
//		filterChainDefinitionMap.put("/js/**", "anon");
//		filterChainDefinitionMap.put("/fonts/**", "anon");
//		filterChainDefinitionMap.put("/img/**", "anon");
//		filterChainDefinitionMap.put("/docs/**", "anon");
//		filterChainDefinitionMap.put("/druid/**", "anon");
//		filterChainDefinitionMap.put("/upload/**", "anon");
//		filterChainDefinitionMap.put("/files/**", "anon");
//		filterChainDefinitionMap.put("/logout", "logout");
//		filterChainDefinitionMap.put("/", "anon");
//		filterChainDefinitionMap.put("/imageCode", "anon");
//		//申请退款
//		filterChainDefinitionMap.put("/order/orderRefundCallback", "anon");
//
//		filterChainDefinitionMap.put("/test", "anon");
//		filterChainDefinitionMap.put("/test/pay", "anon");
//		filterChainDefinitionMap.put("/test/getUserInfo", "anon");
//		filterChainDefinitionMap.put("/test/notify", "anon");
//
//		filterChainDefinitionMap.put("/**", "authc");
//
//		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//		return shiroFilterFactoryBean;
//	}
//
//	@Bean
//	DefaultWebSecurityManager securityManager(UserRealm userRealm) {
//		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
////		manager.setRealm(userRealm);
////		manager.setCacheManager(redisCacheManager());
//		manager.setSessionManager(sessionManager());
//		return manager;
//	}
//
//	@Bean
//	public DefaultWebSessionManager sessionManager() {
//		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
//		listeners.add(new BDSessionListener());
//		sessionManager.setSessionListeners(listeners);
//		sessionManager.setSessionDAO(sessionDAO());
//		sessionManager.setCacheManager(redisCacheManager());
//		sessionManager.setGlobalSessionTimeout(1800000);
//		sessionManager.setDeleteInvalidSessions(true);
//		sessionManager.setSessionValidationSchedulerEnabled(true);
//		sessionManager.setDeleteInvalidSessions(true);
//		return sessionManager;
//	}
//
//	@Bean
//	@DependsOn(value={"lifecycleBeanPostProcessor", "shrioRedisCacheManager"})
//	UserRealm userRealm() {
//		UserRealm userRealm = new UserRealm();
//		userRealm.setCacheManager(redisCacheManager());
//		userRealm.setCachingEnabled(true);
//		userRealm.setAuthenticationCachingEnabled(true);
//		userRealm.setAuthorizationCachingEnabled(true);
//		return userRealm;
//	}
//
//	@Bean(name="shrioRedisCacheManager")
//	@DependsOn(value="redisTemplate")
//	public ShrioRedisCacheManager redisCacheManager() {
//		ShrioRedisCacheManager cacheManager = new ShrioRedisCacheManager(redisTemplate());
//		return cacheManager;
//	}
//
//	@Bean(name="redisTemplate")
//	public RedisTemplate<byte[], Object> redisTemplate() {
//		RedisTemplate<byte[], Object> template = new RedisTemplate<>();
//		template.setConnectionFactory(connectionFactory());
//
////		template.setKeySerializer(new StringRedisSerializer(Charset.defaultCharset()));
////		template.setHashKeySerializer(new StringRedisSerializer(Charset.defaultCharset()));
////		template.setValueSerializer(new JdkSerializationRedisSerializer());
////		template.setHashValueSerializer(new JdkSerializationRedisSerializer());
//		return template;
//	}
//
//	@Bean
//	@ConfigurationProperties(prefix="spring.redis")
//	public JedisConnectionFactory connectionFactory(){
//		JedisConnectionFactory conn = new JedisConnectionFactory();
//		return conn;
//	}
//
//	@Bean("lifecycleBeanPostProcessor")
//	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//		return new LifecycleBeanPostProcessor();
//	}
//
//
//	@Bean
//	SessionDAO sessionDAO() {
//		MemorySessionDAO sessionDAO = new MemorySessionDAO();
//		return sessionDAO;
//	}
//
//	@Bean
//	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
//		proxyCreator.setProxyTargetClass(true);
//		return proxyCreator;
//	}
//
//	/**
//	 * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
//	 * @return
//	 */
//	@Bean
//	public ShiroDialect shiroDialect() {
//		return new ShiroDialect();
//	}
//
//	/**
//	 * 启用shiro注解
//	 * @param securityManager
//	 * @return
//	 */
//	@Bean
//	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
//			@Qualifier("securityManager") SecurityManager securityManager) {
//		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//		return authorizationAttributeSourceAdvisor;
//	}



	@Bean
	public EhCacheManager getEhCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:config/ehcache.xml");
		return em;
	}

	@Bean
	UserRealm userRealm(EhCacheManager cacheManager) {
		UserRealm userRealm = new UserRealm();
		userRealm.setCacheManager(cacheManager);
		return userRealm;
	}
	@Bean
	SessionDAO sessionDAO() {
		MemorySessionDAO sessionDAO = new MemorySessionDAO();
		return sessionDAO;
	}

	@Bean
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
		listeners.add(new BDSessionListener());
		sessionManager.setSessionListeners(listeners);
		sessionManager.setSessionDAO(sessionDAO());
		return sessionManager;
	}

	@Bean
	SecurityManager securityManager(UserRealm userRealm) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(userRealm);
		manager.setCacheManager(getEhCacheManager());
		manager.setSessionManager(sessionManager());
		return manager;
	}

	@Bean
	ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/docs/**", "anon");
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/upload/**", "anon");
		filterChainDefinitionMap.put("/files/**", "anon");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/imageCode", "anon");
		filterChainDefinitionMap.put("/", "anon");

		filterChainDefinitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}
