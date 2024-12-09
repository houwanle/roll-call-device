package com.lele.rollcall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: lele
 * @date: 2024/12/9 11:22
 * @description: 主界面类
 */

public class MainFrame extends JFrame implements ActionListener {

    public static boolean flag = false;
//    public JButton enter = new JButton("确定");

    public JButton enter = new JButton("点名");
    public JButton exit = new JButton("停止");
    public JLabel text = new JLabel("点名");
    public JLabel name = new JLabel("姓名");

    public String[] nameList = {"王一","刘二","赵三","周四","马五","龙六","张七","贾八","钟九"};

    public JLabel message = new JLabel("学生总数："+nameList.length);
    public EnterThread mainThread = null;

    public MainFrame() {
        this.setTitle("点名系统");
        this.setSize(230,150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(null);    //布局为空;
        this.setFocusable(false);

        text.setBounds(20, 10, 100, 30);
        name.setBounds(100, 10, 180, 30);
        enter.setBounds(20, 50, 80, 20);
        exit.setBounds(110, 50, 80, 20);
        message.setBounds(120, 80, 100, 20);

        this.add(text);
        this.add(name);
        this.add(enter);
        this.add(exit);
        this.add(message);

        enter.setEnabled(true);
        exit.setEnabled(false);

        Font font = new Font("华文楷体",Font.BOLD,22);
        this.text.setFont(font);
        this.name.setFont(font);

        enter.addActionListener(this);
        exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enter) {
            mainThread = new EnterThread(this);
            mainThread.start();
            enter.setEnabled(false);
            exit.setEnabled(true);
        }

        if (e.getSource() == exit) {
            new ExitThread(this).start();
            exit.setEnabled(false);
        }
    }
}
