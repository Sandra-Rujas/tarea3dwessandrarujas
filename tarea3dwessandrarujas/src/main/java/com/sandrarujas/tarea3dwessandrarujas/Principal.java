package com.sandrarujas.tarea3dwessandrarujas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.sandrarujas.tarea3dwessandrarujas.vista.ViveroFachadaInvitado;


public class Principal implements CommandLineRunner{
	
	 @Autowired
	 private ViveroFachadaInvitado viveroFachadaInvitado;

	@Override
	public void run(String... args) throws Exception {
		
		/*Entramos al menú del invitado y de ahí ya podemos "viajar" a los demás*/
		viveroFachadaInvitado.menuInvitado();
  }
		
	}

