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
 * @date: 2025-06-07 14:49
 * @description:
 */
public class RollCaller extends JFrame {

    private List<String> students;
    private JLabel resultLabel;
    private JButton rollCallButton;
    private Timer timer;
    private Timer stopTimer;
    private int currentIndex;
    private String lastSelectedStudent;

    public RollCaller() {
        // 初始化学生名单
        students = new ArrayList<>();
        loadStudentsFromFile();
        // 设置窗口标题
        setTitle("点名器");
        // 设置窗口大小
        setSize(600, 400);
        // 设置关闭窗口时的操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 设置窗口布局（使用BorderLayout）
        setLayout(new BorderLayout());

        // 创建主显示面板
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setOpaque(false);
        namePanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // 创建结果显示标签
        resultLabel = new JLabel("点名", SwingConstants.CENTER);
        resultLabel.setFont(new Font("微软雅黑", Font.BOLD, 72));
        resultLabel.setForeground(Color.BLACK);
        namePanel.add(resultLabel, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(600, 80));

        // 创建按钮
        rollCallButton = new JButton("点名");
        rollCallButton.setPreferredSize(new Dimension(120, 50));
        rollCallButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
        rollCallButton.setForeground(Color.BLACK);
        rollCallButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        rollCallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRollCall();
            }
        });
        buttonPanel.add(rollCallButton);

        // 添加组件到窗口
        add(namePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 初始化定时器
        timer = new Timer(100, e -> {
            if (students.isEmpty()) {
                timer.stop();
                resultLabel.setText("请检查学生名单");
                return;
            }
            currentIndex = (currentIndex + 1) % students.size();
            resultLabel.setText(students.get(currentIndex));
        });

        stopTimer = new Timer(3000, e -> {
            timer.stop();
            stopTimer.stop();
            if (!students.isEmpty()) {
                Random random = new Random();
                int finalIndex = random.nextInt(students.size());
                lastSelectedStudent = students.get(finalIndex);

                resultLabel.setForeground(new Color(255, 215, 0)); // 金色更醒目
                resultLabel.setFont(new Font("微软雅黑", Font.BOLD, 96));
                resultLabel.setText(lastSelectedStudent);

                rollCallButton.setEnabled(true);
                rollCallButton.setText("点名");
                rollCallButton.setForeground(Color.BLACK);
            }
        });
        stopTimer.setRepeats(false);

        setVisible(true);
    }

    private void loadStudentsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String studentName = line.trim().split(",")[0];
                    students.add(studentName);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "读取学生名单出错：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRollCall() {
        if (students.isEmpty()) {
            resultLabel.setText("请检查学生名单");
            return;
        }

        // 更新按钮状态
        rollCallButton.setEnabled(false);
        rollCallButton.setText("点名");
        rollCallButton.setForeground(new Color(200, 200, 200));
        // 重置并启动定时器
        currentIndex = 0;
        if (!timer.isRunning()) {
            resultLabel.setForeground(Color.BLACK);
            resultLabel.setFont(new Font("微软雅黑", Font.BOLD, 72));
            timer.start();
            stopTimer.start();
        }
    }


}
