package game.hw.wolfk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import game.hw.trans.TcpClient;
import game.hw.trans.TcpServer;
import game.hw.trans.UdpClient;
import game.hw.trans.UdpServer;

public class MainActivity extends AppCompatActivity {
    int mTCPPort = 9998;
    int mUdpPort = 9997;

    private TcpServer mTcpServer;
    private TcpClient mTcpClient;
    private UdpServer mUdpServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTcpServer = new TcpServer(mTCPPort);
        mTcpServer.startServer();

        mUdpServer = new UdpServer(mUdpPort);
        mUdpServer.startServer();

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
                    mTcpClient.sendData("Test for tcp!!!");
                    break;
                case R.id.btn_udp_send:
                    UdpClient.sendData("127.0.0.1", mUdpPort, "Test for udp!!!");
                    break;
            }
        }
    };
}
