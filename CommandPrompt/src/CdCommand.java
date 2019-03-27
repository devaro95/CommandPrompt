import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class CdCommand {

	public String directorioActual(File directorio) {
		String directorioActual = "";
		try {
			directorioActual = directorio.getCanonicalPath();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return directorioActual;
	}
	public void help() {
		System.out.println("cd:\n  -cd : Mostrar directorio actual. \n  "
				+ "-cd : Acceder al directorio padre.\n  "
				+ "-cd <nombre directorio>: Acceder a un directorio \n  "
				+ "-cd <C:\\Nombre_Directorio>: Accedee a una ruta absoluta del sistema.\n"
				+ "mkdir <nombre_directorio>: Creae directorio en la ruta actual.\n"
				+ "cat <nombre_fichero> : Mostrar contenido de un fichero a través del terminal.\n"
				+ "top <numero_lineas> <nombre_fichero>: Mostrar las primeras lineas de un fichero.\n"
				+ "mkfile <nombre_fichero> <texto>: Crear un nuevo fichero.\n"
				+ "write <nombre_fichero> <texto>: Añadir texto a un fichero existente.\n"
				+ "info <nombre_archivo_o_directorio> este comando imprimirá los datos básicos de un archivo\n" + 
				"o directorio: su nombre, nombre de la carpeta padre, el tamaño del archivo o la carpeta.\n"
				+ "dir: Lista los archivos contenidos en el path actual.\n"
				+ "delete <nombre_archivo_o_directorio>: Borra un archivo o una carpeta, en caso de ser una\n" + 
				"carpeta borra todos los archivos que la contienen.");
	}
	public void mkdir(String nombre, File directorio) throws IOException {
		File miDir = new File(directorioActual(directorio)+"/"+nombre);
		miDir.mkdir();
		System.out.println("Se ha creado el directorio '" + nombre + "'");
	}
	public void mkfile(String nombre, String contenido, File directorio) throws IOException {
		File miDir = new File(directorioActual(directorio)+"/"+nombre);
		miDir.createNewFile();
		FileWriter fw = new FileWriter (miDir);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(contenido);
		bw.close();
	}
	public void cat(String nombre, File directorio) throws Exception {
		File miDir = new File(directorioActual(directorio)+"/"+nombre);
		if(miDir.exists() && miDir.isFile()){
			FileReader fr = new FileReader(miDir);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			while((linea=br.readLine())!=null) {
				System.out.println(linea);
			}
			br.close();
		}else {
			System.out.println("No es un fichero o no existe");
		}
	}
	public void top(String nombre, File directorio, int numLineas) throws Exception {
		File miDir = new File(directorioActual(directorio)+"/"+nombre);
		if(miDir.exists() && miDir.isFile()){
			FileReader fr = new FileReader(miDir);
			BufferedReader br = new BufferedReader(fr);
			int contador = 0;
			String linea;
			while((linea=br.readLine())!=null && contador<numLineas) {
				System.out.println(linea);
				contador++;
			}
			br.close();
		}else {
			System.out.println("No es un fichero o no existe");
		}
	}
	public void write(String nombre, File directorio, String contenido) throws IOException {
		File miDir = new File(directorioActual(directorio)+"/"+nombre);
		if(miDir.isFile() && miDir.exists()) {
			FileWriter fw = new FileWriter (miDir, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(contenido);
			bw.close();
		}else {
			System.out.println("No existe o es un directorio");
		}
	}
	public File moverDirectorio(String nombre, File directorio) throws Exception {
		File miDir = new File(directorioActual(directorio));
		File nuevoDirec = new File(miDir +"/"+ nombre);
		if(nuevoDirec.exists() && nuevoDirec.isDirectory()) {
			return nuevoDirec;
		}else {
			System.out.println("No existe el directorio o no es un directorio");
			return miDir;
		}
	}
	public File moverPadre(File directorio) {
		if(directorio.getParent() == null) {
			return directorio;
		}else {
			File miDir = new File(directorio.getParent());
			return miDir;
		}
	}
	public File moverAbsoluto(String nombre, File directorio) throws Exception {
		File miDir = new File(nombre);
		if(miDir.exists() && miDir.isDirectory()) {
			return miDir;
		}else {
			System.out.println("No existe el directorio o no es un directorio");
			return directorio;
		}
	}
	
	public void delete(String nombre, File directorio) throws Exception {
		File f = new File(directorio.toString() + "/" + nombre);
		if(f.isFile()) {
			if (f.delete())
			 System.out.println("El fichero " + f.toString() + " ha sido borrado correctamente");
			else
			 System.out.println("El fichero " + f.toString() + " no se ha podido borrar");
		}else if(f.isDirectory()){
			borrarDirectorio(f);
			if (f.delete())
			  System.out.println("El directorio '" + f.toString() + "' ha sido borrado correctamente.");
			else
			  System.out.println("El directorio '" + f.toString() + "' no se ha podido borrar.");
		}
	}
	public void listarDirectorioActual(File directorio) {
		String[] ficheros = directorio.list();
	    if (ficheros == null)
	        System.out.println("No hay ficheros en el directorio especificado");
	      else { 
	        for (int x=0;x<ficheros.length;x++)
	          System.out.println(ficheros[x]);
	    }
	}
	public void listarDirectorio(String nombre, File directorio) {
		File dir = new File(directorio + "/" + nombre);
		String[] ficheros = dir.list();
	    if (ficheros == null)
	        System.out.println("No hay ficheros en el directorio especificado");
	      else { 
	        for (int x=0;x<ficheros.length;x++)
	          System.out.println(ficheros[x]);
	    }
	}
	public void listarDirectorioAbsoluto(String nombre) {
		File dir = new File(nombre);
		String[] ficheros = dir.list();
	    if (ficheros == null)
	        System.out.println("No hay ficheros en el directorio especificado");
	      else { 
	        for (int x=0;x<ficheros.length;x++)
	          System.out.println(ficheros[x]);
	    }
	}
	public void borrarDirectorio(File directorio) throws Exception {
		 File[] ficheros = directorio.listFiles();
		 for (int x=0;x<ficheros.length;x++){
			 if (ficheros[x].isDirectory()) {
				  borrarDirectorio(ficheros[x]);
				}
			 ficheros[x].delete();
		 }
	}
	public void info(String nombre, File directorio) {
		File dir = new File(directorio + "/" +  nombre);
		if(dir.isFile()) {
			System.out.println("Nombre directorio: " + dir.getName());
			System.out.println("Nombre directorio padre: " + dir.getParent());
			System.out.println("Tamaño: " + formatear(dir.length()));
		}else if(dir.isDirectory()){
			System.out.println("Nombre directorio: " + dir.getName());
			System.out.println("Nombre directorio padre: " + dir.getParent());
			System.out.println("Tamaño: " + formatear(infoDirectory(dir)));
		}else {
			System.out.println(dir.getName() + ": No existe el archivo o el directorio");
		}
	}
	public float infoDirectory(File directorio) {
		String[] listado = directorio.list();
		float tamano = 0;
		for(int i=0; i<listado.length; i++) {
			File dir = new File(directorio + "/" + listado[i]);
			if(dir.isDirectory()){
				tamano += infoDirectory(dir);
			}else {
				tamano += dir.length();
			}
		}
		return tamano;
	}
	public String formatear(float tamano) {
		String cadena;
		DecimalFormat df = new DecimalFormat("#.00");
		if(tamano>1024000000)
			cadena = df.format(tamano/1024000000) + " Gb";
		else if(tamano>1024000)
			cadena = df.format(tamano/1024000) + " Mb";
		else if(tamano>1024)
			cadena = df.format(tamano/1024) + " Kb";
		else
			cadena = df.format(tamano) + " bytes";
		return cadena;
	}
}
