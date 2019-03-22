import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CMD {

	public static void main (String args[]) throws Exception{
		String current = new java.io.File( "." ).getCanonicalPath();
		File miDir = new File(current);
		String  opcion="", comando[], nombre, contenido;
		int numLineas;
		Scanner teclado = new Scanner(System.in);
		CdCommand cd = new CdCommand();

		String directorioActual;
		while(!opcion.equals("exit")) {
			java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
			String usuario = System.getProperty("user.name");
			System.out.print(usuario + "@" + localMachine.getHostName() + ":" + miDir + " ");
			opcion = teclado.nextLine();
			comando = opcion.split(" ");
			if(comando.length==1 && comando[0].equals("help")) {
				System.out.println("cd:\n  -cd : Mostrar directorio actual. \n  "
						+ "-cd : Acceder al directorio padre.\n  "
						+ "-cd <nombre directorio>: Acceder a un directorio \n  "
						+ "-cd <C:\\Nombre_Directorio>: Accedee a una ruta absoluta del sistema.\n"
						+ "mkdir <nombre_directorio>: Creae directorio en la ruta actual.\n"
						+ "cat <nombre_fichero> : Mostrar contenido de un fichero a través del terminal.\n"
						+ "top <numero_lineas> <nombre_fichero>: Mostrar las primeras lineas de un fichero.\n"
						+ "mkfile <nombre_fichero> <texto>: Crear un nuevo fichero.\n"
						+ "write <nombre_fichero> <texto>: Añadir texto a un fichero existente.\n"
						+ "Dir: Lista los archivos contenidos en el path actual.\n"
						+ "Delete <nombre_archivo_o_directorio>: Borra un archivo o una carpeta, en caso de ser una\n" + 
						"carpeta borra todos los archivos que la contienen.");
			}else if(comando.length==3 && comando[0].equals("mkfile")){
				nombre = comando[1];
				contenido = comando[2];
				cd.mkfile(nombre, contenido, miDir);
			}else if(comando.length==2 && comando[0].equals("mkdir")) {
				nombre = comando[1];
				cd.mkdir(nombre, miDir);;
			}else if(comando.length==2 && comando[0].equals("cat")) {
				nombre = comando[1];
				cd.cat(nombre, miDir);
			}else if(comando.length==3 && comando[0].equals("top")) {
				nombre = comando[1];
				numLineas = Integer.parseInt(comando[2]);
				cd.top(nombre, miDir, numLineas);
			}else if(comando.length>=3 && comando[0].equals("write")) {
				nombre = comando[1];
				contenido = "\n";
				for(int i=2; i<comando.length; i++) {
					contenido += comando[i] + " ";
				}
				cd.write(nombre, miDir, contenido);
			}else if(comando.length==2 && comando[0].equals("cd") && !comando[1].equals("..") && comando[1].startsWith("/")) {
				nombre = comando[1];
				try {
					miDir = cd.moverAbsoluto(nombre);
				} catch (Exception e) {
					System.out.println(e);
				}
			}else if(comando.length==2 && comando[0].equals("cd") && !comando[1].equals("..")) {
				nombre = comando[1];
				try {
					miDir = cd.moverDirectorio(nombre, miDir);
				} catch (Exception e) {
					System.out.println(e);
				}
			}else if(comando.length==2 && comando[0].equals("cd") && comando[1].equals("..")) {
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
			}else if(comando.length==1 && comando[0].equals("cd")) {
				directorioActual = cd.directorioActual(miDir);
				System.out.println(directorioActual);
			}else if(comando.length==1 && comando[0].equals("Dir")) {
				directorioActual = cd.directorioActual(miDir);
				File dir = new File(directorioActual);
				String[] ficheros = dir.list();
			    if (ficheros == null)
			        System.out.println("No hay ficheros en el directorio especificado");
			      else { 
			        for (int x=0;x<ficheros.length;x++)
			          System.out.println(ficheros[x]);
			    }
			}else if(comando.length==2 && comando[0].equals("Dir")) {
				nombre = comando[1];
				directorioActual = cd.directorioActual(miDir);
				File dir = new File(directorioActual + "/" + nombre);
				String[] ficheros = dir.list();
			    if (ficheros == null)
			        System.out.println("No hay ficheros en el directorio especificado");
			      else { 
			        for (int x=0;x<ficheros.length;x++)
			          System.out.println(ficheros[x]);
			    }
			}else if(comando.length==2 && comando[0].equals("Delete")) {
				nombre = comando[1];
				cd.delete(nombre, miDir);
			}else {
				System.out.println("El comando '" + comando[0] + "' no existe");
			}
			
		}
	}
}

