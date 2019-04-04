import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client implements Runnable {

	private Socket s;
	
	public client() throws IOException {
		s = new Socket("localhost", 12252);
		PrintWriter pr = new PrintWriter(s.getOutputStream());
		(new Thread(this)).start();
		ReadClient(pr);
	}
	
	public void ReadClient(PrintWriter pr) {
		Scanner scan = new Scanner(System.in);
		while(!s.isClosed()){
			String line = scan.nextLine();
			pr.println(line);
			pr.flush();
		}
	}
	
	public void run() {
		while(!s.isClosed()){
			try {
			InputStreamReader in = new InputStreamReader(s.getInputStream());
			BufferedReader bf = new BufferedReader(in);
			String str = bf.readLine();
			System.out.println(str);
			}catch(IOException e) {
				System.err.println("Could not send message");
			}
			
		}
		
	}

	/*public static void main(String[] args) throws IOException {
		Socket s = new Socket("localhost", 12252);
		//in runnable thread, include scanner
		PrintWriter pr = new PrintWriter(s.getOutputStream());
		pr.println("Hello from Client1");
		pr.flush();
		//while loop Main Thread
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		
		String str = bf.readLine();
		System.out.println(str); //server will send string and tell you where it came from

	}**/
	
	public static void main(String[] args) throws IOException {
	
		client cl1 = new client();
	}

}
