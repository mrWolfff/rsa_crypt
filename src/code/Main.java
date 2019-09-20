package code;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Encrypt encrypt = new Encrypt();
		encrypt.generateKeys();
		int publicCode = encrypt.getRandomE();
		int multiPQ = encrypt.getNumberP() *  encrypt.getNumberQ();
		int phi = encrypt.getPhi();
		int privateKey = encrypt.getPrivateKey();
		
		int[] messageEncrypt = (int[]) encrypt.encryptMessage("seguranca da informacao", publicCode, multiPQ);//Digite sua Mensagem Aqui
		encrypt.decryptMessage(messageEncrypt, publicCode, phi, multiPQ, privateKey);
	}

}
