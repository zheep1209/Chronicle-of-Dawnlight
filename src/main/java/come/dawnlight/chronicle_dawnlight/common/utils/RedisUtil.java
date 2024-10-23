package come.dawnlight.chronicle_dawnlight.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置键值对，并设置过期时间
     */
    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MINUTES);
    }

    /**
     * 获取值
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除键
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
}
