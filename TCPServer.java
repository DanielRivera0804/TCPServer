import java.io.*;
import java.net.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TCPServer {
	
	public static void main(String argv[]) throws Exception
	{
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
			
			String cadenaHistorial = SolicitudHistorial();
			System.out.println("CADENAHISTORIAL: "+ cadenaHistorial);
			if(comando.equals("actualizar"))
				outToClient.writeBytes(clientSentences[0]+ "#" + cadenaHistorial);
			else if(comando.equals("enviar"))
				actualizarHistorial(clientSentences[0]);
			
			System.out.println("Received: " + clientSentences[1]);	
			capitalizedSentence = clientSentence.toUpperCase() + '\n';    
			outToClient.writeBytes(capitalizedSentence);
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
			System.out.println(cadena);
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
