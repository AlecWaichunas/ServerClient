import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client2 {

	public static void main(String[] args)throws IOException {
Socket s2 = new Socket("localhost", 12252);
		
		PrintWriter pr = new PrintWriter(s2.getOutputStream());
		pr.println("is it working");
		pr.flush();
		
		InputStreamReader in = new InputStreamReader(s2.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		
		String str = bf.readLine();
		System.out.println("Server : "+ str);


	}

}
