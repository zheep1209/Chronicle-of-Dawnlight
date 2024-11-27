package come.dawnlight.chronicle_dawnlight.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 配置支持 Java 8 时间类型的 ObjectMapper
    private final ObjectMapper objectMapper;

    public RedisUtil() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 避免时间戳格式
    }

    /**
     * 设置键值对（对象序列化为 JSON 存储），并设置过期时间
     */
    public <T> void set(String key, T value, long timeout) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value cannot be null");
        }
        try {
            String json = objectMapper.writeValueAsString(value);
            stringRedisTemplate.opsForValue().set(key, json, timeout, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new RuntimeException("Redis set operation failed", e);
        }
    }

    /**
     * 获取值（JSON 反序列化为对象）
     */
    public <T> T get(String key, TypeReference<T> typeReference) {
        try {
            String json = stringRedisTemplate.opsForValue().get(key);
//            System.out.println("Redis get success: key=" + key + ", value=" + json);
            if (json == null) {
                return null;
            }
            // 使用 ObjectMapper 解析 JSON 字符串为指定类型
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
//            System.err.println("Redis get operation failed: key=" + key + ", error=" + e.getMessage());
            throw new RuntimeException("Redis get operation failed", e);
        }
    }

    /**
     * 删除键
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
    /**
     * 删除指定前缀的所有键
     * @param folderPrefix 前缀，例如 "folder:subfolder:"
     */
    public void deleteFolder(String folderPrefix) {
        try {
            // 使用 Redis 模糊查询匹配键
            Set<String> keys = stringRedisTemplate.keys(folderPrefix + "*");
            if (keys != null && !keys.isEmpty()) {
                // 删除匹配的键
                stringRedisTemplate.delete(keys);
                System.out.println("删除成功，删除键数量：" + keys.size());
            } else {
                System.out.println("未找到匹配的键");
            }
        } catch (Exception e) {
            throw new RuntimeException("删除文件夹失败", e);
        }
    }
}
