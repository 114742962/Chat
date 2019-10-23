import java.io.BufferedReader;
import java.io.BufferedWriter;
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
            chatServerSocket = new ServerSocket(8888);
            chatSocket = chatServerSocket.accept();
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            InputStreamReader insReader = new InputStreamReader(chatSocket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(insReader);
            OutputStreamWriter outsWriter = new OutputStreamWriter(chatSocket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outsWriter);
            System.out.println("Gui:  " + bufferedReader.readLine());
            String words = inputReader.readLine();
            
            while (!words.equals("bye")) {
                // 如果不加换行符对方读不到信息
                bufferedWriter.write(words+ "\n");
                bufferedWriter.flush();
                System.out.println("Server:  " + words);
                System.out.println("Gui:  " + inputReader.readLine());
                words = bufferedReader.readLine();
            }
            
            chatSocket.close();
            insReader.close();
            bufferedReader.close();
            outsWriter.close();
            bufferedWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
    }
}
