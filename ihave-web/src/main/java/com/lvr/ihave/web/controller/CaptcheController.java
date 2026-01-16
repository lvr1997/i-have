package com.lvr.ihave.web.controller;

import com.lvr.ihave.annotation.PassToken;
import com.lvr.ihave.constant.Constant;
import com.lvr.ihave.util.JSONResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.util.Random;

/**
 * 图片验证码接口
 */
@Controller
public class CaptcheController {
    private final Logger logger = LoggerFactory.getLogger(CaptcheController.class);
    Random r = new Random();
    /**
     * 发送图片验证码
     * @param request
     * @param response
     * @param time 时间戳（防止缓存）
     * @param width 验证码宽度（默认125）
     * @param height 验证码高度（默认33）
     * @return
     * @throws IOException
     */
    @PassToken
    @ResponseBody
    @GetMapping(value = "/captcha")
    public JSONResult getCaptcha(HttpServletRequest request, 
                               HttpServletResponse response,
                               @RequestParam(value = "time", required = false) Long time,
                               @RequestParam(value = "width", defaultValue = "125") Integer width,
                               @RequestParam(value = "height", defaultValue = "33") Integer height) throws IOException {
        
        // 验证参数合理性
        if (width < 80 || width > 300) {
            width = 125;
        }
        if (height < 20 || height > 100) {
            height = 33;
        }
        
        // 画布
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 画笔
        Graphics g = image.getGraphics();
        // 画笔颜色
        g.setColor(new Color(255, 255, 255));
        // 画布颜色
        g.fillRect(0, 0, width, height);
        
        // 生成随机字符（5个字符）
        StringBuffer buffer = new StringBuffer();
        int charCount = 5;
        int charWidth = width / charCount;
        
        for (int i = 0; i < charCount; i++) {
            String num = getNumber(1);
            buffer.append(num);
            
            // 字符大小为高度的70%-100%
            int fontSize = (int) (height * 0.7 + height * 0.3 * r.nextDouble());
            g.setFont(new Font(null, Font.BOLD | Font.ITALIC, fontSize));
            g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            
            // 字符位置
            int x = charWidth * i;
            int y = fontSize;
            g.drawString(num, x, y);
        }
        
        String captchaCode = buffer.toString();
        logger.info("生成验证码：{}，大小：{}x{}", captchaCode, width, height);
        
        // 将验证码绑定到session对象上
        HttpSession session = request.getSession();
        session.setAttribute(Constant.CAPTCHA_CODE_KEY, captchaCode);
        
        // 添加干扰线（线条数量根据验证码大小动态调整）
        int lineCount = Math.max(5, (width * height) / 1000);
        for (int i = 0; i < lineCount; i++) {
            g.setColor(new Color(r.nextInt(200), r.nextInt(200), r.nextInt(200)));
            g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
        }
        
        // 添加噪点
        int noiseCount = Math.max(20, (width * height) / 50);
        for (int i = 0; i < noiseCount; i++) {
            g.setColor(new Color(r.nextInt(150), r.nextInt(150), r.nextInt(150)));
            g.fillRect(r.nextInt(width), r.nextInt(height), 1, 1);
        }
        
        // 生成base64格式图片
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bs);
        String imgsrc = Base64.getEncoder().encodeToString(bs.toByteArray());
        
        return JSONResult.success(Constant.SUCCESS_DATA, "data:image/png;base64," + imgsrc);
    }

    /**
     * 生成随机字符，排除容易混淆的字符
     * 排除字符：1 l i I L O 0 o
     * @param count 字符数量
     * @return 随机字符串
     */
    public String getNumber(int count) {
        // 只使用容易辨认的字符
        String validChars = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int index = r.nextInt(validChars.length());
            char a = validChars.charAt(index);
            builder.append(a);
        }
        return builder.toString();
    }

}
