package come.dawnlight.chronicle_dawnlight;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import come.dawnlight.chronicle_dawnlight.pojo.po.ArticlePO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class ChronicleDawnlightApplicationTests {


    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static List<ArticlePO> generateArticleList() {
        List<ArticlePO> articles = new ArrayList<>();

        ArticlePO article1 = new ArticlePO();
        article1.setId(1L);
        article1.setUserId("user1");
        article1.setTitle("Redis与序列化反序列化");
        article1.setContent("<h1>这是文章内容1</h1>");
        article1.setIsPrivate(false);
        article1.setCategoryId(1L);
        article1.setCreatedAt(LocalDateTime.of(2024, 11, 1, 12, 0, 0));
        article1.setUpdatedAt(LocalDateTime.of(2024, 11, 1, 13, 0, 0));
        articles.add(article1);

        ArticlePO article2 = new ArticlePO();
        article2.setId(2L);
        article2.setUserId("user2");
        article2.setTitle("Java中的泛型使用");
        article2.setContent("<p>这是文章内容2</p>");
        article2.setIsPrivate(true);
        article2.setCategoryId(2L);
        article2.setCreatedAt(LocalDateTime.of(2024, 11, 2, 14, 0, 0));
        article2.setUpdatedAt(LocalDateTime.of(2024, 11, 2, 15, 0, 0));
        articles.add(article2);

        ArticlePO article3 = new ArticlePO();
        article3.setId(3L);
        article3.setUserId("user3");
        article3.setTitle("Spring Boot与Redis集成");
        article3.setContent("<p>这是文章内容3</p>");
        article3.setIsPrivate(false);
        article3.setCategoryId(null); // 无分类
        article3.setCreatedAt(LocalDateTime.of(2024, 11, 3, 10, 0, 0));
        article3.setUpdatedAt(LocalDateTime.of(2024, 11, 3, 11, 0, 0));
        articles.add(article3);

        return articles;
    }

    public static void main(String[] args) {
        List<ArticlePO> articles = generateArticleList();
        articles.forEach(System.out::println); // 打印测试数据
    }

    /**
     * 设置键值对（对象序列化为JSON存储），并设置过期时间
     */
    public <T> void set(String key, T value, long timeout) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value cannot be null");
        }
        try {
            String json = objectMapper.writeValueAsString(value);
            stringRedisTemplate.opsForValue().set(key, json, timeout, TimeUnit.MINUTES);
            System.out.println("Redis set success: key=" + key + ", value=" + json + ", timeout=" + timeout);
        } catch (Exception e) {
            System.err.println("Redis set operation failed: key=" + key + ", error=" + e.getMessage());
            throw new RuntimeException("Redis set operation failed", e);
        }
    }

    /**
     * 获取值（JSON反序列化为对象）
     */
    public <T> T get(String key, TypeReference<T> typeReference) {
        try {
            String json = stringRedisTemplate.opsForValue().get(key);
            System.out.println("Redis get success: key=" + key + ", value=" + json);
            if (json == null) {
                return null;
            }
            // 使用 ObjectMapper 解析 JSON 字符串为指定类型
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            System.err.println("Redis get operation failed: key=" + key + ", error=" + e.getMessage());
            throw new RuntimeException("Redis get operation failed", e);
        }
    }

    /**
     * 删除键
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Test
    void contextLoads() {
//        System.out.println(get("getArticlesByUser:9c533672-00e7-47bd-9c58-337425abe3ee", new TypeReference<List<ArticlePO>>() {}));
//        String json = "[{\"id\":82,\"userId\":\"9c533672-00e7-47bd-9c58-337425abe3ee\",\"title\":\"Redis与序列化反序列化\",\"content\":\"<h1>内容</h1>\"}]";
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<ArticlePO> articles = objectMapper.readValue(json, new TypeReference<List<ArticlePO>>() {});
//        System.out.println(articles);
        set("getArticlesByUser:9c533672-00e7-47bd-9c58-337425abe3ee", generateArticleList(), 10);
    }
}

