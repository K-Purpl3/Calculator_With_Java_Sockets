package org.example;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        final int PUERTO = 12345;

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor esperando conexiones...");

            while (true) {
                Socket socketCliente = servidor.accept();
                System.out.println("Nuevo cliente conectado");

                //crear un hilo por cada cliente
                Thread hilo = new Thread(new HiloServidor(socketCliente));
                hilo.start();
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}
