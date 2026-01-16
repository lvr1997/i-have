package com.lvr.ihave.web.controller;

import com.lvr.ihave.annotation.PassToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 图片验证码接口
 */
@Controller
public class CaptcheController {
    Random r = new Random();
    /**
     * 发送图片验证码
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PassToken
    @ResponseBody
    @GetMapping(value = "/captcha")
    public Map<String, Object> getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //画布
        BufferedImage image = new BufferedImage(125, 33, BufferedImage.TYPE_INT_RGB);
        //画笔
        Graphics g = image.getGraphics();
        //画笔颜色
        g.setColor(new Color(255, 255, 255));
        //画布颜色
        g.fillRect(0, 0, 125, 33);
        //随机字符

        //生成随机字符
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            String num = getNumber(1);

            buffer.append(num);

            int h = (int) (33 * 0.7 + 33 * 0.3 * r.nextDouble());

            g.setFont(new Font(null, Font.BOLD | Font.ITALIC, h));

            g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));

            g.drawString(num, 125 / 5 * i, h);
        }

        String number = buffer.toString();
        System.out.println("number: " + number);

        //将验证码绑定到session对象上
        HttpSession session = request.getSession();
        session.setAttribute("number", number);

        //添加干扰线
        for (int i = 0; i < 8; i++) {
            g.drawLine(r.nextInt(125), r.nextInt(33), r.nextInt(125), r.nextInt(33));
        }

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bs);
        String imgsrc = Base64.getEncoder().encodeToString(bs.toByteArray());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("success",true);
        jsonMap.put("image","data:image/png;base64,"+imgsrc);
        return jsonMap;
    }

    public String getNumber(int count) {
        char[] c = new char[36];
        for (int i = 0; i < c.length - 10; i++) {
            int cc = 97 + i;
            c[i] = (char) cc;
        }
        for (int i = c.length - 10; i < c.length; i++) {
            int cc = 22 + i;
            c[i] = (char) cc;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            char a = c[r.nextInt(c.length)];
            builder.append(a);
        }
        return builder.toString();
    }

}
