package com.simon.game;

import java.awt.*;

/**
 * 炮弹类
 * @author vodka
 */
public class Shell extends GameObject {

    double degree;

    public Shell() {
        x = 250;
        y = 250;
        width = 10;
        height = 10;
        speed = 3;

        // 炮弹弧度
        degree = Math.random() * Math.PI * 2;
    }

    public void draw(Graphics g) {
        // 设置颜色
        Color color = g.getColor();
        g.setColor(Color.yellow);
        // 填充颜色
        g.fillOval((int) x, (int) y, width, height);

        // 炮弹沿任意角度飞行
        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);

        // 控制小球反弹
        if (x < 0|| x > Constant.GAME_WIDTH - width) {
            degree = Math.PI - degree;
        }

        if (y < 20 || y > Constant.GAME_HEIGHT - height) {
            degree = - degree;
        }

        g.setColor(color);
    }
}
