package org.example;


// Importamos clases para entrada/salida y red
import java.io.*; // BufferedReader, InputStreamReader, PrintWriter
import java.net.*; // Socket
import java.util.Scanner; // Para leer datos desde teclado


public class Cliente {
    public static void main(String[] args) {


        /*
         * try-with-resources:
         * T0do lo que se declara dentro del try se cerrará automáticamente
         * cuando termine el bloque (socket, streams, scanner).
         */
        try (Socket socket = new Socket("localhost", 12345); // Conecta con el servidor en localhost:12345
             BufferedReader entrada = new BufferedReader(
                     new InputStreamReader(socket.getInputStream())); // Flujo de entrada desde el servidor
             PrintWriter salida = new PrintWriter(
                     socket.getOutputStream(), true); // Flujo de salida al servidor (true = autoFlush)
             Scanner sc = new Scanner(System.in)) { // Para leer datos del usuario


            System.out.println("Conexion establecida con el servidor.");


            int continuar = 1; // Variable de control para salir del programa


        // Bucle principal del cliente
            while (continuar != 0) {


        // Pedimos el tipo de operación
                System.out.print("Introduce tipo de operacion: 1-Suma, 2-Resta, 3-Multiplicacion, 4-Division: ");
                int op = sc.nextInt();


        // Pedimos el primer número
                System.out.print("Introduce primer valor: ");
                double n1 = sc.nextDouble();


        // Pedimos el segundo número
                System.out.print("Introduce segundo valor: ");
                double n2 = sc.nextDouble();


                /*
                 * Construimos el mensaje que se enviará al servidor
                 * Formato: "op#n1#n2"
                 * Ejemplo: "1#5.0#3.0"
                 */
                String mensaje = op + "#" + n1 + "#" + n2;


        // Enviamos el mensaje al servidor
                salida.println(mensaje);


        // Leemos la respuesta del servidor
                String respuesta = entrada.readLine();


        // Mostramos el resultado quitando el prefijo "RESULTADO:"
                System.out.println("El resultado es: " + respuesta.replace("RESULTADO:", ""));


                System.out.println("Para continuar, introduce un numero que no sea 0.");
                System.out.println("Para salir, escribe 0.");
                continuar = sc.nextInt();
                if (continuar == 0) {
                    salida.println("0"); // Mensaje especial para cerrar conexión
                }
            }


        } catch (IOException e) {
// Captura errores de red o de entrada/salida
            System.out.println("Error en el cliente: " + e.getMessage());
        }


        System.out.println("Cliente desconectado.");
    }
}