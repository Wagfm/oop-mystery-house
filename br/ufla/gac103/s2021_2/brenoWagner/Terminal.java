package br.ufla.gac103.s2021_2.brenoWagner;

import java.util.Scanner;

import br.ufla.gac103.s2021_2.baseJogo.EntidadeGrafica;
import br.ufla.gac103.s2021_2.baseJogo.InterfaceUsuario;

/**
 * Classe Terminal
 * 
 * @author Breno Vinicius de Sousa e Wagner Flausino Maciel
 * @version 1.0
 */
public class Terminal implements InterfaceUsuario {
    private Scanner input;

    /**
     * Controi o objeto da classe Terminal.
     */
    public Terminal() {
        input = new Scanner(System.in);
    }

    /**
     * Sobrescreve o metodo exibirMensagem() da interface InterfaceUsuario;
     * Exibe a mensagem informada no terminal.
     */
    @Override
    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    /**
     * Sobrescreve o metodo continuarMensagem() da interface InterfaceUsuario;
     * Exibe a mensagem a ser acrescentada na mensagem anterior.
     */
    @Override
    public void continuarMensagem(String mensagem) {
        System.out.print(mensagem);
    }

    /**
     * Sobrescreve o metodo obterComando() da interface InterfaceUsuario;
     * Retorna o comando informado pelo jogador no terminal.
     */
    @Override
    public String obterComando() {
        String comando = input.nextLine();
        return comando;
    }

    /**
     * Sobrescreve o metodo obterInformacao() da interface InterfaceUsuario;
     * Retorna a informacao dada pelo usuario.
     */
    @Override
    public String obterInformacao(String instrucao) {
        String info = "";
        System.out.print(instrucao);
        info = input.nextLine();
        return info;

    }

    /**
     * Sobrescreve o metodo ambienteAtualMudou() da interface InterfaceUsuario;
     */
    @Override
    public void ambienteAtualMudou(EntidadeGrafica ambiente) {
        // nada a fazer no momento...
    }

    /**
     * Sobrescreve o metodo jogadorPegouItem() da interface InterfaceUsuario;
     */
    @Override
    public void jogadorPegouItem(EntidadeGrafica item) {
        // nada a fazer no momento...
    }

    /**
     * Sobrescreve o metodo jogadorDescartouItem() da interface InterfaceUsuario;
     */
    @Override
    public void jogadorDescartouItem(EntidadeGrafica item) {
        // nada a fazer no momento...
    }
}
