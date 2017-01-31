package tools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * IOStreams is used to manipulate a DataOutputStream object and a DataInputStream object on a socket.
 
 * @author Majdi Ben Fredj
 *
 */
public class IOStreams 
{
	private ObjectOutputStream out;
	private ObjectInputStream in;

	/**
	 * Creation of the input and output streams on the socket
	 * 
	 * @param socket
	 *            the socket used to create the streams
	 */
	public IOStreams(Socket socket) {

		try {
			out = new ObjectOutputStream(socket.getOutputStream());

			in = new ObjectInputStream(socket.getInputStream());

		} catch (IOException ex) {
			System.err.println("Impossible to create the stream");
		}
	}

	/**
	 * @return the DataOutputStream object
	 */
	public ObjectOutputStream getOutputStream()
	{
		return out;
	}

	/**
	 * @return the DataInputStream object
	 */
	public ObjectInputStream getInputStream()
	{
		return in;
	}

	/**
	 * Flush the output stream
	 * @throws IOException
	 */
	public void flushOutStream() throws IOException
	{
		out.flush();
	}

	/**
	 * Close the streams
	 */
	public void close()
	{
		try 
		{
			out.close();
			in.close();

		} catch (IOException ex) {
			Logger.getLogger(IOStreams.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
