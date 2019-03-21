import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CMD {

	public static void main (String args[]) throws IOException{
		File miDir = new File("/datos/usuarios/alumnos/alvaro.monterocarmena/Escritorio/");
		String  opcion="", comando[], nombre, contenido;
		int numLineas;
		Scanner teclado = new Scanner(System.in);
		CdCommand cd = new CdCommand();

		String directorioActual;
		while(!opcion.equals("exit")) {
			opcion = teclado.nextLine();
			comando = opcion.split(" ");
			if(comando.length==1 && comando[0].equals("help")) {
				opcion = "1";
			}else if(comando.length==3 && comando[0].equals("mkfile")){
				opcion = "2";
			}else if(comando.length==2 && comando[0].equals("mkdir")) {
				opcion = "3";
			}else if(comando.length==2 && comando[0].equals("cat")) {
				opcion = "4";
			}else if(comando.length==3 && comando[0].equals("top")) {
				opcion = "5";
			}else if(comando.length>=3 && comando[0].equals("write")) {
				opcion = "6";
			}else if(comando.length==2 && comando[0].equals("cd") && !comando[1].equals("..") && comando[1].startsWith("/")) {
				opcion = "8";
			}else if(comando.length==2 && comando[0].equals("cd") && !comando[1].equals("..")) {
				opcion = "7";
			}
			switch (opcion) {
			case "1":
				System.out.println("cd:\n  -cd : Mostrar directorio actual. \n  "
						+ "-cd : Acceder al directorio padre.\n  "
						+ "-cd <nombre directorio>: Acceder a un directorio \n  "
						+ "-cd <C:\\Nombre_Directorio>: Accedee a una ruta absoluta del sistema.\n"
						+ "-mkdir <nombre_directorio>: Creae directorio en la ruta actual.\n"
						+ "-cat <nombre_fichero> : Mostrar contenido de un fichero a través del terminal.\n"
						+ "-top <numero_lineas> <nombre_fichero>: Mostrar las primeras lineas de un fichero.\n"
						+ "-mkfile <nombre_fichero> <texto>: Crear un nuevo fichero.\n"
						+ "-write <nombre_fichero> <texto>: Añadir texto a un fichero existente.");
				break;
			case "cd":
				directorioActual = cd.directorioActual(miDir);
				System.out.println(directorioActual);
				break;
			case "cd ..":
				String[] ruta = cd.directorioActual(miDir).split("/");
				String nuevaRuta = "";
				if(ruta.length>=3) {
					for(int i=0; i<ruta.length-1; i++) {
						if(i<ruta.length-2) nuevaRuta += ruta[i]+"/";
						else nuevaRuta += ruta[i];
					}					
				}else {
					nuevaRuta = "/";
				}
				miDir = new File(nuevaRuta);
				System.out.println(miDir.getPath());
				break;
			case "2":
				nombre = comando[1];
				contenido = comando[2];
				cd.mkfile(nombre, contenido, miDir);
				break;
			case "3":
				nombre = comando[1];
				cd.mkdir(nombre, miDir);
			case "4":
				nombre = comando[1];
				cd.cat(nombre, miDir);
			case "5":
				nombre = comando[1];
				numLineas = Integer.parseInt(comando[2]);
				cd.top(nombre, miDir, numLineas);
			case "6":
				nombre = comando[1];
				contenido = "\n";
				for(int i=2; i<comando.length; i++) {
					contenido += comando[i] + " ";
				}
				cd.write(nombre, miDir, contenido);
			case "7":
				nombre = comando[1];
				try {
					miDir = cd.moverDirectorio(nombre, miDir);
				} catch (Exception e) {
					System.out.println(e);
				}
				break;
			case "8":
				nombre = comando[1];
				try {
					miDir = cd.moverAbsoluto(nombre);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}

