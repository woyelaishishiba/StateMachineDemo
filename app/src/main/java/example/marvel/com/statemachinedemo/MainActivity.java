package example.marvel.com.statemachinedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.marvel.com.statemachinedemo.StatusMachineContent.StateController;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.runDemoStateMachine)
    Button runDemoStateMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.runDemoStateMachine)
    public void runDemoStateMachine() {
        StateController.startOperation();
    }
}
