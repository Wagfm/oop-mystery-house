package br.ufla.gac103.s2021_2.brenoWagner;

import java.util.HashMap;

import br.ufla.gac103.s2021_2.baseJogo.EntidadeGrafica;

/**
 * Classe Ambiente - um ambiente em um jogo adventure.
 *
 * Esta classe eh parte da aplicacao "World of Zuul".
 * "World of Zuul" eh um jogo de aventura muito simples, baseado em texto.
 *
 * Um "Ambiente" representa uma localizacao no cenario do jogo. Ele eh
 * conectado aos outros ambientes atraves de saidas. As saidas sao
 * nomeadas como norte, sul, leste e oeste. Para cada direcao, o ambiente
 * guarda uma referencia para o ambiente vizinho, ou null se nao ha
 * saida naquela direcao.
 * 
 * @author Michael Kölling and David J. Barnes (traduzido por Julio Cesar Alves)
 * @version 2011.07.31 (2016.02.01)
 */
public class Ambiente extends EntidadeGrafica {
    private String nomeAmbiente;
    private String descricao;
    private HashMap<String, Ambiente> saidas;

    private Item item;

    private String saidaBloqueada;
    private String itemDesbloqueia;

    /**
     * Cria um ambiente com a "descricao" passada. Inicialmente, ele
     * nao tem saidas. "descricao" eh algo como "uma cozinha" ou
     * "
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "um jardim aberto".
     * 
     * @param descricao A descricao do ambiente.
     */
    public Ambiente(String nomeAmbiente, String descricao, String caminhoImagem) {
        super(caminhoImagem);
        this.descricao = descricao;
        this.nomeAmbiente = nomeAmbiente;
        saidas = new HashMap<String, Ambiente>();
        saidaBloqueada = "";
        itemDesbloqueia = "";
    }

    /**
     * Constroi o ambiente a partir de sua descricao com um item.
     * 
     * @param descricao Descricao do ambiente.
     * @param item      Item do ambiente.
     */
    public Ambiente(String nomeAmbiente, String descricao, Item item, String caminhoImagem) {
        this(nomeAmbiente, descricao, caminhoImagem);
        this.item = item;
    }

    /**
     * Retorna o ambiente associado a direcao fornecida.
     * 
     * @param direcao Direcao a seguir.
     * @return O ambiente associado a direcao fornecida.
     */
    public Ambiente getSaida(String direcao) {
        if (direcao.equals(saidaBloqueada)) {
            return null;
        }
        return saidas.get(direcao);
    }

    /**
     * Verifica se ha um item no ambiente.
     * 
     * @return TRUE caso haja um item no ambiente.
     */
    public boolean existeItem() {
        return !(item == null);
    }

    /**
     * Retorna a descricao detalhada do ambiente.
     * 
     * @return A descricao detalhada do ambiente.
     */
    public String getDescricaoLonga() {
        if (existeItem()) {
            return getDescricao() + "; contem uma " + item.getNome() + " -> " + item.getDescricao();
        } else {
            return getDescricao() + "; nao ha nada aqui";
        }
    }

    /**
     * Caso haja um item no ambiente, retorna seu nome.
     * 
     * @return O nome do item no ambiente.
     */
    public String getNomeItem() {
        if (existeItem()) {
            return item.getNome();
        }
        return "";
    }

    /**
     * Remove o item do ambiente, retornando-o.
     * 
     * @return O item coletado.
     */
    public Item pegarItem() {
        Item item = this.item;
        this.item = null;
        return item;
    }

    /**
     * Define a saida do ambiente. A direcao ou leva a um
     * outro ambiente ou eh null (nenhuma saida para la).
     * 
     * @param direcao  A direcao da saida.
     * @param ambiente Ambiente na direcao de saida
     */
    public void ajustarSaida(String direcao, Ambiente ambiente) {
        saidas.put(direcao, ambiente);
    }

    /**
     * @return A descricao do ambiente.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna todas as possiveis saidas do ambiente.
     * 
     * @return As saidas do ambiente.
     */
    public String getSaidas() {
        String saidas = "";
        for (String direcao : this.saidas.keySet()) {
            saidas += direcao + ' ';
        }
        return saidas;
    }

    /**
     * Define a saida na direcao fornecida como bloqueada e o objeto que a
     * desbloqueia.
     * 
     * @param direcao         A direcao de saida.
     * @param ambiente        O ambiente na direcao de saida.
     * @param itemDesbloqueia O nome do item que desbloqueia a passagem.
     */
    public void ajustarSaidaBloqueada(String direcao, Ambiente ambiente, String itemDesbloqueia) {
        ajustarSaida(direcao, ambiente);
        saidaBloqueada = direcao;
        this.itemDesbloqueia = itemDesbloqueia;
    }

    /**
     * Usa o item informado no ambiente.
     * 
     * @param item O item a ser usado
     * @return TRUE caso o item possa ser usado.
     */
    public boolean usarItem(Item item) {
        if (item != null) {
            if (item.getNome().equals(itemDesbloqueia)) {
                saidaBloqueada = "";
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se a direcao informada eh uma saida do ambiente.
     * 
     * @param direcao A direcao de saida.
     * @return TRUE caso a direcao seja uma saida.
     */
    public boolean ehSaida(String direcao) {
        for (String saida : this.saidas.keySet()) {
            if (saida.equals(direcao)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNome() {
        return nomeAmbiente;
    }

}
