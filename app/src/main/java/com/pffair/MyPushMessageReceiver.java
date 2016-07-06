package com.pffair;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by password on 16-7-6.
 * Description
 *
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 *
 * @author mayixiang
 */
public class MyPushMessageReceiver extends PushMessageReceiver {
    private String mRegId;
    private String mTopic;
    private String mAlias;
    private String mAccount;
    private String mStartTime;
    private String mEndTime;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceivePassThroughMessage(context, miPushMessage);
//        Message msg = Message.obtain();
//        msg.obj = "content: " + miPushMessage
//                .getContent();
//        MyApplication.getHandler().sendMessage(msg);

        Log.v(MyApplication.TAG,
                "onReceivePassThroughMessage is called. " + miPushMessage.toString());
        String log = context.getString(R.string.recv_passthrough_message, miPushMessage.getContent());
        if (!TextUtils.isEmpty(miPushMessage.getTopic())) {
            mTopic = miPushMessage.getTopic();
        } else if (!TextUtils.isEmpty(miPushMessage.getAlias())) {
            mAlias = miPushMessage.getAlias();
        }

        Message msg = Message.obtain();
        msg.obj = log;
        MyApplication.getHandler().sendMessage(msg);
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageClicked(context, miPushMessage);
//        Message msg = Message.obtain();
//        msg.obj = "title: " + miPushMessage.getTitle() + "\n" + "content: " + miPushMessage
//                .getContent();
//        MyApplication.getHandler().sendMessage(msg);

        Log.v(MyApplication.TAG,
                "onNotificationMessageClicked is called. " + miPushMessage.toString());
        String log = context.getString(R.string.click_notification_message, miPushMessage.getContent());

        if (!TextUtils.isEmpty(miPushMessage.getTopic())) {
            mTopic = miPushMessage.getTopic();
        } else if (!TextUtils.isEmpty(miPushMessage.getAlias())) {
            mAlias = miPushMessage.getAlias();
        }

        Message msg = Message.obtain();
        if (miPushMessage.isNotified()) {
            msg.obj = log;
        }
        MyApplication.getHandler().sendMessage(msg);
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage miPushMessage) {
        super.onNotificationMessageArrived(context, miPushMessage);
        Log.v(MyApplication.TAG,
                "onNotificationMessageArrived is called. " + miPushMessage.toString());
        String log = context.getString(R.string.arrive_notification_message, miPushMessage.getContent());

        if (!TextUtils.isEmpty(miPushMessage.getTopic())) {
            mTopic = miPushMessage.getTopic();
        } else if (!TextUtils.isEmpty(miPushMessage.getAlias())) {
            mAlias = miPushMessage.getAlias();
        }

        Message msg = Message.obtain();
        msg.obj = log;
        MyApplication.getHandler().sendMessage(msg);
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onCommandResult(context, miPushCommandMessage);
        Log.v(MyApplication.TAG,
                "onCommandResult is called. " + miPushCommandMessage.toString());
        String command = miPushCommandMessage.getCommand();
        List<String> arguments = miPushCommandMessage.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                log = context.getString(R.string.register_success);
            } else {
                log = context.getString(R.string.register_fail);
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
                log = context.getString(R.string.set_alias_success, mAlias);
            } else {
                log = context.getString(R.string.set_alias_fail, miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
                log = context.getString(R.string.unset_alias_success, mAlias);
            } else {
                log = context.getString(R.string.unset_alias_fail, miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ACCOUNT.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mAccount = cmdArg1;
                log = context.getString(R.string.set_account_success, mAccount);
            } else {
                log = context.getString(R.string.set_account_fail, miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mAccount = cmdArg1;
                log = context.getString(R.string.unset_account_success, mAccount);
            } else {
                log = context.getString(R.string.unset_account_fail, miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
                log = context.getString(R.string.subscribe_topic_success, mTopic);
            } else {
                log = context.getString(R.string.subscribe_topic_fail, miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
                log = context.getString(R.string.unsubscribe_topic_success, mTopic);
            } else {
                log = context.getString(R.string.unsubscribe_topic_fail, miPushCommandMessage.getReason());
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
                log = context.getString(R.string.set_accept_time_success, mStartTime, mEndTime);
            } else {
                log = context.getString(R.string.set_accept_time_fail, miPushCommandMessage.getReason());
            }
        } else {
            log = miPushCommandMessage.getReason();
        }
//        MainActivity.logList.add(0, getSimpleDate() + "    " + log);

        Message msg = Message.obtain();
        msg.obj = log;
        MyApplication.getHandler().sendMessage(msg);
    }

    @Override
    public void onReceiveRegisterResult(Context context,
            MiPushCommandMessage miPushCommandMessage) {
        super.onReceiveRegisterResult(context, miPushCommandMessage);
        Log.v(MyApplication.TAG,
                "onReceiveRegisterResult is called. " + miPushCommandMessage.toString());
        String command = miPushCommandMessage.getCommand();
        List<String> arguments = miPushCommandMessage.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                log = context.getString(R.string.register_success);
            } else {
                log = context.getString(R.string.register_fail);
            }
        } else {
            log = miPushCommandMessage.getReason();
        }

        Log.i("mRegId: ",mRegId);
        Message msg = Message.obtain();
        msg.obj = log;
        MyApplication.getHandler().sendMessage(msg);
    }
}


