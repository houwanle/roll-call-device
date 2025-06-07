package com.lele.rollcall;

import javax.swing.*;

/**
 * @author: lele
 * @date: 2025-06-07 17:36
 * @description:
 */
public class Main {
    public static void main(String[] args) {
        // 在事件调度线程中创建和显示 GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RollCallSystem();
            }
        });
    }
}
