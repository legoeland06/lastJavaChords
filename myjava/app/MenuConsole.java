package myjava.app;

import java.util.Scanner;

public class MenuConsole {
	public static void main(String args) {
		try (Scanner sn = new Scanner(System.in)) {
			boolean sortir = false;
			int option; // Guardaremos la opcion del usuario
			while (!sortir) {
				System.out.println("1. Opcion 1");
				System.out.println("2. Opcion 2");
				System.out.println("3. Opcion 3");
				System.out.println("4. Salir");
				System.out.println("Escribe una de las opciones");
				option = sn.nextInt();
				switch (option) {
				case 1: {
					Application.prtln("Quelle est la note fondamentale ? ");
					String fondamentale = sn.next();
					
					
					
					break;
				}
				case 2: {

					break;
				}
				case 3: {

					break;
				}
				case 4: {

					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + option);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}