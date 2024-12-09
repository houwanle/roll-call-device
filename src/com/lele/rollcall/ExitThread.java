package com.lele.rollcall;

/**
 * @author: lele
 * @date: 2024/12/9 11:27
 * @description:
 */

public class ExitThread extends Thread {
    private MainFrame mainFrame = null;

    public ExitThread(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void run() {
        try {
            for (int i = 0; i < 400; i += 50) {
                this.mainFrame.mainThread.time += i;
                sleep(1600);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mainFrame.mainThread.stopThread();
    }
}
