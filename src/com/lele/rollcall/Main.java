package com.lele.rollcall;

import javax.swing.*;

/**
 * @author: lele
 * @date: 2025-06-07 14:50
 * @description: 程序启动类
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RollCaller());
    }
}
