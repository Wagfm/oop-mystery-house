package br.ufla.gac103.s2021_2.brenoWagner;

import br.ufla.gac103.s2021_2.baseJogo.EntidadeGrafica;

/**
 * Classe Item
 * 
 * @author Breno Vinicius de Sousa e Wagner Flausino Maciel
 * @version 1.0
 */
public abstract class Item extends EntidadeGrafica {
    private String nome;
    private String descricao;

    /**
     * Constroi o objeto da classe Item a partir dos parametros informados.
     * 
     * @param nome      O nome do item.
     * @param descricao A descricao do item.
     */
    public Item(String nome, String descricao, String caminhoImagem) {
        super(caminhoImagem);
        this.nome = nome;
        this.descricao = descricao;
    }

    /**
     * Retorna o nome do item.
     * 
     * @return O nome do item.
     */
    @Override
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a descricao do item.
     * 
     * @return A descricao do item.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o valor em pontos do item.
     * 
     * @return O valor em pontos do item.
     */
    public int getValorPontos(){
        return 0;
    }

}
