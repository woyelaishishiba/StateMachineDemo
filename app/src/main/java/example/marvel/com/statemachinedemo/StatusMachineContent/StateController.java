package example.marvel.com.statemachinedemo.StatusMachineContent;

/**
 * StateController
 *
 * @author j
 * @since 2019/10/20.
 */
public class StateController {
    private static StateMachine stateMachine = new StateMachine("DemoStateMachine");

    public StateController(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    public static void startOperation() {
        stateMachine.getLocation();
    }
}
