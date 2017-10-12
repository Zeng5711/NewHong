package com.hongbang.ic.keycenter;

import android.os.SystemClock;

import com.hongbang.ic.keycenter.exceptions.TaskRuntimeException;
import com.hongbang.ic.keycenter.tasks.OnTaskTracker;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 分包报文
 * <p/>
 * Created by xionghf on 16/5/9.
 */
public class SplitPacket extends AbstractPacket {

    private static final int MAX_DATA_LENGTH = 224;

    private static final int SOCKET_INTERVAL_FIRST = 5000;

    private static final int SOCKET_INTERVAL = 80;

    public int total = 1;

    public int current = 1;

    @Override
    protected byte[] getSocketData() {
        int len;
        if (data == null) {
            len = 0;
        } else {
            len = data.length;
        }
        length = (byte) (len + 10);
        byte[] result = new byte[len + 14];

        result[0] = head;
        result[1] = cmd;
        result[2] = length;

        if (mac != null) {
            System.arraycopy(mac, 0, result, 3, Math.min(6, mac.length));
        }

        result[9] = (byte) (total & 0xff);
        result[10] = (byte) (total >> 8 & 0xff);
        result[11] = (byte) (current & 0xff);
        result[12] = (byte) (current >> 8 & 0xff);

        if (len > 0) {
            System.arraycopy(data, 0, result, 13, len);
        }
        int checksum = 0;
        for (int i = 0; i < result.length - 1; i++) {
            checksum += result[i] & 0xff;
            checksum &= 0xff;
        }
        checksum ^= 0xff;
        result[result.length - 1] = (byte) (checksum & 0xff);

        return result;
    }

    public interface ISendCallback {
        void onSuccess();

        void onError(Throwable ex);

        void onSendData(int total, int current);
    }

    private static final ISendCallback defaultCallback = new ISendCallback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(Throwable ex) {
            if (ex != null) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onSendData(int total, int current) {
        }
    };

    public static class Task implements Interruptable {

        private Socket mSocket;
        private int mCommand;
        private File mFile;
        private ISendCallback mCallback;
        private OnTaskTracker mTracker;
        private byte[] mBytes;

        public Task() {
        }

        public Task socket(Socket socket) {
            mSocket = socket;
            return this;
        }

        public Task command(int command) {
            mCommand = command;
            return this;
        }

        public Task file(File file) {
            mFile = file;
            return this;
        }

        public Task bytes(byte[] bytes) {
            mBytes = bytes;
            return this;
        }

        public Task tracker(OnTaskTracker tracker) {
            mTracker = tracker;
            return this;
        }

        public Task callback(ISendCallback callback) {
            mCallback = callback;
            return this;
        }

        public Task start() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    startWork();
                }
            }).start();
            return this;
        }

        public Task startDelayed(final long delayed) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (delayed > 0) {
                        SystemClock.sleep(delayed);
                    }
                    startWork();
                }
            }).start();
            return this;
        }

        private void startWork() {
            if (mCallback == null) {
                mCallback = defaultCallback;
            }
            if (mSocket == null || mSocket.isClosed()) {
                mCallback.onError(new TaskRuntimeException("socket closed"));
                return;
            }


            if (mFile != null) {
                mTracker.onSendData("文件 =====>sendFile===" + (mFile == null));
                sendFile();

            } else {
                mTracker.onSendData("字节 =====>sendBytes===" + (mFile == null));
                sendBytes();
            }
        }

        private void sendFile() {
            if (!mFile.exists() || !mFile.isFile() || !mFile.canRead()) {
                mCallback.onError(new TaskRuntimeException("文件不可用: " + mFile));
                return;
            }
            if (mFile.length() == 0) {
                mCallback.onSuccess();
                return;
            }
//            double b= (mFile.length() + MAX_DATA_LENGTH - 1) / MAX_DATA_LENGTH;
            int total = (int) (mFile.length()) % MAX_DATA_LENGTH;
            int i = (int) (mFile.length()) / MAX_DATA_LENGTH;
            total = total == 0 ? i : i++;

            mTracker.onSendData("sendFile =====>total===" + total);
            mTracker.onSendData("sendFile =====>mFile.length()===" + mFile.length());
            try {
                FileInputStream fis = new FileInputStream(mFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
                byte[] buffer = new byte[MAX_DATA_LENGTH];
                int length;
                int count = 1;
                while ((length = bis.read(buffer)) > 0) {
                    if (isInterrupted) {
                        return;
                    }
                    SplitPacket packet = new SplitPacket();
                    packet.cmd = (byte) (mCommand & 0xff);
                    packet.total = total;
                    packet.current = count;
                    packet.data = new byte[length];
                    System.arraycopy(buffer, 0, packet.data, 0, length);

                    if (!packet.send(mSocket, mTracker)) {
                        mCallback.onError(new TaskRuntimeException("socket send failed"));
                    } else if (count == 1) {
                        SystemClock.sleep(SOCKET_INTERVAL_FIRST);
                    } else if (count < total) {
                        SystemClock.sleep(SOCKET_INTERVAL);
                    }
                    mCallback.onSendData(total, count);
                    count++;
                }
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!isInterrupted) {
                    mCallback.onSuccess();
                }
            } catch (IOException e) {
                mCallback.onError(new TaskRuntimeException(e.getMessage()));
            }
        }

        private void sendBytes() {
            int total;
            if (mBytes == null) {//|| mBytes.length <= MAX_DATA_LENGTH
                total = 1;
            } else {
                total = (mBytes.length + MAX_DATA_LENGTH - 1) / MAX_DATA_LENGTH;
            }

            mTracker.onSendData("total =====>" + total);

            ArrayList<SplitPacket> packets = new ArrayList<>();
            for (int i = 0; i < total; i++) {
                SplitPacket packet = new SplitPacket();
                packet.cmd = (byte) (mCommand & 0xff);
                packet.total = total;
                packet.current = i + 1;
                if (mBytes != null && mBytes.length > 0) {
                    int length = Math.min(mBytes.length - i * MAX_DATA_LENGTH, MAX_DATA_LENGTH);
                    packet.data = new byte[length];
                    System.arraycopy(mBytes, i * MAX_DATA_LENGTH, packet.data, 0, length);
                }
                packets.add(packet);
            }
            int interval = SOCKET_INTERVAL_FIRST;
            for (int i = 0; i < packets.size(); i++) {
                if (isInterrupted) {
                    return;
                }
                SplitPacket packet = packets.get(i);
                if (!packet.send(mSocket, mTracker)) {
                    mCallback.onError(new TaskRuntimeException("socket send failed"));
                    return;
                }
                mCallback.onSendData(packets.size(), i + 1);
                SystemClock.sleep(interval);
                interval = SOCKET_INTERVAL;
            }
            if (!isInterrupted) {
                mCallback.onSuccess();
            }
        }

        private boolean isInterrupted = false;

        @Override
        public void interrupt() {
            isInterrupted = true;
        }
    }
}
