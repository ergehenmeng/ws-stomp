package com.eghm.websocket.config.web.shiro;

import com.eghm.websocket.service.UserService;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/index", "anon");
        definition.addPathDefinition("/login", "anon");
        definition.addPathDefinition("/static/**", "anon");
        definition.addPathDefinition("/**", "authc");
        return definition;
    }

    @Bean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public Realm userRealm(UserService userService) {
        UserRealm realm = new UserRealm();
        realm.setUserService(userService);
        realm.setCacheManager(cacheManager());
        return realm;
    }
}
