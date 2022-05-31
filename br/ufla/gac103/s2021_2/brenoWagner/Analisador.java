package br.ufla.gac103.s2021_2.brenoWagner;

import java.util.Scanner;

import br.ufla.gac103.s2021_2.baseJogo.InterfaceUsuario;

/**
 * Esta classe eh parte da aplicacao "World of Zuul".
 * "World of Zuul" eh um jogo de aventura muito simples, baseado em texto.
 * 
 * Esse analisador le a entrada do usuario e tenta interpreta-la como um
 * comando "Adventure". Cada vez que eh chamado ele le uma linha do terminal
 * e tenta interpretar a linha como um comando de duas palavras. Ele retorna
 * o comando como um objeto da classe Comando.
 *
 * O analisador tem um conjunto de palavras de comando conhecidas. Ele compara
 * a entrada do usuario com os comandos conhecidos, e se a entrada nao eh um
 * dos comandos conhecidos, ele retorna um objeto comando que eh marcado como
 * um comando desconhecido.
 * 
 * @author Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */
public class Analisador {
    private PalavrasComando palavrasDeComando; // guarda todas as palavras de comando validas
    private InterfaceUsuario interfaceJogo;

    private static Analisador analisador; // atributo para a aplicação do padrão de projeto Singleton

    /**
     * Cria um analisador para ler do terminal.
     */
    private Analisador(InterfaceUsuario interfaceJogo) {
        palavrasDeComando = new PalavrasComando();
        this.interfaceJogo = interfaceJogo;
    }

    /**
     * @return O proximo comando do usuario
     */
    public Comando pegarComando() {
        String linha; // guardara uma linha inteira
        String palavra1 = null;
        String palavra2 = null;

        interfaceJogo.continuarMensagem(" > "); // imprime o prompt

        linha = interfaceJogo.obterComando();

        // Tenta encontrar ate duas palavras na linha
        Scanner tokenizer = new Scanner(linha);
        if (tokenizer.hasNext()) {
            palavra1 = tokenizer.next(); // pega a primeira palavra
            if (tokenizer.hasNext()) {
                palavra2 = tokenizer.next(); // pega a segunda palavra
                // obs: nos simplesmente ignoramos o resto da linha.
            }
        }
        tokenizer.close();

        // Agora verifica se esta palavra eh conhecida. Se for, cria um
        // com ela. Se nao, cria um comando "null" (para comando desconhecido)
        if (palavrasDeComando.ehComando(palavra1)) {
            return new Comando(palavra1, palavra2);
        } else {
            return new Comando(null, palavra2);
        }
    }

    /**
     * Retorna os comandos validos do jogo.
     * 
     * @return Os comandos validos do jogo.
     */
    public String getComandos() {
        return PalavrasComando.getComandos();
    }

    /**
     * Método getInstance de Analisador
     * 
     * @param interfaceJogo A interface do jogo.
     * @return A instância do analisador.
     */
    public static Analisador getInstance(InterfaceUsuario interfaceJogo) {
        if (analisador == null) {
            return new Analisador(interfaceJogo);
        }
        return analisador;
    }

}
