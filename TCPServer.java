import java.io.*;
import java.net.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TCPServer implements Runnable {
	
	static String flag;
	
	public static void main(String argv[]) throws Exception
	{		
		flag = "1";
		(new Thread(new TCPServer())).start();
		Thread.sleep(2000);
		flag = "2";
		(new Thread(new TCPServer())).start();
				
	}
	
	public void run(){
		
		if(flag.equals("1")){
			try {
				TCPServer1();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			}		
		}	
		if(flag.equals("2")){
			TCPServer2();
		}
	}

	private static void TCPServer1() throws IOException {
		String clientSentence;
		String capitalizedSentence;		
		// Establece el servidor en el socket 6789
		ServerSocket welcomeSocket = new ServerSocket(6789);
		//Se ejecuta un bucle de escucha/acepta
		while(true)
		{
			Socket connectionSocket = welcomeSocket.accept(); //Espera para aceptar una conexión
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			//En este if se analiza si se solicita al servidorTCP actualizar o escribir
			String clientSentences[] = clientSentence.split(":");
			String comando = clientSentences[1];
			
			String[] comandos = clientSentences[0].split("=");
			String thecomando = comandos[0];
			String cadenaHistorial = SolicitudHistorial();
				if(comando.equals("actualizar"))
					outToClient.writeBytes(clientSentences[0]+ "#" + cadenaHistorial);
				else if(comando.equals("enviar") && (thecomando.equals("message")))
					actualizarHistorial(clientSentences[0]);
	
			System.out.println("Received: " + clientSentences[1]);	
			capitalizedSentence = clientSentence.toUpperCase() + '\n';    
			outToClient.writeBytes(capitalizedSentence);
		}
		
	}
	
	private static void TCPServer2() {
		ServerSocket server;
		 Socket connection;
		 
		DataOutputStream output;
		 BufferedInputStream bis;
		 BufferedOutputStream bos;
		 
		byte[] receivedData;
		 int in;
		 String file;
		 
		try{
		 //Servidor Socket en el puerto 5000
		 server = new ServerSocket( 6788 );
		 while ( true ) {
		 //Aceptar conexiones
		 connection = server.accept();
		 //Buffer de 1024 bytes
		 receivedData = new byte[1024];
		 bis = new BufferedInputStream(connection.getInputStream());
		 DataInputStream dis=new DataInputStream(connection.getInputStream());
		 //Recibimos el nombre del fichero
		 file = dis.readUTF();
		 file = file.substring(file.indexOf('\\')+1,file.length());
		 //Para guardar fichero recibido
		 bos = new BufferedOutputStream(new FileOutputStream(file));
		 while ((in = bis.read(receivedData)) != -1){
		 bos.write(receivedData,0,in);
		 }
		 bos.close();
		 dis.close();
		 }
		 }catch (Exception e ) {
		 System.err.println(e);
		 }
		
	}


	private static void actualizarHistorial(String query) throws IOException {
		
		System.out.println("ACTUALIZAR HISTORIAL");
		String[] s = query.split("=");
		String[] datos = s[1].split("\\+");
		String comando = datos[0];
		String ip = datos[1];
		String mensaje = datos[2];
		String ip_origen = datos[3];
		
		File f = new File("Historial.txt");
		
		if( f.exists())
		{
			String cadena ="default";				
			FileWriter w = new FileWriter(f,true);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);		
			wr.append(ip + "!" + "MESALUDO" + " (" + ip_origen + ")" + mensaje + "  ( enviado " +getCurrentTimeStamp() +")\n");
			wr.close();
			bw.close();
		}	
	}

private static String SolicitudHistorial() throws IOException {
		String cadenaHistorial = "";
		String cadena = "default";
		File g = new File("Historial.txt");
		FileReader fr = new FileReader (g);
		BufferedReader br = new BufferedReader(fr);

		
		while( cadena != null)
		{
			cadena = br.readLine();
			if( cadena != null){	
				cadenaHistorial = cadenaHistorial.concat(cadena + "$");
			}
		}		
		return cadenaHistorial;
	}
	
	public static String getCurrentTimeStamp() {
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH;mm;ss").format(Calendar.getInstance().getTime());	
	    return timeStamp;
	}

}
