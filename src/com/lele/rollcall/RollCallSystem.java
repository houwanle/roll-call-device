package com.lele.rollcall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: lele
 * @date: 2025-06-07 17:34
 * @description:
 */
public class RollCallSystem extends JFrame {

    private JLabel resultLabel;
    private JButton startButton;
    private JButton stopButton;
    private List<String> students;
    private Timer timer;
    private Random random;
    // 初始延迟调整为 50 毫秒
    private int delay = 50;
    // 每次延迟递增调整为 20 毫秒
    private final int delayIncrement = 20;
    // 最大延迟调整为 2000 毫秒
    private final int maxDelay = 2000;
    private JLabel countLabel;

    public RollCallSystem() {
        // 初始化学生列表
        students = new ArrayList<>();
        // 读取姓名文件
        readNamesFromFile("names.txt");

        random = new Random();

        // 设置窗口标题
        setTitle("点名器");
        // 设置窗口大小
        setSize(300, 200);
        // 设置关闭窗口时的操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口布局为 BorderLayout
        setLayout(new BorderLayout());

        // 创建结果标签，用于显示点名结果，初始提示信息，确保文本居中
        resultLabel = new JLabel("点击开始点名", SwingConstants.CENTER);
        resultLabel.setFont(new Font("宋体", Font.PLAIN, 20));
        // 将结果标签添加到窗口中心区域
        add(resultLabel, BorderLayout.CENTER);

        // 创建面板用于放置按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // 创建开始按钮
        startButton = new JButton("开始点名");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startRollCall();
            }
        });
        buttonPanel.add(startButton);

        // 创建停止按钮
        stopButton = new JButton("停止");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopRollCall();
            }
        });
        stopButton.setEnabled(false);
        buttonPanel.add(stopButton);

        // 将按钮面板添加到窗口底部
        add(buttonPanel, BorderLayout.SOUTH);

        // 创建显示人数的标签
        countLabel = new JLabel("人数: " + students.size(), SwingConstants.RIGHT);
        countLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        JPanel countPanel = new JPanel(new BorderLayout());
        countPanel.add(countLabel, BorderLayout.SOUTH);
        add(countPanel, BorderLayout.EAST);

        // 使窗口可见
        setVisible(true);
    }

    private void readNamesFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                students.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("读取文件时出错: " + e.getMessage());
        }
    }

    private void startRollCall() {
        if (students.isEmpty()) {
            resultLabel.setText("没有学生可供点名。");
            return;
        }
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        delay = 50;
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = random.nextInt(students.size());
                resultLabel.setText(students.get(index));
            }
        });
        timer.start();
    }

    private void stopRollCall() {
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (delay < maxDelay) {
                    try {
                        Thread.sleep(100);
                        delay += delayIncrement;
                        timer.setDelay(delay);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                timer.stop();
            }
        }).start();
    }


}
