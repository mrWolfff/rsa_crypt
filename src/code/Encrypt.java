package code;

import java.math.BigInteger;
import java.util.Random;

public class Encrypt {
	private int numberP = 0;
	private int numberQ = 0;
	private int randomE = 0;
	private int phi = 0;
	private int privateKey = 0;

	public int getNumberP() {
		return numberP;
	}

	public int getNumberQ() {
		return numberQ;
	}

	public int getRandomE() {
		return randomE;
	}

	public int getPhi() {
		return phi;
	}

	private static boolean isPrimo(int number) {
		for (int j = 2; j < number; j++) {
			if (number % j == 0)
				return false;
		}
		return true;
	}

	private static int mdc(int value1, int value2) {
		int aux;
		while (value2 != 0) {
			aux = value1 % value2;
			value1 = value2;
			value2 = aux;
		}
		return value1;
	}

	public void generateKeys() {
		System.out.println("Gerando chaves...");
		Random rand = new Random();
		int j = 1;
		boolean conf = false;
		while (!conf) {
			numberP = (int) rand.nextInt(10 + 1) + 10;
			numberQ = (int) rand.nextInt(10 + 1) + 10;
			if (isPrimo(numberP) && isPrimo(numberQ))
				conf = true;
		}
		phi = (numberP - 1) * (numberQ - 1);
		conf = false;
		int mdc = 0;
		while (!conf) {
			for (j = 1; j < phi-1; j++);
			randomE = rand.nextInt(j);
			mdc = mdc(phi, randomE);
			if (mdc == 1)
				conf = true;
		}
		for (int i = 2; i < 10000; i++) {
			if(((randomE * i) % phi) == 1 && i != randomE) {
				privateKey = i;
				break;
			}
		}
		System.out.println("CHAVE PUBLICA(" +randomE+", " + numberP*numberQ + ")" + "CHAVE PRIVADA(" +privateKey+", " + numberP*numberQ + ")");
	}

	public int[] encryptMessage(String message, int publicCode, int multiPQ) {
		System.out.println("Criptografando a mensagem - : " + message );
		BigInteger publicCodeBIG = BigInteger.valueOf(multiPQ);
		BigInteger aux = publicCodeBIG;	
		int[] byteMessage = new int[message.length()];
		for(int i = 0; i < message.length(); i++) {
			byteMessage[i] = (int)message.charAt(i);
		}
		for (int i = 0; i < byteMessage.length; i++) {
			aux = BigInteger.valueOf(byteMessage[i]);
			aux = aux.pow(publicCode);
			aux = aux.mod(publicCodeBIG);
			byteMessage[i] = (int) aux.intValue();
		}
		return byteMessage ;
	}

	public void decryptMessage(int[] messageEncrypt, int publicCode, int phi2, int multiPQ, int privateKey) {
		String message = "";
		BigInteger bigint;
		BigInteger aux = BigInteger.valueOf(multiPQ);
		int messageDecrypt[] = new int[messageEncrypt.length];
		for (int i = 0; i < messageEncrypt.length; i++) {
			bigint = BigInteger.valueOf(messageEncrypt[i]);
			bigint = bigint.pow(privateKey);
			bigint = bigint.mod(aux);
			messageDecrypt[i] = (int) bigint.intValue();
		}
		for (int i = 0; i < messageDecrypt.length; i++) {
			message =  message + Character.toString((char) messageDecrypt[i]);
		}
		System.out.println("Mensagem Decriptografada: " + message);
	}

	public int getPrivateKey() {
		return privateKey;
	}

}
