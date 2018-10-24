package JEMP;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Main {
	public static void firmador(String aca, String cpf, String fac) {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			KeyPair keyPair = keyGen.generateKeyPair();
			try (FileOutputStream fos = new FileOutputStream(cpf)) {
				   fos.write(keyPair.getPublic().getEncoded());
			}
			
			Signature sig = Signature.getInstance("SHA1WithRSA");
			sig.initSign(keyPair.getPrivate());
			sig.update(Files.readAllBytes(Paths.get(aca)));
			try (FileOutputStream fos = new FileOutputStream(fac)) {
				   fos.write(sig.sign());
			}
		}
		
		catch (Exception e) { e.printStackTrace(); } 
	}
	
	public static void cifradorA(String a, String cp,String ac) {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			KeyPair clavesRSA = keyGen.generateKeyPair();
			try (FileOutputStream fos = new FileOutputStream(cp)) {
				fos.write(clavesRSA.getPrivate().getEncoded());
			}
			Cipher cifrador = Cipher.getInstance("RSA");
			cifrador.init(Cipher.ENCRYPT_MODE, clavesRSA.getPublic());
			try (FileOutputStream fos = new FileOutputStream(ac)) {
				fos.write(cifrador.doFinal(Files.readAllBytes(Paths.get(a))));
			}
		}
			
		catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void cifradorS(String a,String ac) {
		try {
			Cipher cifrador = Cipher.getInstance("AES");
			cifrador.init(Cipher.ENCRYPT_MODE, new SecretKeySpec("cotra no secreta".getBytes(),"AES"));
			try (FileOutputStream fos = new FileOutputStream(ac)) {
				   fos.write(cifrador.doFinal(Files.readAllBytes(Paths.get(a))));
			}
		}
		
		catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		String ruta = "C:\\Users\\jose mundo\\Desktop\\test\\";
		cifradorA(ruta + "test.zip",ruta + "clavePrivadaA",ruta + "archivoCifradoA");
		cifradorS(ruta + "test.zip",ruta + "archivoCifradoS");
		firmador(ruta + "archivoCifradoA",ruta + "clavePublicaFirmaA",ruta + "firmaArchivoCifradoA");
		firmador(ruta + "archivoCifradoS",ruta + "clavePublicaFirmaS",ruta + "firmaArchivoCifradoS");
	}
}