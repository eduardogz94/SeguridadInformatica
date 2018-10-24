package JEMP;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class OpServer {
	public OpServer(String s) {
		if(this.verificar(s + "\\clavePublicaFirmaA", s + "\\firmaArchivoCifradoA")) {
			this.descifradorA(s + "\\clavePrivadaA", s + "\\archivoCifradoA", s + "\\resultadoA.zip");
		}
		
		if(this.verificar(s + "\\clavePublicaFirmaS", s + "\\firmaArchivoCifradoS")) {
			this.descifradorS(s + "\\archivoCifradoS", s + "\\resultadoS.zip");
		}
	}
	
	private Boolean verificar(String cpf,String fac) {
		try {
			byte[] bytesFirmados = Files.readAllBytes(Paths.get(fac));
			Signature sig = Signature.getInstance("SHA1WithRSA");
			sig.initVerify(KeyFactory.getInstance("RSA")
				.generatePublic(new X509EncodedKeySpec(Files.readAllBytes(Paths.get(cpf)))));
			sig.update(bytesFirmados);
			if (!sig.verify(bytesFirmados)) { return true; }
			else { return false; }	
		} 
		
		catch (Exception e) { e.printStackTrace(); return false; }
	}
	
	private void descifradorA(String cp,String ac,String ad) {
		try {
			Cipher cifrador = Cipher.getInstance("RSA");
			cifrador.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance("RSA")
				.generatePrivate(new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(cp)))));
			try (FileOutputStream fos = new FileOutputStream(ad)) {
				fos.write(cifrador.doFinal(Files.readAllBytes(Paths.get(ac))));
			}
		} 
		
		catch (Exception e) { e.printStackTrace(); }	
	}
	
	private void descifradorS(String ac,String ad) {
		try {
			Cipher cifrador = Cipher.getInstance("AES");         
			cifrador.init( Cipher.DECRYPT_MODE,new SecretKeySpec("cotra no secreta".getBytes(),"AES"));
			try (FileOutputStream fos = new FileOutputStream(ad)) {
				fos.write(cifrador.doFinal(Files.readAllBytes(Paths.get(ac))));
			}
		}
		
		catch (Exception e) { e.printStackTrace(); }
	}
}