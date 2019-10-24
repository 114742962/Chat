import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
* @Title: ChatServer.java
* @Package 
* @Description: 聊天程序服务端
* @author Administrator
* @date 2019年10月23日
* @version V1.0
*/

/**
* Simple to Introduction  
 * @ProjectName:  [Chat0.2] 
 * @Package:      [.ChatServer.java]  
 * @ClassName:    [ChatServer]   
 * @Description:  [聊天程序服务端]   
 * @Author:       [桂亚君]   
 * @CreateDate:   [2019年10月23日 下午9:11:19]   
 * @UpdateUser:   [桂亚君]   
 * @UpdateDate:   [2019年10月23日 下午9:11:19]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
*/
public class ChatServer {

    /**
    * @Title: main
    * @Description: 
    * @param @param args    参数 
    * @return void    返回类型
    * @throws
    */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ServerSocket chatServerSocket = null;
        Socket chatSocket = null;
        
        try {
            while (true) {
                boolean isConnect = false;
                chatServerSocket = new ServerSocket(8888);
                chatSocket = chatServerSocket.accept();
                System.out.println("A client connect!");
                isConnect = true;
                DataInputStream dis = new DataInputStream(chatSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(chatSocket.getOutputStream());
                
                while (isConnect) {
                    System.out.println("Gui:  " + dis.readUTF());
                }
                
                dis.close();
                dos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
