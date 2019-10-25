/**
* @Title: TalkClient.java
* @Package 
* @Description: 实现客户端类
* @author Administrator
* @date 2019年10月23日
* @version V1.0
*/
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
* Simple to Introduction  
 * @ProjectName:  [Chat] 
 * @Package:      [.TalkClient.java]  
 * @ClassName:    [TalkClient]   
 * @Description:  [聊天程序的客户端，显示用户交互界面]   
 * @Author:       [桂亚君]   
 * @CreateDate:   [2019年10月23日 下午7:41:37]   
 * @UpdateUser:   [桂亚君]   
 * @UpdateDate:   [2019年10月23日 下午7:41:37]   
 * @UpdateRemark: [说明本次修改内容]  
 * @Version:      [v1.0]
*/
public class ChatClient extends Frame {
    
    /** 聊天输入框 */
    TextField textField = new TextField();
    /** 聊天信息显示框 */
    TextArea textArea = new TextArea();
    /** 聊天客户端 */
    Socket socket = null;
    /** 输入字符流 */
    DataInputStream dis = null;
    /** 输出字符流 */
    DataOutputStream dos = null;
    /** 开始聊天 */
    boolean start = false;
    /**
     * @Fields field:
     */
    private static final long serialVersionUID = 1L;
    
    public static void main(String[] args) {
        ChatClient tc = new ChatClient();
        tc.launchFrame();
    }
    
    /**
    * @Title: launchFrame
    * @Description: (客户端初始化时加载的界面元素)
    * @param     参数 
    * @return void    返回类型
    * @throws
     */
    public void launchFrame() {
        setBounds(100, 100, 800, 600);
        setBackground(Color.white);
        setTitle("TalkClient");
        textField.setBackground(Color.PINK);
        textArea.setBackground(Color.PINK);
        add(textField, BorderLayout.SOUTH);
        add(textArea, BorderLayout.NORTH);
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                disConnect();
                System.exit(0);
            }
        });
        
        textField.addKeyListener(new TextKeyListener());
        
        pack();
        setVisible(true);
        
        connect();
    }
    
    /**
    * @Title: connect
    * @Description: 开启连接和接收字符流
    * @param     参数 
    * @return void    返回类型
    * @throws
     */
    public void connect() {
        try {
            socket = new Socket("127.0.0.1", 8888);
            start = true;
            while (start) {
                dis = new DataInputStream(socket.getInputStream());
                String words = dis.readUTF();
                textArea.append("Server:\n" + "  " + words + "\n");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    /**
    * @Title: disConnect
    * @Description: 关闭连接和字符流
    * @param     参数 
    * @return void    返回类型
    * @throws
     */
    public void disConnect() {
        try {
            dis.close();
            dos.close();
            socket.close();
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
    private class TextKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String newWords = textField.getText().trim();
                textArea.append("Gui:\n" + "  " + newWords + "\n");
                textField.setText("");    
                try {
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(newWords);
                    dos.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

