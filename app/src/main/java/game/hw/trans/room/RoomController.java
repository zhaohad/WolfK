package game.hw.trans.room;

import java.util.ArrayList;

import game.hw.trans.BaseData;
import game.hw.trans.TcpServer;
import game.hw.trans.UdpServer;

public class RoomController<RoomData extends BaseData, UserData extends BaseData> {
    private ArrayList<UserData> mUsers;
    private UserData mHostUser;
    private int mCntMaxUser;
    private int mCntMinUser;

    private TcpServer mTcpServer;
    private int mTcpPort;
    private UdpServer mUdpServer;
    private int mUdpPort;
    private String mRoomTitle;
    private String mPasswd;

    public RoomController(UserData hostUser, int udpPort, int tcpPort) {
        mHostUser = hostUser;
        mUdpPort = udpPort;
        mTcpPort = tcpPort;
    }

    public void setCntMaxUser(int cntMaxUser) {
        mCntMaxUser = cntMaxUser;
    }

    public void setCntMinUser(int cntMinUser) {
        mCntMinUser = cntMinUser;
    }

    public void createRoom() {
        mTcpServer = new TcpServer(mTcpPort);
        mUdpServer = new UdpServer(mUdpPort);
    }

    public void setRoomTitle(String roomTitle) {
        mRoomTitle = roomTitle;
    }

    public void setPasswd(String passwd) {
        mPasswd = passwd;
    }
}
