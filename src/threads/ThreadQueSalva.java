package threads;

import java.io.IOException;

import gerenciadorDeDados.DadosDoSistema;

public class ThreadQueSalva extends Thread {
	@Override
	public void run(){
		while(true) {
			try {
				DadosDoSistema.persistirDados();
			} catch (IOException e) {
				
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {

			}
		}
	}
}
