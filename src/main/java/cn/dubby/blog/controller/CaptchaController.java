package cn.dubby.blog.controller;

import cn.dubby.blog.dto.AuthResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

/**
 * Created by yangzheng03 on 2018/1/3.
 */
@Controller
@RequestMapping(path = "captcha")
public class CaptchaController {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @ResponseBody
    @RequestMapping(path = "auth")
    public AuthResult auth() {
        String authCode = UUID.randomUUID().toString();
        String captchaURL = "captcha/" + authCode + ".png";

        return new AuthResult(authCode, captchaURL);
    }

    @RequestMapping(path = "{authCode}.png")
    public void getCaptcha(@PathVariable(name = "authCode") String authCode, HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(name = "w", defaultValue = "500") int w,
                           @RequestParam(name = "h", defaultValue = "200") int h) {
        response.setContentType("image/png");
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Random random = new Random();
        String content = random(6, 0, 0, true, true, null, random);

        // TODO: 2018/1/4 此处需要保存 authCode 和 content 的关系
        logger.info("authCode:{}, content:{}", authCode, content);

        int baseHeight = (h / 5) * 2;
        int startWidth = w / 12;

        try {
            Font font = new Font("宋体", Font.BOLD, h / 2);
            Graphics2D g2 = image.createGraphics();

            g2.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            g2.fillRect(0, 0, w, h);

            g2.setFont(font);

            for (int i = 0; i < 6; ++i) {
                g2.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                AffineTransform old = g2.getTransform();

                boolean upOrDown = random.nextInt(100) % 2 == 0;

                int temp = baseHeight + random.nextInt(100);
                if (upOrDown) {
                    g2.rotate(-0.1 * (random.nextInt(6)), startWidth, temp);
                } else {
                    g2.rotate(0.1 * (random.nextInt(6)), startWidth, temp);
                }

                g2.drawString(content.substring(i, i + 1), startWidth, temp);
                startWidth += w / 8;

                g2.setTransform(old);
            }

            int limit = 5 + random.nextInt(10);
            for (int i = 0; i < limit; ++i) {
                g2.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                double x1 = w * random.nextDouble(), y1 = h * random.nextDouble(), x2 = w - w * random.nextDouble(), y2 = h - h * random.nextDouble();
                Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);

                g2.setStroke(new BasicStroke(2 + random.nextInt(5)));
                g2.draw(line);
            }

            g2.dispose();
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            logger.error("ImageIO.write", e);
        }
    }

    /**
     * @param count   随机字符串的长度
     * @param start   从给定的 char 数组哪个位置开始
     * @param end     从给定的 char 数组哪个位置结束
     * @param letters 只允许字母？
     * @param numbers 只允许数字？
     * @param chars   给定的 char 数组，如果为空，就会使用所有 char
     * @param random  给定一个 random 对象
     * @return 随机字符串
     */
    public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars, Random random) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        } else {
            if (start == 0 && end == 0) {
                end = 123;
                start = 32;
                if (!letters && !numbers) {
                    start = 0;
                    end = 2147483647;
                }
            }

            char[] buffer = new char[count];
            int gap = end - start;

            while (true) {
                while (true) {
                    while (count-- != 0) {
                        char ch;
                        if (chars == null) {
                            ch = (char) (random.nextInt(gap) + start);
                        } else {
                            ch = chars[random.nextInt(gap) + start];
                        }

                        if (letters && Character.isLetter(ch) || numbers && Character.isDigit(ch) || !letters && !numbers) {
                            if (ch >= '\udc00' && ch <= '\udfff') {
                                if (count == 0) {
                                    ++count;
                                } else {
                                    buffer[count] = ch;
                                    --count;
                                    buffer[count] = (char) ('\ud800' + random.nextInt(128));
                                }
                            } else if (ch >= '\ud800' && ch <= '\udb7f') {
                                if (count == 0) {
                                    ++count;
                                } else {
                                    buffer[count] = (char) ('\udc00' + random.nextInt(128));
                                    --count;
                                    buffer[count] = ch;
                                }
                            } else if (ch >= '\udb80' && ch <= '\udbff') {
                                ++count;
                            } else {
                                buffer[count] = ch;
                            }
                        } else {
                            ++count;
                        }
                    }

                    return new String(buffer);
                }
            }
        }
    }

}
