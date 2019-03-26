package com.simon.game;

import java.awt.*;

/**
 * 游戏物体的父类
 * @author vodka
 */
public class GameObject {

    Image img;
    double x;
    double y;
    int speed;
    int width;
    int height;

    public void drawSelf(Graphics g) {
        g.drawImage(img, (int)x , (int)y, null);
    }

    public GameObject(Image img, double x, double y) {
        super();
        this.img = img;
        this.x = x;
        this.y = y;
    }

    public GameObject() {

    }

    /**
     * 返回物体所在矩形，方便进行碰撞检测
     * @return
     */
    public Rectangle getRect() {
        return new Rectangle((int)x, (int)y, width, height);
    }
}
