package client;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client implements Runnable {

	private Socket s = null;
	
	public Client(){
		try{
			s = new Socket("localhost", 12461);
			PrintWriter pr = new PrintWriter(s.getOutputStream());
			(new Thread(this)).start();
			ReadClient(pr);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void ReadClient(PrintWriter pr) {
		Scanner scan = new Scanner(System.in);
		while(!s.isClosed()){
			String line = scan.nextLine();
			pr.println(line);
			pr.flush();
		}
		scan.close();
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
				s.close();
			}
			
		}
		
	}

	public static void main(String[] args) {
		new Client();
	}

}
