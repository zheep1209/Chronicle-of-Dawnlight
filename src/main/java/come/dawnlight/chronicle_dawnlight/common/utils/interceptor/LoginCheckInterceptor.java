package come.dawnlight.chronicle_dawnlight.common.utils.interceptor;

import com.alibaba.fastjson.JSON;
import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import come.dawnlight.chronicle_dawnlight.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.UUID;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor{
    //  目标资源方法执行前执行（Controller方法执行之前）， true：放行， false：不放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //  TODO 1.获取请求url
        String requestURL = request.getRequestURL().toString(); //不toString就是StringBuffer类型
        log.info("请求的url:{}", requestURL);

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
//            log.info("请求头 - {}: {}", headerName, request.getHeader(headerName));
        }

        //  TODO 2.判断请求url中是否包含login，如果包含，说明是登录操作，放行
        if (requestURL.contains("/login")) {
            log.info("登录操作，放行...");
            return true;
        }
        if (requestURL.contains("/register")) {
            log.info("注册操作，放行...");
            return true;
        }
        if (requestURL.contains("/getEmail")) {
            log.info("获取验证码，放行...");
            return true;
        }
        if (requestURL.contains("/publicArticle")) {
            log.info("获取公开文章，放行...");
            return true;
        }
        if (requestURL.contains("/getTouhouUrl")) {
            log.info("获取图片，放行...");
            return true;
        }


        //  TODO 3.获取请求头中的令牌（token）
        String token = request.getHeader("Authorization");

        //  TODO 4.判断令牌是否存在，如果不存在，返回错误结果（未登录）
        if (!StringUtils.hasLength(token)) { //spring当中的工具类
//            说明字符串为null，返回错误结果（未登录）
            log.info("请求头token为空，返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
//          手动转JSON
            String errorJson = JSON.toJSONString(error);
//          response.getWriter()获取输出流，write()直接将数据响应给浏览器
            response.getWriter().write(errorJson);
            return false;
        }
        //  TODO 5.解析token，如果解析失败，返回错误结果（未登录）
//      说明存在令牌，校验
        Claims claims;
        try {
            claims = JwtUtil.parseJWT("zheep", token);
        } catch (Exception e) { // 出现异常代表着解析失败
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
//          手动转JSON
            String errorJson = JSON.toJSONString(error);
//          response.getWriter()获取输出流，write()直接将数据响应给浏览器
            response.getWriter().write(errorJson);
            return false;
        }
//       到这里说明令牌解析成功，直接放行
        //  TODO 6.放行
        UUID uuid = UUID.fromString(claims.get("id", String.class));
        BaseContext.setCurrentThreadId(uuid);
        log.info("令牌合法，放行");
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        return true;
    }

}