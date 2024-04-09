package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {

    private boolean isRunning = true;
    private Context context;
    private String studentName;
    private String group;

    public ProcessingThread(Context context, String studentName, String group) {
        this.context = context;
        this.studentName = studentName;
        this.group = group;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started!");
        boolean action = true;
        while (isRunning) {
            if (action) {
                sendMessage0();
                action = false;
            } else {
                sendMessage1();
                action = true;
            }
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage0() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[0]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, studentName);
        context.sendBroadcast(intent);
    }

    private void sendMessage1() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[1]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, group);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
