/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Gerente extends Funcionario{
    //loja do gerente
    private Loja loja;
    
    public Gerente(String nome, String cpf, String senha, Loja loja){
        super(nome, cpf, senha);
        this.loja = loja;
        File pastaDaLoja = new File("Gerentes");
        boolean successful = pastaDaLoja.mkdirs();
        try( PrintWriter out = new PrintWriter("Gerentes/gerentesInfo.txt" )){
            out.println(this.getNome() + ";" + cpf + ";" + senha + ";" + this.getLoja().getNome());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void setLoja(Loja loja){
        this.loja = loja;
    }
    public Loja getLoja(){
        return this.loja;
    }

    //CADASTRA PRODUTOS NOVOS
    //PRODUTOS ANTIGOS SERÃO IGNORADOS
    public Produto cadastrarProduto(String nome, float preco) throws IOException {
        HashMap<Produto, Integer> inventario = this.loja.getInventario();
        Set<Produto> produtos =  inventario.keySet();
        for(Produto p : produtos){
            if(p.getNome().equals(nome)){
                Main.EscreveNoLog("O gerente "+this.getNome()+" tentou cadastrar o produto "+p.getNome()+" de valor "+ p.getPreco()+" que já havia sido cadastrado na loja " + this.loja.getNome());
                return p;
            }
        }
        Produto produto = new Produto(nome, preco);
        inventario.put(produto, 0);
        if(inventario.containsKey(produto)){
            this.loja.registrarVendasNoArquivo();
            Main.EscreveNoLog("O gerente "+this.getNome()+" cadastrou com sucesso o produto '"+produto.getNome()+"' de valor "+ produto.getPreco()+" na loja " + this.loja.getNome());
            return produto;
        }
        else{
            Main.EscreveNoLog("O gerente "+this.getNome()+" falhou em cadastrar o produto "+produto.getNome()+" de valor "+ produto.getPreco()+" na loja " + this.loja.getNome());
            return null;
        }
    }
    //CADASTRA FUNCIONARIOS NOVOS
    //FUNCIONARIOS ANTIGOS SERÃO IGNORADOS
    public Atendente cadastrarAtendente(String nome, String cpf, String senha, String nomeDaLoja){
        ArrayList<Atendente> atendentes = this.loja.getAtendentes();
        //verifica se já existe
        for(Atendente a : atendentes){
            if(a.getCPF().equals(cpf)){
                Main.EscreveNoLog("O gerente "+this.getNome()+" falhou no cadastro do atendente '"+a.getNome()+"' de cpf "+ a.getCPF()+" na loja " + this.loja.getNome()+", pois ele já está cadastrado");
                return a;
            }
        }
        float comissao = 0.1f;
        Atendente atendente = new Atendente(nome, cpf, senha, comissao, this.loja.getNome());
        atendentes.add(atendente);
        if( atendentes.contains(atendente) ){
            Main.EscreveNoLog("O gerente "+this.getNome()+" cadastrou com sucesso o atendente '"+atendente.getNome()+"' de cpf "+ atendente.getCPF()+" na loja " + this.loja.getNome());
            this.loja.registrarAtendentesNoArquivo();
            return atendente;
        }else{
            Main.EscreveNoLog("O gerente "+this.getNome()+" falhou no cadastro do atendente '"+atendente.getNome()+"' de cpf "+ atendente.getCPF()+" na loja " + this.loja.getNome());
            return null;
        }
    }
    @Override
    public void pagar(){
        float total = 0;
        for(Atendente atendente : this.loja.getAtendentes()){
            total += (1-atendente.getComissao())*atendente.getSomatorioDeVendas();
        }
        this.setSalario(total * 0.2f);
    }
}
