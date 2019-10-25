import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
public class ChatServer extends Frame{
    /** 聊天输入框 */
    TextField textField = new TextField();
    /** 聊天信息显示框 */
    TextArea textArea = new TextArea();
    /** 聊天服务端 */
    ServerSocket chatServerSocket = null;
    /** 聊天服务端接受的Socket */
    Socket chatSocket = null;
    /** 输入字符流 */
    DataInputStream dis = null;
    /** 输出字符流 */
    DataOutputStream dos = null;
    /** 开始聊天 */
    boolean start = false;
    /**
    * @Fields field:field:{todo}(用一句话描述这个变量表示什么)
    */
    private static final long serialVersionUID = 1L;
    
    /**
    * @Title: main
    * @Description: 
    * @param @param args    参数 
    * @return void    返回类型
    * @throws
    */
    public static void main(String[] args) {
        ChatServer cs = new ChatServer();
        cs.launch();
    }
    
    /**
    * @Title: launch
    * @Description: (客户端初始化时加载的界面元素)
    * @param     参数 
    * @return void    返回类型
    * @throws
     */
    public void launch() {
        setLocation(600, 100);
        setTitle("TalkServer");
        setBackground(Color.WHITE);
        textField.setBackground(Color.BLUE);
        textArea.setBackground(Color.BLUE);
        add(textField, BorderLayout.SOUTH);
        add(textArea, BorderLayout.NORTH);
        setVisible(true);
        
        pack();
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                endServer();
                System.exit(0);
            }
        });
        
        textField.addKeyListener(new KeyMoniter());
        startServer();

    }
    
    /**
    * @Title: startServer
    * @Description: 服务端消息接收处理逻辑
    * @param     参数 
    * @return void    返回类型
    * @throws
     */
    public void startServer() {
        try {
            chatServerSocket = new ServerSocket(8888);
            start = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {
            chatSocket = chatServerSocket.accept();
            System.out.println("A client connect!");
            boolean isConnect = true;
            while (start) {
                dis = new DataInputStream(chatSocket.getInputStream());
                
                while (isConnect) {
                    String words = dis.readUTF();
                    textArea.append("Gui:\n" + "  " + words + "\n");
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
    * @Title: endServer
    * @Description: 关闭字符流和连接
    * @param     参数 
    * @return void    返回类型
    * @throws
     */
    public void endServer() {
        try {
            dis.close();
            dos.close();
            chatSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Simple to Introduction  
     * @ProjectName:  [Chat0.3] 
     * @Package:      [.ChatClient.java]  
     * @ClassName:    [TextKeyListener]   
     * @Description:  [监听回车键输出信息]   
     * @Author:       [桂亚君]   
     * @CreateDate:   [2019年10月24日 下午8:58:49]   
     * @UpdateUser:   [桂亚君]   
     * @UpdateDate:   [2019年10月24日 下午8:58:49]   
     * @UpdateRemark: [说明本次修改内容]  
     * @Version:      [v1.0]
     */
    private class KeyMoniter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String newWords = textField.getText().trim();
                textArea.append("Server:\n" + "  " + newWords + "\n");
                textField.setText("");
                
                try {
                    dos = new DataOutputStream(chatSocket.getOutputStream());
                    dos.writeUTF(newWords);
                    dos.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
