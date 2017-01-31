package beans;

import java.io.Serializable;

/**
 * FileReasd records.
 * 
 * @author Majdi Ben Fredj, TORKHANI Rami
 * 
 */
//format de réponse de la commande readFile(fichier dechiffré +  le nom du fichier)

public class FileRead implements Serializable{
	private String filePath;
	private String file;
	
	public FileRead(String filePath, String file) {
		this.filePath = filePath;
		this.file = file;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getFile() {
		return file;
	}

	
	public void setFile(String file) {
		this.file = file;
	}

}
