package example.marvel.com.statemachinedemo.StatusMachineContent;

import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * StateMachine
 *
 * @author j
 * @since 2019/10/20.
 */
public class StateMachine {
    private static final int CMD_READY_OPERATION_A = 0;
    private static final int CMD_OPERATION_A = 1;
    private static final int CMD_OPERATION_B = 2;

    private State curState;
    private State mInitialState = new InitialState();
    private State mOperationBState = new OperationBState();
    private State mOperationAState = new OperationAState();

    private HandlerThread mSmThread;
    private String mName;
    private SmHandler mSmHandler;

    /**
     * Constructor creates a StateMachine with its own thread.
     *
     * @param name of the state machine
     */
    protected StateMachine(String name) {
        mSmThread = new HandlerThread(name);
        mSmThread.start();
        Looper looper = mSmThread.getLooper();
        initStateMachine(name, looper);
    }

    /**
     * Initialize.
     *
     * @param looper for this state machine
     * @param name   of the state machine
     */
    private void initStateMachine(String name, Looper looper) {
        mName = name;
        mSmHandler = new SmHandler(looper, this);
        setInitialState(mOperationAState);

    }

    public void getLocation() {
        Message msgReady = Message.obtain();
        msgReady.what = CMD_READY_OPERATION_A;
        mSmHandler.sendMessage(msgReady);
        Message msg = Message.obtain();
        msg.what = CMD_OPERATION_A;
        mSmHandler.sendMessage(msg);
        Message msgWrite = Message.obtain();
        msgWrite.what = CMD_OPERATION_B;
        mSmHandler.sendMessage(msgWrite);
    }


    /**
     * transition to destination state. Upon returning
     * from processMessage the current state's exit will
     * be executed and upon the next message arriving
     * destState.enter will be invoked.
     * <p>
     * this function can also be called inside the enter function of the
     * previous transition target, but the behavior is undefined when it is
     * called mid-way through a previous transition (for example, calling this
     * in the enter() routine of a intermediate node when the current transition
     * target is one of the nodes descendants).
     *
     * @param destState will be the state that receives the next message.
     */
    protected final void transitionTo(State destState) {
        mSmHandler.transitionTo(destState);
    }

    private void setInitialState(State state) {
        this.curState = state;
    }

    public State getCurState() {
        return this.curState;
    }

    public void setCurState(State curState) {
        this.curState = curState;
    }

    public class InitialState extends State {
        private static final String TAG = "InitialState";

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case CMD_READY_OPERATION_A: {
                    Log.d(TAG, "现在是初始状态，准备切换到OperationA状态");
                    transitionTo(mOperationAState);
                }
                default: {
                    Log.d(TAG, "不能解析指令");
                    break;
                }

            }
            return true;
        }

        @Override
        public String getName() {
            return "InitialState";
        }
    }

    public class OperationAState extends State {
        private static final String TAG = "OperationAState";

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case CMD_OPERATION_A: {
                    Log.d(TAG, "模拟operationA");
                    Log.d(TAG, "当前的线程是" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "模拟operation A finished");
                    Log.d(TAG, "将线程状态切换到模拟operation B状态");
                    transitionTo(mOperationBState);
                }
                break;

                default:
                    break;
            }
            return true;
        }

        @Override
        public String getName() {
            return "OperationAState";
        }
    }

    public class OperationBState extends State {
        private static final String TAG = "OperationBState";

        @Override
        public boolean processMessage(Message msg) {
            switch (msg.what) {
                case CMD_OPERATION_B: {
                    Log.d(TAG, "模拟Operation B");
                    Log.d(TAG, "当前的线程是" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "模拟Operation B完成");
                    Log.d(TAG, "将线程状态切换到初始状态");
                    transitionTo(mInitialState);
                    break;
                }
                default:
                    break;
            }
            return true;
        }

        @Override
        public String getName() {
            return "OperationBState";
        }
    }
}
