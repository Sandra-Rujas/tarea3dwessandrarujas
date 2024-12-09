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

import com.sandrarujas.tarea3dwessandrarujas.modelo.Credencial;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Ejemplar;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Mensaje;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Persona;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;
import com.sandrarujas.tarea3dwessandrarujas.servicios.Controlador;

@Component
public class ViveroFachadaAdmin {



    @Autowired
    @Lazy
    private Controlador controlador;

    @Autowired
    @Lazy
    private ViveroFachadaInvitado viveroFachadaInvitado;

    @Autowired
    @Lazy
    private ViveroFachadaPersonal viveroFachadaPersonal;

    Scanner sc = new Scanner(System.in);

    
    // ----------------------------------- MENU ADMIN ----------------------------------
    public void menuAdmin() {
        int opcion = 0;
        do {
            System.out.println("------MENÚ ADMINISTRADOR------");
            System.out.println("- - - - - - - - - - - - - - - - -");
            System.out.println("Selecciona una opción:");
            System.out.println("1. Gestión de plantas");
            System.out.println("2. Gestión de ejemplares");
            System.out.println("3. Gestión de mensajes");
            System.out.println("4. Gestión de personas");
            System.out.println("5. Cerrar sesión.");
            System.out.println("- - - - - - - - - - - - - - - -");
            try {
                opcion = sc.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.err.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        menuAdminPlantas();
                        break;
                    case 2:
                        menuAdminEjemplares();
                        break;
                    case 3:
                        menuAdminMensajes();
                        break;
                    case 4:
                        menuAdminPersonas();
                        break;
                    case 5:
                        controlador.cerrarSesion();
                        return;
                }
            } catch (InputMismatchException e) {
                System.err.println("Debe escribir un número");
                sc.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }

    // --------------------------------- MENU ADMIN PLANTAS -----------------------------------
    public void menuAdminPlantas() {
        int opcion = 0;
        do {
        	System.out.println("MENU PLANTAS");
            System.out.println(" - - - - - - - - - - - - - - - -");
            System.out.println("Selecciona una opción:");
            System.out.println("1. Ver plantas");
            System.out.println("2. Crear nueva planta");
            System.out.println("3. Modificar datos de una planta");
            System.out.println("4. Volver al menú principal");
            System.out.println("- - - - - - - - - - - - - - -");
            try {
                opcion = sc.nextInt();
                if (opcion < 1 || opcion > 4) {
                    System.err.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        ArrayList<Planta> plantas = (ArrayList<Planta>) controlador.getServiciosPlanta().verPlantas(); 
                        if (plantas == null || plantas.isEmpty()) {
                            System.err.println("No hay plantas añadidas en la BBDD.");
                            return;
                        }
                        System.out.println("PLANTAS: ");
                        System.out.println();
                        for (Planta planta : plantas) {
                            System.out.println(planta);
                            System.out.println();
                        }
                        break;
                    case 2:
                    	sc.nextLine();
                		Planta planta;
                		boolean datosPlanta = false;
                		do {
                			planta = new Planta();
                			System.out.print("Escriba el código para la planta: ");
                			try {
                				String codigo = sc.nextLine().trim().toUpperCase();
                				boolean correcto = controlador.getServiciosPlanta().validarCodigo(codigo);
                				boolean existe = controlador.getServiciosPlanta().codigoExistente(codigo);
                				if (!correcto) {
                					System.err.println("El formato del código no es correcto.");
                					continue;
                				}
                				if (existe) {
                					System.err.println("Código ya existente.");
                					continue;
                				}
                				planta.setCodigo(codigo);
                				System.out.print("Nombre común: ");
                				String nombreComun = sc.nextLine().trim();
                				planta.setNombreComun(nombreComun);
                				System.out.print("Nombre científico: ");
                				String nombreCientifico = sc.nextLine().trim();
                				planta.setNombreCientifico(nombreCientifico);
                				datosPlanta = controlador.getServiciosPlanta().validarPlanta(planta);
                				if (!datosPlanta) {
                					System.err.println("Los datos que has introducido no son correctos");
                				}
                			} catch (Exception ex) {
                				System.err.println("Error durante la entrada de datos: " + ex.getMessage());
                				datosPlanta = false;
                			}
                		} while (!datosPlanta);
                		try {
                	        controlador.getServiciosPlanta().insertar(planta);
                	        System.out.println("Planta insertada");
                	    } catch (Exception ex) {
                	        System.err.println("Error al insertar la planta: " + ex.getMessage());
                	    }

                        break;
                    case 3:
                        menuAdminModificarPlantas();
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Debe escribir un número.");
                sc.nextLine();
                opcion = 0;
            }
        } while (opcion != 4);
    }

    
    
    // -------------------------------- MENU ADMIN MODIFICAR PLANTAS -------------------------------
    public void menuAdminModificarPlantas() {
        int opcion = 0;
        do {
        	System.out.println("MENU MODIFICAR PLANTAS");
            System.out.println(" - - - - - - - - - - - - - - - -");
            System.out.println("Selecciona una opción:");
            System.out.println("1. Modificar nombre común");
            System.out.println("2. Modificar nombre científico");
            System.out.println("3. Volver al menú de plantas");
            System.out.println(" - - - - - - - - - - - - - - - -");

            try {
                opcion = sc.nextInt();
                if (opcion < 1 || opcion > 3) {
                    System.err.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                    	ArrayList<Planta> plantas = (ArrayList<Planta>) controlador.getServiciosPlanta().verPlantas(); 
                        if (plantas == null || plantas.isEmpty()) {
                            System.err.println("No hay plantas añadidas en la BBDD.");
                            return;
                        }
                        System.out.println("PLANTAS: ");
                        System.out.println();
                        for (Planta planta : plantas) {
                            System.out.println(planta);
                            System.out.println();
                        }
                    	sc.nextLine();
                	    String codigo = "";
                	    boolean valido = false;
                	    do {
                	        System.out.print("Introduce el código de la planta de la que quieres modificar el nombre común: ");
                	        codigo = sc.nextLine().trim().toUpperCase();
                	        valido = controlador.getServiciosPlanta().validarCodigo(codigo);
                	        if (!valido) {
                	            System.err.println("El código de la planta que has introducido no es válido");
                	        }
                	    } while (!valido);
                	    boolean existe = controlador.getServiciosPlanta().codigoExistente(codigo);
                	    if (!existe) {
                	        System.err.println("El código de la planta que has introducido no existe en la base de datos");
                	        return;
                	    }
                	    System.out.print("Introduce el nuevo nombre común de la planta: ");
                	    String nnComun = sc.nextLine().trim().toUpperCase();
                	    try {
                	        boolean actualizado = controlador.getServiciosPlanta().actualizarNombreComun(codigo, nnComun);
                	        if (actualizado) {
                	            System.out.println("El nombre común de la planta " + codigo
                	                    + " ha sido modificado por: " + nnComun);
                	        } else {
                	            System.err.println("Error al intentar actualizar el nombre común");
                	        }
                	    } catch (Exception ex) {
                	        System.err.println("Error al actualizar el nombre común: " + ex.getMessage());
                	    }
                        break;
                    case 2:
                    	plantas = (ArrayList<Planta>) controlador.getServiciosPlanta().verPlantas(); 
                        if (plantas == null || plantas.isEmpty()) {
                            System.err.println("No hay plantas añadidas en la BBDD.");
                            return;
                        }
                        System.out.println("PLANTAS: ");
                        System.out.println();
                        for (Planta planta : plantas) {
                            System.out.println(planta);
                            System.out.println();
                        }
                    	sc.nextLine();
                	    do {
                	        System.out.print("Introduce el código de la planta de la que quieres modificar el nombre científico: ");
                	        codigo = sc.nextLine().trim();
                	        valido = controlador.getServiciosPlanta().validarCodigo(codigo);
                	        if (!valido) {
                	            System.err.println("El código de la planta que has introducido no es válido");
                	        }
                	    } while (!valido);
                	    existe = controlador.getServiciosPlanta().codigoExistente(codigo);
                	    if (!existe) {
                	        System.err.println("El código de la planta que has introducido no existe en la base de datos");
                	        return; 
                	    }
                	    System.out.print("Introduce el nuevo nombre científico de la planta: ");
                	    String nnCientifico = sc.nextLine().trim();
                	    try {
                	        boolean actualizado = controlador.getServiciosPlanta().actualizarNombreCientifico(codigo, nnCientifico);
                	        if (actualizado) {
                	            System.out.println("El nombre científico de la planta: " + codigo
                	                    + " ha sido modificado por: " + nnCientifico);
                	        } else {
                	            System.err.println("Error al intentar actualizar el nombre científico");
                	        }
                	    } catch (Exception ex) {
                	        System.err.println("Error al actualizar el nombre científico: " + ex.getMessage());
                	    }
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Debe escribir un número");
                sc.nextLine();
                opcion = 0;
            }
        } while (opcion != 3);
    }
    
    
    // --------------------------------- MENU ADMIN EJEMPLARES ----------------------------------------
    public void menuAdminEjemplares() {
        int opcion = 0;
        do {
        	System.out.println("MENU EJEMPLARES");
            System.out.println("- - - - - - - - - - - - - - -");
            System.out.println("Selecciona una opción:");
            System.out.println("1. Registrar nuevo ejemplar");
            System.out.println("2. Filtrar ejemplares por tipo de planta");
            System.out.println("3. Ver mensajes de un ejemplar");
            System.out.println("4. Volver al menú principal");
            System.out.println("- - - - - - - - - - - - - - -");            
            
            try {
                opcion = sc.nextInt();
                if (opcion < 1 || opcion > 4) {
                    System.err.println("Opción incorrecta");
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
                	            boolean codigoValido = controlador.getServiciosPlanta().validarCodigo(codigoPlanta);
                	            boolean plantaExiste = controlador.getServiciosPlanta().codigoExistente(codigoPlanta);
                	            if (!codigoValido) {
                	                System.err.println("El formato del código no es correcto");
                	                continue;
                	            }
                	            if (!plantaExiste) {
                	                System.err.println("No existe una planta con ese codigo");
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
                	                System.err.println("No se ha encontrado la persona autenticada");
                	            } else {
                	                mensaje = new Mensaje(fechaHora, mensajeTexto, persona, ejemplar);
                	                controlador.getServiciosMensaje().addMensaje(mensaje);
                	                System.out.println("Mensaje de creación del ejemplar añadido");
                	            }
                	            correcto = true;
                	        } catch (Exception ex) {
                	            System.err.println("Error al crear el ejemplar: " + ex.getMessage());
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
                        System.out.println("PLANTAS: ");
                        System.out.println();
                        for (Planta planta : plantas) {
                            System.out.println(planta);
                            System.out.println();
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
                            System.err.println("Error al buscar ejemplares: " + e.getMessage());
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
                            System.out.println();
                        }
                    	System.out.print("Introduce el id de un ejemplar para ver sus mensajes: ");
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
                					System.out.println();
                				}
                			}
                		} catch (Exception e) {
                			System.err.println("Error al intentar buscar los mensajes del ejemplar: " + e.getMessage());
                		}
                        break;

                }
            } catch (InputMismatchException e) {
                System.err.println("Debe escribir un número");
                sc.nextLine();
                opcion = 0;
            }
        } while (opcion != 4);
    }
    
    
    // ---------------------------------- MENU ADMIN PERSONAS ------------------------------------
    public void menuAdminPersonas() {
		int opcion = 0;
		do {
			System.out.println("MENU PERSONAS");
            System.out.println(" - - - - - - - - - - - - - - - -");
			System.out.println("Selecciona una opción:");
			System.out.println("1. Registrar nueva persona");
			System.out.println("2. Ver todas las personas");
			System.out.println("3. Volver al menú principal");
            System.out.println(" - - - - - - - - - - - - - - - -");
			try {
				opcion = sc.nextInt();
				if (opcion < 1 || opcion > 4) {
					System.err.println("Opción incorrecta");
					continue;
				}
				switch (opcion) {
				case 1:
					sc.nextLine();
				    Persona pers;
				    Credencial credencial;
				    boolean correcto = false;
				    boolean emailValido = false;
				    boolean usuarioValido = false;
				    boolean contraseñaValida = false;
				    String usuario = "";
				    String contraseña = "";
				    do {
				        emailValido = false;
				        usuarioValido = false;
				        contraseñaValida = false;
				        pers = new Persona();  
				        credencial = new Credencial();
				        System.out.print("Nombre: ");
				        String nombre = sc.nextLine().trim();
				        pers.setNombre(nombre);
				        String email = "";
				        do {
				            System.out.print("Email: ");
				            email = sc.nextLine().trim();
				            pers.setEmail(email);
				            if (controlador.getServiciosPersona().emailExistente(email)) {
				                System.err.println("El email ya está registrado");
				            } else {
				                emailValido = true;
				            }
				        } while (!emailValido);
				        do {
				            System.out.print("Usuario: ");
				            usuario = sc.nextLine().trim();
				            if (controlador.getServiciosCredencial().usuarioExistente(usuario) || usuario.length() < 3) {
				                System.err.println("Usuario registrado o no válido.");
				            } else {
				                usuarioValido = true;
				                credencial.setUsuario(usuario);
				            }
				        } while (!usuarioValido);
				        do {
				            System.out.print("Contraseña: ");
				            contraseña = sc.nextLine().trim();
				            if (controlador.getServiciosCredencial().validarPassword(contraseña) == false) {
				                System.err.println("La contraseña debe tener entre 6 y 20 caracteres y/o números.");
				            } else {
				                contraseñaValida = true;
				                credencial.setPassword(contraseña);
				            }
				        } while (!contraseñaValida);
				        credencial.setPersona(pers);  
				        pers.setCredencial(credencial);
				        correcto = controlador.getServiciosPersona().validarPersona(pers);
				        if (!correcto) {
				            System.err.println("Datos no válidos.");
				        }
				    } while (!correcto);
				    try {
				        controlador.getServiciosPersona().insertar(pers);
				        System.out.println("Persona y credenciales insertadas correctamente");
				    } catch (Exception ex) {
				        System.err.println("Error al insertar la persona: " + ex.getMessage());
				    }
					break;
				case 2:
					ArrayList<Persona> personas = (ArrayList<Persona>) controlador.getServiciosPersona().verTodos();
					if (personas == null || personas.isEmpty()) {
						System.err.println("No hay personas en la BBDD.");
						return;
					}
					System.out.println("PERSONAS: ");
					System.out.println();
					for (Persona persona : personas) {
						System.out.println(persona);
						System.out.println();
					}
					break;
				}
			} catch (InputMismatchException e) {
				System.err.println("Debe escribir un número");
				sc.nextLine();
				opcion = 0;
			}
		} while (opcion != 3);
	}

    
    // ----------------------------------------- MENU ADMIN MENSAJES -------------------------------
    public void menuAdminMensajes() {
        int opcion = 0;
        do {
        	System.out.println("MENU MENSAJES");
            System.out.println(" - - - - - - - - - - - - - - - -");
            System.out.println("Selecciona una opción:");
            System.out.println("1. Nuevo mensaje");
            System.out.println("2. Ver mensajes");
            System.out.println("3. Volver al menú principal");
            System.out.println(" - - - - - - - - - - - - - - - -");
            try {
                opcion = sc.nextInt();
                if (opcion < 1 || opcion > 3) {
                    System.err.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                    	boolean correcto = false;
                        do {
                            try {
                                controlador.getServiciosEjemplar().verEjemplares();
                                if (controlador.getServiciosEjemplar().verEjemplares() == null || controlador.getServiciosEjemplar().verEjemplares().isEmpty()) {
                                    System.err.println("No hay ejemplares.");
                                    return; 
                                }
                                System.out.println();
                                System.out.print("Introduce el id del ejemplar para ponerle un mensaje: ");
                                int idEjemplar = sc.nextInt();
                                sc.nextLine();
                                Ejemplar ejemplar = controlador.getServiciosEjemplar().buscarPorID((long) idEjemplar);
                                if (ejemplar == null) {
                                    System.err.println("No existe un ejemplar con el ID proporcionado");
                                } else {
                                    System.out.println("Introduce el mensaje: ");
                                    String mensajeTexto = sc.nextLine().trim();
                                    if (mensajeTexto.isEmpty()) {
                                        System.err.println("El mensaje no puede estar vacío.");
                                    } else if (controlador.getServiciosMensaje().validarMensaje(mensajeTexto)) {
                                        String usuarioAutenticado = controlador.obtenerUsuarioConectado();
                                        Persona p = controlador.getServiciosPersona().buscarPorNombre(usuarioAutenticado);
                                        if (p == null) {
                                            System.err.println("No se ha encontrado la persona autenticada");
                                        } else {
                                            Mensaje nuevoMensaje = new Mensaje(LocalDateTime.now(), mensajeTexto, p, ejemplar);
                                            controlador.getServiciosMensaje().addMensaje(nuevoMensaje);
                                            System.out.println("Mensaje añadido.");
                                            correcto = true;
                                        }
                                    } else {
                                        System.err.println("El mensaje no es válido.");
                                    }
                                }
                            } catch (InputMismatchException e) {
                                System.err.println("Debe introducir un número válido");
                                sc.nextLine();
                            }
                        } while (!correcto);
                        break;
                    case 2:
                        menuAdminVerMensajes();
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Debe escribir un número");
                sc.nextLine();
                opcion = 0;
            }
        } while (opcion != 3);
    }

    
    // -------------------------- MENU ADMIN VER MENSAJES ------------------------------
    public void menuAdminVerMensajes() {
        int opcion = 0;
        do {
        	System.out.println("MENU VER MENSAJES");
            System.out.println(" - - - - - - - - - - - - - - - -");
            System.out.println("Selecciona una opción:");
            System.out.println("1. Ver todos los mensajes");
            System.out.println("2. Ver mensajes por persona");
            System.out.println("3. Ver mensajes por fechas");
            System.out.println("4. Ver mensajes por código de planta");
            System.out.println("5. Volver al menú de mensajes");
            System.out.println(" - - - - - - - - - - - - - - - -");
            try {
                opcion = sc.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.err.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                    	ArrayList<Mensaje> mensajes = controlador.getServiciosMensaje().verMensajes();
                		if (mensajes == null || mensajes.isEmpty()) {
                			System.err.println("No hay mensajes en la BBDD.");
                			return;
                		}
                		System.out.println("Todos los mensajes: ");
                		System.out.println();
                		for (Mensaje m : mensajes) {
                			System.out.println(m);
                			System.out.println();
                		}
                        break;
                    case 2:
                    	ArrayList<Persona> personas = (ArrayList<Persona>) controlador.getServiciosPersona().verTodos();
    					if (personas == null || personas.isEmpty()) {
    						System.err.println("No hay personas en la BBDD.");
    						return;
    					}
    					System.out.println("PERSONAS: ");
    					System.out.println();
    					for (Persona persona : personas) {
    						System.out.println(persona);
    						System.out.println();
    					}
                    	System.out.print("Introduce el id de una persona para ver sus mensajes: ");
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
                    case 3:
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
                    case 4:
                    	ArrayList<Planta> plantas = (ArrayList<Planta>) controlador.getServiciosPlanta().verPlantas(); 
                        if (plantas == null || plantas.isEmpty()) {
                            System.err.println("No hay plantas añadidas en la BBDD.");
                            return;
                        }
                        System.out.println("PLANTAS: ");
                        System.out.println();
                        for (Planta planta : plantas) {
                            System.out.println(planta);
                            System.out.println();
                        }
                    	System.out.print("Introduce el código de una planta: ");
            			sc.nextLine();
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
                }
            } catch (InputMismatchException e) {
                System.err.println("Debe escribir un número");
                sc.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }


}