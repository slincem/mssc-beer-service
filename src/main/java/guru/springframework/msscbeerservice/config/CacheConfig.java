package guru.springframework.msscbeerservice.config;

import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Configuration;

//It was necessary to comment Caching, because ehcache support for Spring 3 changed.
//@EnableCaching
@Configuration
public class CacheConfig implements CachingConfigurer {

}
