/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

 
public class Produto {
    private String nome;
    private float preco;
    
    public Produto(String nome, float preco){
        this.nome = nome;
        this.preco = preco;
    }
    
    public void setPreco(float preco){
        this.preco = preco;
    }
    public float getPreco(){
        return this.preco;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }
}
