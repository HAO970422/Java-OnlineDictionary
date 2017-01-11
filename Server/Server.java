package javaHw2;
import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame{

	private JTextArea jta = new JTextArea();
	private Vector<String> sendTo = new Vector<String>();
	private Vector<String> word = new Vector<String>();
	private Vector<String> trans= new Vector<String>();
	private Vector<String> sendFr = new Vector<String>();
	public Server(){
		setLayout(new BorderLayout());
		add(new JScrollPane(jta),BorderLayout.CENTER);
		
		setTitle("Server");
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		try{
			ServerSocket serverSocket = new ServerSocket(8000);
			jta.append("Server Started at "+ new Date()+'\n');
/*			Socket socket = serverSocket.accept();
			
			ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
			DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
*/			
			while (true){
				
			    Socket socket = serverSocket.accept();
			   
				ServerRequest thread = new ServerRequest(socket,jta,sendTo,word,trans,sendFr);
				new Thread(thread).start();
				
			}
		}catch(IOException ex){
			System.err.println(ex);
		} /*catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server();
	}

}
