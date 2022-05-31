package br.ufla.gac103.s2021_2.brenoWagner;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe Jogador
 * 
 * @author Breno Vinicius de Sousa e Wagner Flausino Maciel
 * @version 1.0
 */
public class Jogador {
    private String nome;
    private List<Item> bolsos;
    private int pontos;

    /**
     * Constroi o objeto da classe Jogador a partir de seu nome.
     * 
     * @param nome O nome do jogador.
     */
    public Jogador(String nome) {
        this.nome = nome;
        bolsos = new ArrayList<>();
        pontos = 0;
    }

    /**
     * Retorna o nome do jogador.
     * 
     * @return O nome do jogador.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Adciona o item informado ao inventario do jogador.
     * 
     * @param item O item a ser adicionado.
     */
    public void addItem(Item item) {
        bolsos.add(item);
    }

    /**
     * Remove o item com o nome informado, retornando-o.
     * 
     * @param nome O nome do item.
     * @return O item removido.
     */
    public Item removerItem(String nome) {
        Item itemRetorno = null;
        for (int i = 0; i < bolsos.size(); i++) {
            if (bolsos.get(i).getNome().equalsIgnoreCase(nome)) {
                itemRetorno = bolsos.get(i);
                bolsos.remove(itemRetorno);
                return itemRetorno;
            }
        }
        return null;
    }

    /**
     * Retorna os itens contidos no inventario do jogador.
     * 
     * @return Os itens contidos no inventario do jogador.
     */
    public String getItens() {
        String itens = "";
        for (Item item : bolsos) {
            itens += item.getNome() + "; ";
        }
        if (itens.isEmpty()) {
            return "[Sem itens]";
        }
        return itens;
    }

    /**
     * Verifica se o jogador possui o item com o nome informado em seu inventario.
     * 
     * @param nomeItem O nome do item.
     * @return TRUE caso o jogador possua o item.
     */
    public boolean possuiItem(String nomeItem) {
        for (Item item : bolsos) {
            if (item.getNome().equals(nomeItem)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna os pontos do jogador.
     * 
     * @return Pontos do jogador.
     */
    public int getPontos() {
        return pontos;
    }

    /**
     * Atualiza os pontos do jogador.
     * 
     * @param valorPontos Valor em pontos.
     */
    public void atualizarPontos(int valorPontos) {
        pontos += valorPontos;
    }

}
