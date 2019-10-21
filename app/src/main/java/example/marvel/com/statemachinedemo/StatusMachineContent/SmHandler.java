package example.marvel.com.statemachinedemo.StatusMachineContent;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * SmHandler
 *
 * @author j
 * @since 2019/10/20.
 */
class SmHandler extends Handler {
    private static final String TAG = "SmHandler";

    private State msgProcessedState = null;
    /** Reference to the StateMachine */
    private StateMachine mSm;

    public SmHandler(Looper looper, StateMachine stateMachine) {
        super(looper);
        mSm = stateMachine;
    }

    @Override
    public void handleMessage(Message msg) {
        msgProcessedState = processMsg(msg);
        performTransitions(msgProcessedState, msg);
    }

    private void performTransitions(State msgProcessedState, Message msg) {
        Log.d(TAG, "已执行的状态为");
    }

    private State processMsg(Message msg) {
        State curState = mSm.getCurState();
        curState.processMessage(msg);
        return curState;
    }

    public void transitionTo(State destState) {
        mSm.setCurState(destState);
        Log.d(TAG, "切换后的状态是" + mSm.getCurState().getName());
    }
}
