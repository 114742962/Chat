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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    /** 客户端 */
    Socket chatSocket = null;
    InputStreamReader insReader = null;
    BufferedReader bufferedReader = null;
    OutputStreamWriter outsWriter = null;
    BufferedWriter bufferedWriter = null;
    String words = null;
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
    * @Description: TODO(客户端初始化时加载的界面元素)
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
        textField.addKeyListener(new TextKeyListener());
        pack();
        setVisible(true);
        
        try {
            chatSocket = new Socket("127.0.0.1", 8888);
            insReader = new InputStreamReader(chatSocket.getInputStream());
            bufferedReader = new BufferedReader(insReader);
            outsWriter = new OutputStreamWriter(chatSocket.getOutputStream());
            bufferedWriter = new BufferedWriter(outsWriter);
        } catch (IOException ec) {
            ec.printStackTrace();
        }
        
        this.addWindowListener(new WindowAdapter() {
           public void  windowClosing(WindowEvent e) {
               System.exit(0);
           }
        });
    }
    
    private class TextKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                words = textField.getText();
                    
                if (words != null) {
                    textArea.append("Gui:\n" + "  " + words + "\n");
                    textField.setText("");
                    while (words != null) {
                        try {
                            bufferedWriter.write(words + "\n");
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        try {
                            bufferedWriter.flush();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        try {
                            textArea.append("Server:\n" + "  " + bufferedReader.readLine() + "\n");
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                    
                    try {
                        chatSocket.close();
                        insReader.close();
                        bufferedReader.close();
                        outsWriter.close();
                        bufferedWriter.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
            }
        }
    }
}
