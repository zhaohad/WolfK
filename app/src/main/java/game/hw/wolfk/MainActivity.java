package game.hw.wolfk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import game.hw.trans.TcpClient;
import game.hw.trans.TcpServer;

public class MainActivity extends AppCompatActivity {
    int mPort = 9998;
    private TcpServer mTcpServer;
    private TcpClient mTcpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTcpServer = new TcpServer(mPort);
        mTcpServer.startServer();

        mTcpClient = new TcpClient("127.0.0.1", mPort);
        mTcpClient.init();

        initUi();
    }

    private void initUi() {
        findViewById(R.id.btn_send).setOnClickListener(mClickListener);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_send:
                    mTcpClient.sendData("Test for tcp!!!");
                    break;
            }
        }
    };
}
