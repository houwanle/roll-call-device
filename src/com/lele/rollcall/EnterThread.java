package com.lele.rollcall;

/**
 * @author: lele
 * @date: 2024/12/9 11:25
 * @description:
 */

public class EnterThread extends Thread {

    private MainFrame mainFrame = null;
    private boolean isStop = true;
    public int time = 50;

    public EnterThread(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void stopThread() {
        this.isStop = false;
    }

    public void run() {
        try {
            int index = 0;
            while(isStop) {
                if (index == Constant.NAME_LIST.length) {
                    index = 0;
                }
                this.mainFrame.name.setText(Constant.NAME_LIST[index]);
                index++;

                Thread.sleep(time);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mainFrame.enter.setEnabled(true);
    }
}
