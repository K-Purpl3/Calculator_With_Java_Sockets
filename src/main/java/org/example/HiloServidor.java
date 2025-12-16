package org.example;


import java.io.*;
import java.net.*;


/*
 * Esta clase representa un hilo que atiende a UN cliente.
 * Implementa Runnable para poder ejecutarse en un Thread.
 */
public class HiloServidor implements Runnable {


    // Socket asociado al cliente concreto
    private Socket socketCliente;


    // Constructor: recibe el socket del cliente
    public HiloServidor(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }


    @Override
    public void run() {
        /*
         * Creamos los flujos de entrada y salida para comunicarnos
         * con el cliente conectado a este socket.
         */
        try (BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socketCliente.getInputStream()));
             PrintWriter salida = new PrintWriter(
                     socketCliente.getOutputStream(), true)) {


            String mensaje;


            /*
             * El cliente enviará mensajes con el formato:
             * "op#n1#n2"
             * Mientras el cliente no cierre la conexión,
             * readLine() seguirá devolviendo mensajes.
             */
            while ((mensaje = entrada.readLine()) != null) {


// Si el cliente envía "0", quiere cerrar la conexión
                if (mensaje.equalsIgnoreCase("0")) {
                    break;
                }


// Separamos el mensaje por el carácter '#'
                String[] partes = mensaje.split("#");


// Comprobamos que el formato sea correcto
                if (partes.length != 3) {
                    salida.println("ERROR: Formato incorrecto");
                    continue; // Volvemos al inicio del bucle
                }


// Convertimos los datos recibidos
                int op = Integer.parseInt(partes[0]);
                double n1 = Double.parseDouble(partes[1]);
                double n2 = Double.parseDouble(partes[2]);


                double resultado;


// Según la operación, calculamos el resultado
                switch (op) {
                    case 1: // Suma
                        resultado = n1 + n2;
                        break;
                    case 2: // Resta
                        resultado = n1 - n2;
                        break;
                    case 3: // Multiplicación
                        resultado = n1 * n2;
                        break;
                    case 4: // División
                        if (n2 == 0) {
                            salida.println("ERROR: División entre 0");
                            continue;
                        }
                        resultado = n1 / n2;
                        break;
                    default:
                        salida.println("ERROR: Operación no valida");
                        continue;
                }


// Enviamos el resultado al cliente
                salida.println("RESULTADO:" + resultado);
            }


        } catch (Exception e) {
// Error relacionado con este cliente
            System.out.println("Error con cliente: " + e.getMessage());
        } finally {
// Cerramos el socket del cliente
            try {
                socketCliente.close();
            } catch (IOException e) {
            }
            System.out.println("Cliente desconectado");
        }
    }
}