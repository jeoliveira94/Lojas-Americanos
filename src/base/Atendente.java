/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

public class Atendente extends Funcionario{
    //valor percentual que ele ganha do total de vendas
    private float comissao;
    //somatório de todas as vendas
    private float somatorioDeVendas;

    //CONSTRUTOR
    public Atendente(String nome, String cpf, String senha, float comissao, String nomeDaLoja){
        super(nome, cpf, senha);
        this.comissao = comissao;
        this.somatorioDeVendas = 0;
        super.setNomeDaLoja(nomeDaLoja);
    }

    //ENCAPSULAMENTO
    public void setComissao(float comissao) {
        this.comissao = comissao;
    }
    public float getComissao() {
        return comissao;
    }
    public void setSomatorioDeVendas(float somatorioDeVendas) {
        this.somatorioDeVendas = somatorioDeVendas;
    }
    public float getSomatorioDeVendas() {
        return somatorioDeVendas;
    }
    //FIM DO ENCAPSULAMENTO

    //Registra no vendedor a venda do produto,
    //multiplicando o preco do produto e somando
    //ao total de vendas
    protected float vender(Produto produto, int quantidade){
        float preco = produto.getPreco();
        float total = preco * quantidade;
        this.somatorioDeVendas += total;
        return total;
    }
    @Override
    public void pagar(){
        this.setSalario(this.somatorioDeVendas * this.comissao);
    }
}
