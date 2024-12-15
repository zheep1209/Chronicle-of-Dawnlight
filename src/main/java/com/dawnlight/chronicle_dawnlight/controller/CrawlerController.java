package com.dawnlight.chronicle_dawnlight.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CrawlerController {

    private static final Logger log = LoggerFactory.getLogger(CrawlerController.class);

    /**
     * 爬取知乎热榜
     *
     * @return 热榜列表
     */
    @GetMapping("/zhihu/hot")
    public List<Map<String, String>> getZhihuHotList() {
        String url = "https://www.zhihu.com/hot";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";
        String cookie = "_xsrf=HLWZT2CwSiUtYUyLdMOL99FAsnmIUqfZ; _zap=51062b33-9cb4-480a-ae2d-fe21de61052f; d_c0=ABBSsf5bdBmPTjc8DEWp-11tYAo0fD7c_rM=|1730085473; q_c1=41788ca6ac8240d780efee148c460c57|1730158108000|1730158108000; Hm_lvt_98beee57fd2ef70ccdd5ca52b9740c49=1731973207,1731979069,1732011211,1732066141; z_c0=2|1:0|10:1732712158|4:z_c0|80:MS4xd092T0dnQUFBQUFtQUFBQVlBSlZUZDVrTkdoZW9zTmhwdDJrUUZCMHE4M0t4NUNlU255ekxBPT0=|af89273ab50b0d8b7544726266cc2127773b9efe0e89f17ef4f7565ea938b56a; edu_user_uuid=edu-v1|867c4384-664f-4dd7-b0e3-12f39d7c831d; __zse_ck=004_hwTO2nS8T6OMRHy5yqNqX4da";

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("User-Agent", userAgent);
        headers.add("Cookie", cookie);

        // Send GET request
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
        String response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, String.class).getBody();
        // Parse HTML response using Jsoup
        Document doc = Jsoup.parse(response);
        Elements elements = doc.select(".HotItem-img");

        // Extract titles
        List<Map<String, String>> hotList = new ArrayList<>();
        elements.forEach(element -> {
            Map<String, String> item = new HashMap<>();
            String title = element.attr("title");
            String href = element.attr("href");
            item.put(title, href);
            if (!title.isEmpty()) {
                hotList.add(item);
            }
        });
        return hotList;
    }

    /**
     * 爬取百度贴吧热榜
     *
     * @return 热榜列表
     */
    @GetMapping("/tieba/hot")
    public List<Map<String, Object>> getBaiduTiebaHotTopics() {
        String url = "https://tieba.baidu.com/hottopic/browse/topicList";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";
        String cookie = "XFI=d44f99b0-bab8-11ef-b08b-8d543f85364b; XFCS=B71C8DF9FFE13371B80CA59D3045BCD862BF91C0F7CC48DBDB1E8B5ECFA1FDCC; ...";

        // Create RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Set headers
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.add("User-Agent", userAgent);
        headers.add("Cookie", cookie);

        // Send GET request
        org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, String.class);

        // Parse JSON response using Jackson
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            JsonNode root = new com.fasterxml.jackson.databind.ObjectMapper().readTree(response.getBody());
            JsonNode topicListNode = root.path("data").path("bang_topic").path("topic_list");

            // Loop through each topic and add to the list
            if (topicListNode.isArray()) {
                for (JsonNode topic : topicListNode) {
                    Map<String, Object> topicMap = new HashMap<>();
                    topicMap.put("topic_name", topic.path("topic_name").asText());
                    topicMap.put("discuss_num", topic.path("discuss_num").asInt());
                    topicMap.put("topic_url", topic.path("topic_url").asText());

                    resultList.add(topicMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Return null or empty list in case of error
        }

        return resultList;  // Spring will automatically convert this to JSON
    }
}
