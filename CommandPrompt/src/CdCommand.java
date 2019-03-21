import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class CdCommand {
	String directorioActual = "";
	public String directorioActual(File directorio) {
		try {
			directorioActual = directorio.getCanonicalPath();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return directorioActual;
	}
	public void mkdir(String nombre, File directorio) throws IOException {
		File miDir = new File(directorioActual(directorio)+"/"+nombre);
		miDir.mkdir();
		System.out.println("asd");
	}
	public void mkfile(String nombre, String contenido, File directorio) throws IOException {
		File miDir = new File(directorioActual(directorio)+"/"+nombre);
		miDir.createNewFile();
		FileWriter fw = new FileWriter (miDir);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(contenido);
		bw.close();
	}
	public void cat(String nombre, File directorio) throws IOException {
		File miDir = new File(directorioActual(directorio)+"/"+nombre);
		FileReader fr = new FileReader(miDir);
		BufferedReader br = new BufferedReader(fr);
		String linea;
		while((linea=br.readLine())!=null) {
			System.out.println(linea);
		}
	}
	public void top(String nombre, File directorio, int numLineas) throws IOException {
		File miDir = new File(directorioActual(directorio)+"/"+nombre);
		FileReader fr = new FileReader(miDir);
		BufferedReader br = new BufferedReader(fr);
		int contador = 0;
		String linea;
		while((linea=br.readLine())!=null && contador<numLineas) {
			System.out.println(linea);
			contador++;
		}
	}
	public void write(String nombre, File directorio, String contenido) throws IOException {
		File miDir = new File(directorioActual(directorio)+"/"+nombre);
		miDir.createNewFile();
		FileWriter fw = new FileWriter (miDir, true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(contenido);
		bw.close();
	}
	public File moverDirectorio(String nombre, File directorio) throws Exception {
		File miDir = new File(directorioActual(directorio));
		File nuevoDirec = new File(miDir +"/"+ nombre);
		if(nuevoDirec.exists()) {
			return nuevoDirec;
		}else {
			throw new Exception("No existe el directorio");
		}
	}
	public File moverAbsoluto(String nombre) throws Exception {
		File miDir = new File(nombre);
		if(miDir.exists()) {
			return miDir;
		}else {
			throw new Exception("No existe el directorio");
		}
	}
}
