package br.ufla.gac103.s2021_2.brenoWagner;

import br.ufla.gac103.s2021_2.baseJogo.InterfaceUsuario;
import br.ufla.gac103.s2021_2.baseJogo.Tela;

/**
 * Classe ProgramaGrafico
 * 
 * @author Breno Vinicius de Sousa e Wagner Flausino Maciel
 * @version 1.0
 */
public class ProgramaGrafico {
	/**
	 * Funcao principal do jogo.
	 * 
	 * @param args Parametro da maquina virtual do Java.
	 */
	public static void main(String[] args) {
		InterfaceUsuario interfaceJogo = new Tela("Mystery House");
		Jogo jogo = new Jogo(interfaceJogo);
		jogo.jogar();
	}
}
