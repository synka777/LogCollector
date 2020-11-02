package com.yegabebar.logcollector.agent;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class fileClass {
	
	
	public static void createAndCheck(String path) {
		try {
	         File file = new File(path);
	         file.mkdir();
	         file.exists();
	         System.out.println("Ok");
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	}
	
	public static void downloadFile(String fileURL, String fileFullPath) throws IOException {
		HttpURLConnection httpConnection = null;
		try {
			//Lecture de la taille du fichier distant
			URL url = new URL(fileURL);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("HEAD");
			long totalFileSize = httpConnection.getContentLengthLong();
			httpConnection.disconnect();
			
			//V�rification de la taille du fichier de sortie (= fichier local)
			//OutputStream os = new FileOutputStream(fileFullPath, true);
			File downloadedFile = new File(fileFullPath);
			
			//Ajout v�rification sur l'existence du fichier
			if(!downloadedFile.exists()) {
				createAndCheck(fileFullPath);
			}
			
			long existingFileSize = downloadedFile.length();
			//Si la taille du fichier de sortie est plus petite que la taille totale,
			if (existingFileSize < totalFileSize) {
				httpConnection = (HttpURLConnection) url.openConnection();
				//httpConnection.setRequestMethod("GET");
			    httpConnection.setRequestProperty("Range", "bytes=" + existingFileSize + "-" + totalFileSize);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			httpConnection.disconnect();
		}
		
	
	}

}