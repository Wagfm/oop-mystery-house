package br.ufla.gac103.s2021_2.brenoWagner;

import java.io.File;
import java.io.FileWriter;

import br.ufla.gac103.s2021_2.baseJogo.InterfaceUsuario;

/**
 * Essa eh a classe principal da aplicacao "World of Zull".
 * "World of Zuul" eh um jogo de aventura muito simples, baseado em texto.
 * Usuarios podem caminhar em um cenario. E eh tudo! Ele realmente
 * precisa ser estendido para fazer algo interessante!
 * 
 * Para jogar esse jogo, crie uma instancia dessa classe e chame o metodo
 * "jogar".
 * 
 * Essa classe principal cria e inicializa todas as outras: ela cria os
 * ambientes, cria o analisador e comeca o jogo. Ela tambeme avalia e
 * executa os comandos que o analisador retorna.
 * 
 * @author Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */

public class Jogo {
    private Analisador analisador;
    private Ambiente ambienteAtual;
    private InterfaceUsuario interfaceJogo;

    private Jogador jogador;

    private Ambiente saida;

    private String caminhoPercorrido;

    /**
     * Cria o jogo e incializa seu mapa interno.
     */
    public Jogo(InterfaceUsuario interfaceJogo) {
        criarAmbientes();
        this.interfaceJogo = interfaceJogo;
        analisador = Analisador.getInstance(interfaceJogo);
        jogador = new Jogador(interfaceJogo.obterInformacao("Digite seu nome: "));
        caminhoPercorrido = "";
    }

    /**
     * Cria todos os ambientes e liga as saidas deles
     */
    private void criarAmbientes() {
        Ambiente salaEstar, salaJantar, corredor, cozinha, quarto1, quarto2, banheiro, patio, exterior, porao, sotao;

        salaEstar = new Ambiente("Sala de estar", "na sala de estar", "imagens/salaEstar.png");
        salaJantar = new Ambiente("Sala de jantar", "na sala de jantar", "imagens/salaJantar.png");
        corredor = new Ambiente("Corredor", "no corredor de acesso para os quartos e o banheiro",
                "imagens/corredor.png");
        cozinha = new Ambiente("Cozinha", "na cozinha",
                new Moeda("moeda", "uma moeda enferrujada", 25, "imagens/moeda25.png"), "imagens/cozinha.png");
        quarto1 = new Ambiente("Quarto grande", "no quarto grande",
                new Moeda("moeda", "uma moeda brilhante", 100, "imagens/moeda100.png"), "imagens/quarto1.png");
        quarto2 = new Ambiente("Quarto pequeno", "no quarto pequeno",
                new Chave("chave2", "uma chave de cor amarela", "imagens/chaveA.png"), "imagens/quarto2.png");
        banheiro = new Ambiente("Banheiro", "no banheiro", "imagens/banheiro.png");
        patio = new Ambiente("Patio", "no patio da casa",
                new Chave("chave1", "uma chave de cor prata", "imagens/chaveP.png"), "imagens/patio.png");
        exterior = new Ambiente("Exterior", "do lado de fora da residencia", "imagens/exterior.png");
        porao = new Ambiente("Porao", "no porao da casa", "imagens/porao.png");
        sotao = new Ambiente("Sotao", "no sotao da casa", new Moeda("moeda", "uma moeda", 50, "imagens/moeda50.png"),
                "imagens/sotao.png");

        saida = exterior;

        exterior.ajustarSaida("sul", patio);

        patio.ajustarSaidaBloqueada("norte", exterior, "chave2");
        patio.ajustarSaida("sul", salaEstar);

        salaEstar.ajustarSaida("norte", patio);
        salaEstar.ajustarSaida("sul", salaJantar);

        salaJantar.ajustarSaida("norte", salaEstar);
        salaJantar.ajustarSaida("sul", cozinha);
        salaJantar.ajustarSaida("oeste", corredor);

        corredor.ajustarSaidaBloqueada("norte", quarto2, "chave1");
        corredor.ajustarSaida("leste", salaJantar);
        corredor.ajustarSaida("sul", quarto1);
        corredor.ajustarSaida("oeste", banheiro);
        corredor.ajustarSaida("baixo", porao);
        corredor.ajustarSaida("cima", sotao);

        porao.ajustarSaida("cima", corredor);
        sotao.ajustarSaida("baixo", corredor);

        cozinha.ajustarSaida("norte", salaJantar);

        quarto1.ajustarSaida("norte", corredor);

        quarto2.ajustarSaida("sul", corredor);

        banheiro.ajustarSaida("leste", corredor);

        ambienteAtual = banheiro;
    }

    /**
     * Rotina principal do jogo. Fica em loop ate terminar o jogo.
     */
    public void jogar() {
        interfaceJogo.ambienteAtualMudou(ambienteAtual);
        caminhoPercorrido += ambienteAtual.getNome() + " ";
        imprimirBoasVindas();

        // Entra no loop de comando principal. Aqui nos repetidamente lemos
        // comandos e os executamos ate o jogo terminar.

        boolean terminado = false;
        while (!terminado && ambienteAtual != saida) {
            Comando comando = analisador.pegarComando();
            terminado = processarComando(comando);
        }
        interfaceJogo.continuarMensagem("\nPontuacao: " + jogador.getPontos());
        interfaceJogo.continuarMensagem("\nObrigado por jogar. Ate mais!");
    }

    /**
     * Imprime a mensagem de abertura para o jogador.
     */
    private void imprimirBoasVindas() {
        interfaceJogo.exibirMensagem("Bem-vindo ao Mystery House");
        interfaceJogo.continuarMensagem(
                "Mystery House eh um novo jogo de escape, onde voce acorda no chao do banheiro, sem memoria alguma. Boa sorte tentando escapar!");
        interfaceJogo.continuarMensagem("Digite 'ajuda' para saber os comandos disponiveis.\n");
        exibirLocalizacaoAtual();
    }

    /**
     * Exibe a localizacao atual e as possiveis saidas.
     */
    private void exibirLocalizacaoAtual() {
        interfaceJogo.continuarMensagem(
                "Voce esta " + ambienteAtual.getDescricao() + "\nSaidas: " + ambienteAtual.getSaidas());
    }

    /**
     * Dado um comando, processa-o (ou seja, executa-o)
     * 
     * @param comando O Comando a ser processado.
     * @return true se o comando finaliza o jogo.
     */
    private boolean processarComando(Comando comando) {
        boolean querSair = false;

        if (comando.ehDesconhecido()) {
            interfaceJogo.exibirMensagem("Eu nao entendi o que voce disse...");
            return false;
        }

        String palavraDeComando = comando.getPalavraDeComando();
        if (palavraDeComando.equals("ajuda")) {
            imprimirAjuda();
        } else if (palavraDeComando.equals("ir")) {
            irParaAmbiente(comando);
        } else if (palavraDeComando.equals("sair")) {
            querSair = sair(comando);
        } else if (palavraDeComando.equals("observar")) {
            observar();
        } else if (palavraDeComando.equals("pegar")) {
            pegar(comando);
        } else if (palavraDeComando.equals("usar")) {
            usar(comando);
        }

        return querSair;
    }

    /**
     * Informa ao jogador o estado do ambiente e de seu inventario.
     */
    private void observar() {
        interfaceJogo.exibirMensagem(ambienteAtual.getDescricaoLonga());
        interfaceJogo.continuarMensagem("inventario: " + jogador.getItens());
    }

    /**
     * Pega o item no ambiente segundo o comando do jogador.
     * 
     * @param comando O comando do jogador.
     */
    private void pegar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            // se nao ha segunda palavra, nao sabemos pra onde ir...
            interfaceJogo.exibirMensagem("Pegar o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
        if (ambienteAtual.existeItem()) {
            if (nomeItem.equalsIgnoreCase(ambienteAtual.getNomeItem())) {
                Item item = ambienteAtual.pegarItem();
                jogador.addItem(item);
                interfaceJogo.jogadorPegouItem(item);
                interfaceJogo.exibirMensagem("Voce pegou a " + item.getNome() + "!");
                jogador.atualizarPontos(item.getValorPontos());
                return;
            }
        }
        interfaceJogo.exibirMensagem("Nao ha esse item aqui...");
    }

    /**
     * Usa o item no ambiente segundo o comando do jogador.
     * 
     * @param comando O comando do jogador.
     */
    private void usar(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            // se nao ha segunda palavra, nao sabemos pra onde ir...
            interfaceJogo.exibirMensagem("Usar o que?");
            return;
        }
        String nomeItem = comando.getSegundaPalavra();
        if (jogador.possuiItem(nomeItem)) {
            Item item = jogador.removerItem(nomeItem);
            if (ambienteAtual.usarItem(item)) {
                interfaceJogo.jogadorDescartouItem(item);
                interfaceJogo.exibirMensagem("Passagem desbloqueada!");
                return;
            } else {
                interfaceJogo.exibirMensagem("Esse item nao pode ser usado aqui...");
                jogador.addItem(item);
            }
            return;
        }
        interfaceJogo.exibirMensagem("Voce nao possui esse item...");
    }

    // Implementacoes dos comandos do usuario

    /**
     * Printe informacoes de ajuda.
     * Aqui nos imprimimos algo bobo e enigmatico e a lista de
     * palavras de comando
     */
    private void imprimirAjuda() {
        interfaceJogo.exibirMensagem("Voce esta sem memoria e preso na casa...\n");
        interfaceJogo.continuarMensagem("Suas palavras de comando sao:");
        interfaceJogo.continuarMensagem("    " + analisador.getComandos());
    }

    /**
     * Tenta ir em uma direcao. Se existe uma saida entra no
     * novo ambiente, caso contrario imprime mensagem de erro.
     */
    private void irParaAmbiente(Comando comando) {
        if (!comando.temSegundaPalavra()) {
            // se nao ha segunda palavra, nao sabemos pra onde ir...
            interfaceJogo.exibirMensagem("Ir pra onde?");
            return;
        }

        String direcao = comando.getSegundaPalavra();

        // Tenta sair do ambiente atual
        Ambiente proximoAmbiente = ambienteAtual.getSaida(direcao);
        if (proximoAmbiente == null) {
            if (ambienteAtual.ehSaida(direcao)) {
                interfaceJogo.exibirMensagem("Passagem bloqueada!");
            } else {
                interfaceJogo.exibirMensagem("Nao ha passagem!");
            }
        } else {
            ambienteAtual = proximoAmbiente;
            interfaceJogo.ambienteAtualMudou(ambienteAtual);
            caminhoPercorrido += ambienteAtual.getNome() + " ";
        }
        if (ambienteAtual == saida) {
            interfaceJogo.exibirMensagem("Parabens " + jogador.getNome() + "! Voce conseguiu escapar!");
            manipularArquivo();

        } else {
            exibirLocalizacaoAtual();
        }
    }

    /**
     * "Sair" foi digitado. Verifica o resto do comando pra ver
     * se nos queremos realmente sair do jogo.
     * 
     * @return true, se este comando sai do jogo, false, caso contrario
     */
    private boolean sair(Comando comando) {
        if (comando.temSegundaPalavra()) {
            interfaceJogo.exibirMensagem("Sair o que?");
            return false;
        } else {
            return true; // sinaliza que nos queremos sair
        }
    }

    /**
     * Manipula o arquivo texto contendo as pontuacoes e caminhos percorridos.
     * 
     */
    private void manipularArquivo() {
        File arquivo = new File("info.txt");
        try {
            FileWriter escreverArquivo = new FileWriter(arquivo, true);
            try {
                String texto = jogador.getNome() + ": " + Integer.toString(jogador.getPontos()) + " pontos\nCaminho: "
                        + caminhoPercorrido + "\n\n";
                escreverArquivo.write(texto);
                escreverArquivo.close();
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }
}
