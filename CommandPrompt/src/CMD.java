import java.io.File;
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
				cd.help();
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
			}else if(comando.length==2 && comando[0].equals("cd") && !comando[1].equals("..") && (comando[1].startsWith("/") || comando[1].startsWith("C:"))) {
				nombre = comando[1];
				try {
					miDir = cd.moverAbsoluto(nombre, miDir);
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
				miDir = cd.moverPadre(miDir);
			}else if(comando.length==1 && comando[0].equals("cd")) {
				directorioActual = cd.directorioActual(miDir);
				System.out.println(directorioActual);
			}else if(comando.length==1 && comando[0].equals("dir")) {
				cd.listarDirectorioActual(miDir);
			}else if(comando.length==2 && comando[0].equals("dir") && !comando[1].startsWith("/") && !comando[1].startsWith("C:")) {
				nombre = comando[1];
				cd.listarDirectorio(nombre, miDir);
			}else if(comando.length==2 && comando[0].equals("dir") && (comando[1].startsWith("/") || comando[1].startsWith("C:"))) {
				nombre = comando[1];
				cd.listarDirectorioAbsoluto(nombre);
			}else if(comando.length==2 && comando[0].equals("delete")) {
				nombre = comando[1];
				cd.delete(nombre, miDir);
			}else if(comando.length==2 && comando[0].equals("info") && !comando[1].startsWith("/")) {
				nombre = comando[1];
				cd.info(nombre, miDir);
			}else {
				System.out.println("El comando '" + opcion + "' no existe o no has puesto bien los argumentos escribe help para listar los comandos.");
			}
			
		}
		teclado.close();
	}
}

