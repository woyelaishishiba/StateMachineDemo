package example.marvel.com.statemachinedemo.StatusMachineContent;

import android.os.Message;

/**
 * State
 *
 * @author j
 * @since 2019/10/20.
 */
public abstract class State {
    public static final int IS_WAITING = 0;
    public static final int GET_LOCATION = 1;
    public static final int WRITING = 2;
    public static final int COMPARING = 3;

    public abstract boolean processMessage(Message msg);

    public abstract String getName();
}
