package com.sandrarujas.tarea3dwessandrarujas.vista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Ejemplar;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Mensaje;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Persona;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;
import com.sandrarujas.tarea3dwessandrarujas.servicios.Controlador;

@Component
public class ViveroFachadaPersonal {

	/*El @Lazy significa que el controlador no se inicializa hasta que se utiliza
	 * no al empezar el programa. El @Autowired inyecta unas dependencias de un objeto externo.
	 */
    @Autowired
    @Lazy
    private Controlador controlador;

    @Autowired
    @Lazy
    private ViveroFachadaAdmin viveroFachadaAdmin;

    @Autowired
    @Lazy
    private ViveroFachadaInvitado viveroFachadaInvitado;

    Scanner sc = new Scanner(System.in);

    
    // ----------------------------- MENU PERSONAL -------------------------------
    public void menuPersonal() {
        int opcion = 0;
        do {
            System.out.println("------¡BIENVENIDO AL MENÚ DEL PERSONAL!------");
            System.out.println("- - - - - - - - - - - - - - - -");
            System.out.println("Por favor, seleccione una opción:");
            System.out.println("1. Ver plantas.");
            System.out.println("2. Gestión de ejemplares.");
            System.out.println("3. Gestión de mensajes.");
            System.out.println("4. Cerrar sesión.");
            System.out.println("- - - - - - - - - - - - - - - - - ");
            try {
                opcion = sc.nextInt();
                if (opcion < 1 || opcion > 4) {
                    System.err.println("Opción incorrecta.");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        ArrayList<Planta> plantas = (ArrayList<Planta>) controlador.getServiciosPlanta().verPlantas(); 
                        if (plantas == null || plantas.isEmpty()) {
                            System.err.println("La base de datos de plantas está vacía");
                            return;
                        }
                        System.out.println("LISTADO PLANTAS: ");
                        System.out.println();
                        for (Planta p : plantas) {
                            System.out.println(p);
                        }
                        break;
                    case 2:
                        menuPersonalEjemplares();
                        break;
                    case 3:
                        menuPersonalMensajes();
                        break;
                    case 4:
                        controlador.cerrarSesion();
                        return;
                }
            } catch (InputMismatchException e) {
                System.err.println("Debe escribir un número.");
                sc.nextLine();
                opcion = 0;
            }
        } while (opcion != 4);
    }

    
    // ------------------------------------------ MENU PERSONAL EJEMPLARES ----------------------------------------
    public void menuPersonalEjemplares() {
        int opcion = 0;
        do {
        	System.out.println("¡HAS ENTRADO AL MENU DE LOS EJEMPLARES!");
            System.out.println("- - - - - - - - - - - - - -");
            System.out.println("Por favor, seleccione una opción:");
            System.out.println("1. Registrar nuevo ejemplar.");
            System.out.println("2. Filtrar ejemplares por tipo de planta.");
            System.out.println("3. Ver mensajes de un ejemplar.");
            System.out.println("4. Volver al menú principal.");
            System.out.println("- - - - - - - - - - - - - - -");
            try {
                opcion = sc.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.err.println("Opción incorrecta.");
                    continue;
                }
                switch (opcion) {
                    case 1:
                	    sc.nextLine();
                	    Ejemplar ejemplar = null;
                	    Mensaje mensaje = null;
                	    boolean correcto = false;   
                	    do {
                	        try {
                	        	ArrayList<Planta> plantas = (ArrayList<Planta>) controlador.getServiciosPlanta().verPlantas(); 
                                if (plantas == null || plantas.isEmpty()) {
                                    System.err.println("No hay plantas añadidas en la BBDD.");
                                    return;
                                }
                                System.out.println("PLANTAS: ");
                                System.out.println();
                                for (Planta planta : plantas) {
                                    System.out.println(planta.getCodigo());
                                    System.out.println();
                                }
                	            System.out.print("Código de la planta del ejemplar: ");
                	            String codigoPlanta = sc.nextLine().trim().toUpperCase();
                	            boolean codigoValido = controlador.getServiciosPlanta().validarCodigoPlanta(codigoPlanta);
                	            boolean plantaExiste = controlador.getServiciosPlanta().codigoExistente(codigoPlanta);
                	            if (!codigoValido) {
                	                System.err.println("El código no es válido.");
                	                continue;
                	            }
                	            if (!plantaExiste) {
                	                System.err.println("No existe una planta con ese codigo.");
                	                continue;
                	            }
                	            Planta planta = controlador.getServiciosPlanta().buscarPorCodigo(codigoPlanta);
                	            ejemplar = new Ejemplar();
                	            ejemplar.setPlanta(planta);
                	            ejemplar.setNombre(codigoPlanta);
                	            controlador.getServiciosEjemplar().insertar(ejemplar);
                	            ejemplar.setNombre(ejemplar.getPlanta().getCodigo() + "_" + ejemplar.getId());
                	            controlador.getServiciosEjemplar().cambiarNombre(ejemplar.getId(), ejemplar.getNombre());
                	            System.out.println("Ejemplar insertado con ID: " + ejemplar.getId());
                	            String mensajeTexto = "Añadido el ejemplar " + ejemplar.getNombre();
                	            LocalDateTime fechaHora = LocalDateTime.now();
                	            String usuarioAutenticado = controlador.obtenerUsuarioConectado();
                	            Persona persona = controlador.getServiciosPersona().buscarPorNombre(usuarioAutenticado);
                	            if (persona == null) {
                	                System.err.println("No se ha encontrado la persona conectada");
                	            } else {
                	                mensaje = new Mensaje(fechaHora, mensajeTexto, persona, ejemplar);
                	                controlador.getServiciosMensaje().addMensaje(mensaje);
                	                System.out.println("Ha sido añadido un mensaje junto con la creación del ejemplar.");
                	            }
                	            correcto = true;
                	        } catch (Exception ex) {
                	            System.err.println("Fallo al crear el ejemplar: " + ex.getMessage());
                	            correcto = false;
                	        }
                	    } while (!correcto);
                        break;
                    case 2:
                    	ArrayList<Planta> plantas = (ArrayList<Planta>) controlador.getServiciosPlanta().verPlantas(); 
                        if (plantas == null || plantas.isEmpty()) {
                            System.err.println("No hay plantas añadidas en la BBDD.");
                            return;
                        }
                        System.out.println("LISTADO PLANTAS: ");
                        System.out.println();
                        for (Planta planta : plantas) {
                            System.out.println(planta);
                        }
                        
                    	try {
                            System.out.print("Introduce el código de la planta para ver los ejemplares: ");
                            sc.nextLine();
                            String codigo = sc.nextLine().trim().toUpperCase();
                            boolean existe = controlador.getServiciosPlanta().codigoExistente(codigo);
                            if (existe) {
                                ArrayList<Ejemplar> ejemplares = controlador.getServiciosEjemplar().ejemplaresPorTipoPlanta(codigo);
                                if (ejemplares.isEmpty()) {
                                    System.err.println("No hay ejemplares para la planta con código: " + codigo);
                                } else {
                                    System.out.println("Ejemplares con el código " + codigo + ":");
                                    for (Ejemplar ej : ejemplares) {
                                        System.out.println(ej.toString());
                                    }
                                }
                            } else {
                                System.err.println("No se encontró ninguna planta con el código: " + codigo);
                            }
                        } catch (Exception e) {
                            System.err.println("Error al buscar los ejemplares: " + e.getMessage());
                        }                        
                    	break;
                    case 3:
                    	ArrayList<Ejemplar> ejemplares = (ArrayList<Ejemplar>) controlador.getServiciosEjemplar().verEjemplares(); 
                        if (ejemplares == null || ejemplares.isEmpty()) {
                            System.err.println("No hay ejemplares añadidos en la BBDD.");
                            return;
                        }
                        System.out.println("EJEMPLARES: ");
                        System.out.println();
                        for (Ejemplar e : ejemplares) {
                            System.out.println(e);
                        }
                        
                    	System.out.print("Introduce el ID de un ejemplar para ver sus mensajes: ");
                		try {
                			long idEjemplar = sc.nextLong();
                			ArrayList<Mensaje> mensajes = controlador.getServiciosMensaje().buscarMensajesPorEjemplar(idEjemplar);
                			if (mensajes.isEmpty()) {
                				System.err.println("No se encontraron mensajes para el ejemplar");
                			} else {
                				System.out.println("Mensajes del ejemplar con ID: " + idEjemplar + ":");
                				System.out.println();
                				for (Mensaje m : mensajes) {
                					System.out.println(m);
                				}
                			}
                		} catch (Exception e) {
                			System.err.println("Error al buscar los mensajes: " + e.getMessage());
                		}
                        break;
                    case 4:
                        return;
                }
            } catch (InputMismatchException e) {
                System.err.println("Debe escribir un número.");
                sc.nextLine();
                opcion = 0;
            }
        } while (opcion != 4);
    }

    // ----------------------------------------- MENU PERSONAL MENSAJES -----------------------------------------
    public void menuPersonalMensajes() {
        int opcion = 0;
        do {
        	System.out.println("¡HAS ENTRADO AL MENU DE LOS MENSAJES!");
            System.out.println(" - - - - - - - - - - - - - - - -");
            System.out.println("Por favor, selecciona una opción:");
            System.out.println("1. Nuevo mensaje.");
            System.out.println("2. Ver todos los mensajes.");
            System.out.println("3. Ver mensajes por persona.");
            System.out.println("4. Ver mensajes por rango de fechas.");
            System.out.println("5. Ver mensajes por tipo de planta.");
            System.out.println("6. Volver al menú principal.");
            System.out.println(" - - - - - - - - - - - - - - - -");
            try {
                opcion = sc.nextInt();
                if (opcion < 1 || opcion > 6) {
                    System.err.println("Opción incorrecta.");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        boolean correcto = false;
                        do {
                            try {
                                ArrayList<Ejemplar> ejemplares = (ArrayList<Ejemplar>) controlador.getServiciosEjemplar().verEjemplares();
                                if (ejemplares == null || ejemplares.isEmpty()) {
                                    System.err.println("No hay ejemplares en la BBDD.");
                                    return;
                                }
                                System.out.println("LISTADO DE EJEMPLARES: ");
                                for (Ejemplar e : ejemplares) {
                                    System.out.println(e);
                                }
                                
                                System.out.println();
                                System.out.print("Introduce el ID del ejemplar: ");
                                int idEjemplar = sc.nextInt();
                                sc.nextLine();
                                Ejemplar ejemplar = controlador.getServiciosEjemplar().buscarPorID((long) idEjemplar);
                                if (ejemplar == null) {
                                    System.err.println("No hay ejemplar con ese ID.");
                                } else {
                                    System.out.println("Introduce el mensaje: ");
                                    String mensajeTexto = sc.nextLine().trim();
                                    if (mensajeTexto.isEmpty()) {
                                        System.err.println("El mensaje no puede estar vacío.");
                                    } else if (controlador.getServiciosMensaje().validarMensaje(mensajeTexto)) {
                                        String usuarioAutenticado = controlador.obtenerUsuarioConectado();
                                        Persona p = controlador.getServiciosPersona().buscarPorNombre(usuarioAutenticado);
                                        if (p == null) {
                                            System.err.println("No se ha encontrado la persona");
                                        } else {
                                            Mensaje nuevoMensaje = new Mensaje(LocalDateTime.now(), mensajeTexto, p, ejemplar);
                                            controlador.getServiciosMensaje().addMensaje(nuevoMensaje);
                                            System.out.println("Mensaje añadido.");
                                            correcto = true;
                                        }
                                    } else {
                                        System.err.println("El mensaje no válido.");
                                    }
                                }
                            } catch (InputMismatchException e) {
                                System.err.println("Debe introducir un número válido");
                                sc.nextLine();
                            }
                        } while (!correcto);
                        break;
                    case 2:
                		ArrayList<Mensaje> mensajes = controlador.getServiciosMensaje().verMensajes();
                		if (mensajes == null || mensajes.isEmpty()) {
                			System.err.println("No hay mensajes en la BBDD");
                			return;
                		}
                		System.out.println("LISTADO DE MENSAJES: ");
                		System.out.println();
                		for (Mensaje m : mensajes) {
                			System.out.println(m);
                		}
                        break;
                    case 3:
                    	ArrayList<Persona> personas = (ArrayList<Persona>) controlador.getServiciosPersona().totalPersonas();
    					if (personas == null || personas.isEmpty()) {
    						System.err.println("No hay personas en la BBDD.");
    						return;
    					}
    					System.out.println("LISTADO DE PERSONAS: ");
    					System.out.println();
    					for (Persona persona : personas) {
    						System.out.println(persona);
    					}
                        System.out.print("Introduce el ID de una persona para ver sus mensajes: ");
                        try {
                            long idPersona = sc.nextLong();
                            mensajes = controlador.getServiciosMensaje().buscarMensajesPorPersona(idPersona);
                            if (mensajes.isEmpty()) {
                                System.err.println("No se encontraron mensajes para la persona: " + idPersona);
                            } else {
                                System.out.println("Mensajes:");
                                for (Mensaje m : mensajes) {
                                    System.out.println(m);
                                }
                            }
                        } catch (InputMismatchException e) {
                            System.err.println("Debe introducir un número válido.");
                            sc.nextLine();
                        } catch (Exception e) {
                            System.err.println("Se produjo un error al intentar obtener los mensajes: " + e.getMessage());
                        }
                        break;
                    case 4:
                    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        				LocalDate fecha1 = null;
        				LocalDate fecha2 = null;
        				do {
        					
        					try {
        						
        						sc.nextLine();
        						System.out.println("Introduce la primera fecha para ver los mensajes (01-01-0000): ");
        						String fecha1Str = sc.nextLine();
        						fecha1 = LocalDate.parse(fecha1Str, formatter);
        						
        					} catch (DateTimeParseException e) {
        						System.err.println("Fecha añadida incorrectamente.");
        					}
        					
        				} while (fecha1 == null);
        				
        				do {
        					
        					try {
        						
        						System.out.println("Introduce la segunda fecha para ver los mensajes (01-01-0000): ");
        						String fecha2Str = sc.nextLine();
        						fecha2 = LocalDate.parse(fecha2Str, formatter);
        						if(fecha2.isBefore(fecha1)) {
        							System.err.println("La fecha 2 debe ser mayor a la fecha 1.");
        							fecha2 = null;
        						}
        						
        					} catch (DateTimeParseException e) {
        						
        						System.err.println("Fecha añadida incorrectamente.");
        					}
        					
        				} while (fecha2 == null);
        				
                        mensajes = controlador.getServiciosMensaje().buscarMensajePorFecha(fecha1, fecha2);
                        if (mensajes.isEmpty()) {
                            System.err.println("No se encontraron mensajes en el rango de fechas proporcionado.");
                        } else {
                            System.out.println("Mensajes encontrados:");
                            for (Mensaje m : mensajes) {
                                System.out.println(m);
                            }
                        }
                        break;
                    case 5:
                    	ArrayList<Planta> plantas = (ArrayList<Planta>) controlador.getServiciosPlanta().verPlantas(); 
                        if (plantas == null || plantas.isEmpty()) {
                            System.err.println("No hay plantas añadidas en la BBDD.");
                            return;
                        }
                        System.out.println("LISTADO DE PLANTAS: ");
                        for (Planta planta : plantas) {
                            System.out.println(planta);
                        }
                        sc.nextLine();
                    	System.out.print("Introduce el código de una planta: ");
                        String codigo = sc.nextLine().trim().toUpperCase();
                        try {
                            mensajes = controlador.getServiciosMensaje().buscarMensajesPorPlanta(codigo);
                            if (mensajes.isEmpty()) {
                                System.err.println("No se encontraron mensajes para la planta con código: " + codigo);
                            } else {
                                System.out.println("Mensajes para la planta con el código " + codigo + ":");
                                for (Mensaje m : mensajes) {
                                    System.out.println(m);
                                }
                            }
                        } catch (Exception e) {
                            System.err.println("Se produjo un error al intentar obtener los mensajes: " + e.getMessage());
                        }
                        break;
                    case 6:
                        return;
                }
            } catch (InputMismatchException e) {
                System.err.println("Debe escribir un número.");
                sc.nextLine();
                opcion = 0;
            }
        } while (opcion != 6);
    }

 
 

}