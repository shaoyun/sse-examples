package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

@Controller
public class SSEController {
    @GetMapping(value = "/stream")
    @ResponseBody
    public String getMessage(HttpServletResponse response) {
        response.setContentType("text/event-stream");   // 指定ContentType，不可变
        response.setCharacterEncoding("utf-8");         // 指定响应字符集，是否可变，没测试，但建议指定utf-8
        while (true) {
            String s = "";
            s += "retry: 5000\n";                   // 客户端没有获取到数据，就不断发送新的请求（间隔5秒），直到有数据。
            s += "id: " + UUID.randomUUID() + "\n"; // 这里指定消息ID
            s += "event: message\n";              // 这里定义事件类型，自定义！
            s += "data: " + new Date() + "\n\n";    // 这里设置返回的数据
            try {
                PrintWriter pw = response.getWriter();
                Thread.sleep(1000L);
                pw.write(s);
                pw.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @RequestMapping(value = "")
    public String index() {
        return "index.html";
    }
}
