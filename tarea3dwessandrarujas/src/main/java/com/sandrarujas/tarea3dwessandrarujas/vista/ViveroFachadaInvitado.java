package com.sandrarujas.tarea3dwessandrarujas.vista;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;
import com.sandrarujas.tarea3dwessandrarujas.servicios.Controlador;

@Component
public class ViveroFachadaInvitado {

    @Autowired
    private ViveroFachadaAdmin viveroFachadaAdmin;

    @Autowired
    private ViveroFachadaPersonal viveroFachadaPersonal;

    @Autowired
    private Controlador controlador; 

    Scanner sc = new Scanner(System.in);

    
    // --------------------- MENU INVITADO ----------------------------
    public void menuInvitado() {
        int opcion = 0;
        do {
            System.out.println("------GESTIÓN VIVERO------");
            System.out.println(" - - - - - - - - - - - - - - - -");
            System.out.println("Selecciona una opción: ");
            System.out.println("1. Ver plantas");
            System.out.println("2. Login");
            System.out.println("3. Cerrar sesión");
            System.out.println("- - - - - - - - - - - - - - - -");
            try {
                opcion = sc.nextInt();
                switch (opcion) {
                case 1:
                    ArrayList<Planta> plantas = (ArrayList<Planta>) controlador.getServiciosPlanta().verPlantas(); 
                    if (plantas == null || plantas.isEmpty()) {
                        System.err.println("No hay plantas en la BBDD.");
                        return;
                    }
                    System.out.println("Plantas: ");
                    System.out.println();
                    for (Planta p : plantas) {
                        System.out.println(p);
                        System.out.println();
                    }
                    break;
                case 2:
                    sc.nextLine();  
                    System.out.println("Introduce usuario: ");
                    String usuario = sc.nextLine();
                    System.out.println("Introduce contraseña: ");
                    String password = sc.nextLine();
                    try {
                        boolean autenticar = controlador.getServiciosCredencial().autenticar(usuario, password);  
                        if (autenticar) {
                            System.out.println("USUARIO " + usuario);
                            controlador.iniciarSesion(usuario);  
                            if ("admin".equals(usuario) && "admin".equals(password)) {
                                System.out.println("USUARIO admin");
                                viveroFachadaAdmin.menuAdmin(); 
                            } else {
                                viveroFachadaPersonal.menuPersonal(); 
                            }
                        } else {
                            System.err.println("Usuario o contraseña incorrectos.");
                        }
                    } catch (Exception e) {
                        System.err.println("No se ha podido iniciar sesión: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Has salido.");
                    break;
                default:
                    System.err.println("Opción incorrecta");
                }
            } catch (InputMismatchException e) {
                System.err.println("Debe escribir un número.");
                sc.nextLine();
                opcion = 0;
            }
        } while (opcion != 3);
    }

}
