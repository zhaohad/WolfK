package game.hw.wolfk;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import game.hw.trans.TcpClient;
import game.hw.trans.TcpServer;
import game.hw.wolfk.data.Person;

public class MainActivity extends AppCompatActivity {
    int mTCPPort = 9998;
    int mUdpPort = 9997;

    private TcpServer mTcpServer;
    private TcpClient mTcpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTcpServer = new TcpServer(mTCPPort);
        mTcpServer.startServer();

        mTcpClient = new TcpClient("127.0.0.1", mTCPPort);
        mTcpClient.init();

        initUi();
    }

    private void initUi() {
        findViewById(R.id.btn_tcp_send).setOnClickListener(mClickListener);
        findViewById(R.id.btn_udp_send).setOnClickListener(mClickListener);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_tcp_send:
                    Person p = new Person();
                    p.avatar = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
                    p.name = "测试传输协议";
                    mTcpClient.sendData(p);
                    break;
                case R.id.btn_udp_send:
                    // dpClient.sendData("127.0.0.1", mUdpPort, "Test for udp!!!");
                    break;
            }
        }
    };
}
