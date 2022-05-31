package br.ufla.gac103.s2021_2.brenoWagner;

/**
 * Classe Moeda
 * 
 * @author Breno Vinicius de Sousa e Wagner Flausino Maciel
 * @version 1.0
 */
public class Moeda extends Item {
    private int valorPontos;

    /**
     * Constroi o objeto da classe Moeda a partir dos parametros informados.
     * 
     * @param nome        O nome da moeda.
     * @param descricao   A descricao da moeda.
     * @param valorPontos O valor em pontos da moeda.
     */
    public Moeda(String nome, String descricao, int valorPontos, String caminhoImagem) {
        super(nome, descricao, caminhoImagem);
        this.valorPontos = valorPontos;
    }

    /**
     * Retorna o valor em pontos da moeda.
     * 
     * @return O valor em pontos da moeda.
     */
    @Override
    public int getValorPontos() {
        return valorPontos;
    }

    /**
     * Retorna a descricao da chave.
     */
    @Override
    public String getDescricao(){
        return super.getDescricao() + " de valor " + valorPontos;
    }

}
