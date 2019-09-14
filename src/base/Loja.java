/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Loja {
    //todos os atendentes que trabalham para o gerente
    private String nome;
    //todos os atendentes que trabalham para o gerente
    private ArrayList<Atendente> atendentes;
    //estoque de produtos, onde o valor é a quantidade sobrando
    private HashMap<Produto, Integer> inventario;
    public Loja(String nome) throws IOException {
        this.nome = nome;
        this.atendentes = new ArrayList<Atendente>();
        this.inventario = new HashMap<Produto, Integer>();
        File pastaDaLoja = new File("Lojas/"+this.nome+"/funcionarios");
        pastaDaLoja.mkdirs();
        pastaDaLoja = new File("Lojas/"+this.nome+"/vendas");
        pastaDaLoja.mkdirs();
        pastaDaLoja = new File("Lojas/Lista");
        pastaDaLoja.mkdirs();

        Path path;
        path = Paths.get("Lojas/Lista/listaLojas.txt");
        if (!Files.exists(path)) {
            // file exist
            try( PrintWriter out = new PrintWriter("Lojas/Lista/listaLojas.txt" )){
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        path = Paths.get("Lojas/" + this.nome + "/funcionarios/funcionarioInfo.txt");
        if (!Files.exists(path)) {
            try (PrintWriter out = new PrintWriter("Lojas/" + this.nome + "/funcionarios/funcionarioInfo.txt")) {
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        path = Paths.get("Lojas/" + this.nome + "/vendas/vendas.txt");
        if (!Files.exists(path)) {
            try (PrintWriter out = new PrintWriter("Lojas/" + this.nome + "/vendas/vendas.txt")) {
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        this.lerVendasArquivo();
        this.lerAtendentesNoArquivo();
    }
    //COMEÇO DO ENCAPSULAMENTO

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public ArrayList<Atendente> getAtendentes(){
        return this.atendentes;
    }
    public void setAtendentes(ArrayList<Atendente> atendentes){
        this.atendentes = atendentes;
    }
    public HashMap<Produto,Integer> getInventario(){
        return this.inventario;
    }
    public void setInventario(HashMap<Produto,Integer> inventario){
        this.inventario = inventario;
    }
    //FIM DO ENCAPSULAMENTO

    //registra um arraylist de vendas
    public void registrarVendas(ArrayList<String> pedido, ArrayList<Integer> quantidades, Atendente atendente){
        //registra um Arraylist de produtos com as quantidades respectivas
        for(int i=0; i<pedido.size(); i++){
            this.registrarVenda(atendente, pedido.get(i), quantidades.get(i));
        }
    }
    //registra uma única venda usando o nome do objeto como referência
    public void registrarVenda(Atendente atendente, String nomeProduto, int quantidade){
        Set<Produto> produtos = this.inventario.keySet();
        for(Produto produto : produtos){
            if(produto.getNome().equals(nomeProduto) && quantidade > 0){
                atendente.vender(produto, quantidade);
                int vendidos = this.inventario.get(produto);
                //soma o número de produtos vendidos aos que já foram
                this.inventario.put(produto, vendidos + quantidade);
                this.registrarVendasNoArquivo();
                this.registrarAtendentesNoArquivo();
                Main.EscreveNoLog("O atendente "+atendente.getNome()+" com cpf "+atendente.getCPF()+" vendeu "+quantidade+ " x "+produto.getNome());
            }
        }
    }
    /*registra uma única venda usando o ponteiro do objeto como referência
    public void registrarVenda(Atendente atendente, Produto produto, int quantidade){
        atendente.vender(produto, quantidade);
        int vendidos = this.inventario.get(produto);
        //soma o número de produtos vendidos aos que já foram
        this.inventario.put(produto, vendidos + quantidade);
        this.registrarVendasNoArquivo();
        this.registrarFuncionariosNoArquivo();
    }*/
    //registra uma única venda usando o nome do objeto como referência
    public Produto produtoQuerry(String nomeProduto){
        Set<Produto> produtos = this.inventario.keySet();
        for(Produto produto : produtos){
            if(produto.getNome().equals(nomeProduto)){
                return produto;
            }
        }
        return null;
    }
    //ANOTA CADA VENDA NO ARQUIVO APROPIADO
    public void registrarVendasNoArquivo(){
        //cria uma pasta da loja com o nome da loja
        File pastaDaLoja = new File("Lojas/"+this.nome+"/vendas");
        //verifica se o arquivo foi criado com sucesso
        boolean successful = pastaDaLoja.mkdirs();
        try( PrintWriter out = new PrintWriter("Lojas/"+this.nome+"/vendas/vendas.txt" )){
            Set<Produto> produtos = this.inventario.keySet();
            for(Produto produto : produtos){
                //registra em cada linha as informações dos produtos
                out.println(produto.getNome()+";"+produto.getPreco()+";"+this.inventario.get(produto));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //INICIALIZA OS PRODUTOS E QUANTOS FORAM VENDIDOS A PARTIR DE UM ARQUIVO
    public void lerVendasArquivo() throws IOException {
        //procura o arquivo com os registros de venda
        BufferedReader buffer = new BufferedReader(new FileReader("Lojas/"+this.nome+"/vendas/vendas.txt"));
        String aux = buffer.readLine();
        while (aux != null) {
            //Separa os campos em um vetor de strings
            String[] tmp = aux.split(";");
            String nome = tmp[0];
            float preco = Float.parseFloat(tmp[1]);
            Produto p = new Produto(nome, preco);
            this.inventario.put(p, Integer.parseInt(tmp[2]));
            aux = buffer.readLine();
        }
    }
    //ANOTA TODOS OS DADOS DE FUNCIONARIOS NO RESPECTIVO ARQUIVO
    public void registrarAtendentesNoArquivo(){
        File pastaDaLoja = new File("Lojas/"+this.nome+"/funcionarios");
        boolean successful = pastaDaLoja.mkdirs();
        try( PrintWriter out = new PrintWriter("Lojas/"+this.nome+"/funcionarios/funcionarioInfo.txt" )){
            for(Atendente atendente : this.atendentes){
                out.println(atendente.getNome()+";"+atendente.getCPF()+";"+atendente.getSenha()+";"+atendente.getComissao()+";"+atendente.getSomatorioDeVendas());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //RECUPERA FUNCIONARIOS DE UMA LOJA A PARTIR DE UM ARQUIVO
    public void lerAtendentesNoArquivo() throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader("Lojas/"+this.nome+"/funcionarios/funcionarioInfo.txt"));
        String aux = buffer.readLine();
        while (aux != null) {
            //Separa os campos em um vetor de strings
            String[] tmp = aux.split(";");
            String nome = tmp[0];
            String cpf = tmp[1];
            String senha = tmp[2];
            float comissao = Float.parseFloat(tmp[3]);
            float somatorioDeVendas = Float.parseFloat(tmp[4]);
            Atendente atendente = new Atendente(nome, cpf, senha, comissao, this.getNome());
            atendente.setSomatorioDeVendas(somatorioDeVendas);
            this.atendentes.add(atendente);
            aux = buffer.readLine();
        }
    }

    //LOGIN EXTRA-OFICIAL
    public Atendente loginAtendente(String cpf, String senha){
        for(Atendente atendente : this.atendentes){
            if(atendente.getCPF().equals(cpf) && atendente.getSenha().equals(senha)){
                return atendente;
            }
        }
        return null;
    }

}
