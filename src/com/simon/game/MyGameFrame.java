package com.simon.game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * 飞机游戏的主窗口
 * @author vodka
 */
public class MyGameFrame extends Frame {

    Image planeImg = GameUtils.getImage("images/plane.png");
    Image bg = GameUtils.getImage("images/bg.jpg");

    /**
     * 创建飞机类
     */
    Plane plane = new Plane(planeImg, 300, 300);

    /**
     * 使用数组创建炮弹类
     */
    Shell[] shells = new Shell[10];

    /**
     * 创建爆炸类
     */
    Explode explode;

    Date startTime = new Date();
    Date endTime;

    /**
     * 游戏持续时间
     */
    int period;

    /**
     * 窗口绘制，自动被调用，g变量相当于画笔
     * @param g
     */
    @Override
    public void paint(Graphics g) {

        Color color = g.getColor();
        g.drawImage(bg, 0, 0, null);
        plane.drawSelf(g);

        // 画出所有的炮弹
        for (int i = 0; i < shells.length; i++) {
            shells[i].draw(g);

            boolean peng = shells[i].getRect().intersects(plane.getRect());

            if (peng) {
                plane.live = false;

                // 爆炸效果实现
                if (explode == null) {
                    explode = new Explode(plane.x, plane.y);
                    endTime = new Date();

                    period = (int) ((endTime.getTime() - startTime.getTime())/1000);
                }
                explode.draw(g);
            }

            // 游戏结束，给出提示
            if (!plane.live) {
                g.setColor(Color.red);
                Font font = new Font("宋体", Font.BOLD, 30);
                g.setFont(font);
                g.drawString("时间：" + period + "秒", (int)plane.x, (int)plane.y);
            }
        }

        g.setColor(color);
    }

    /**
     * 反复重画窗口
     */
    class PaintThread extends Thread {

        @Override
        public void run() {
            while (true) {
                // 重画窗口
                repaint();

                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 定义键盘监听
     */
    class KeyMonitor extends KeyAdapter {
        /**
         * Invoked when a key has been pressed.
         *
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {
            plane.addDirection(e);
        }

        /**
         * Invoked when a key has been released.
         *
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {
            plane.minusDirection(e);
        }
    }

    /**
     * 初始化窗口
     */
    public  void launchFrame() {
        this.setTitle("飞机游戏");
        this.setVisible(true);
        this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        this.setLocation(300, 300);

        // 匿名内部，确保关闭窗口同时退出程序
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // 启动重画的线程
        new PaintThread().start();
        // 增加键盘监听
        addKeyListener(new KeyMonitor());

        // 初始化50个炮弹
        for (int i = 0; i < shells.length; i++) {
            shells[i] = new Shell();
        }
    }

    // 双缓冲
    private Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        }
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public static void main(String[] args) {
        MyGameFrame myGameFrame = new MyGameFrame();
        myGameFrame.launchFrame();
    }
}
