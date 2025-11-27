package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Conexion establecida con el servidor.");

            int continuar = 1;

            while (continuar != 0) {

                System.out.print("Introduce tipo de operación: 1-Suma, 2-Resta, 3-Multiplicación, 4-División: ");
                int op = sc.nextInt();

                System.out.print("Introduce primer valor: ");
                double n1 = sc.nextDouble();

                System.out.print("Introduce segundo valor: ");
                double n2 = sc.nextDouble();

                // enviamos "op#n1#n2"
                String mensaje = op + "#" + n1 + "#" + n2;
                salida.println(mensaje);

                String respuesta = entrada.readLine();
                System.out.println("El resultado es: " + respuesta.replace("RESULTADO:", ""));

                System.out.println("Si quieres hacer otra operación escribe cualquier numero que no sea 0.");
                System.out.println("Para salir, escribe 0.");
                continuar = sc.nextInt();

                if (continuar == 0) {
                    salida.println("0");  //avisamos al servidor
                }
            }

        } catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        }

        System.out.println("Cliente desconectado.");
    }
}
