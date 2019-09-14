/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base;


public abstract class Funcionario implements Pagamento{
    private String nome;
    //username do login
    private String cpf;
    //senha do login para funcionarios
    private String senha;
    //SALÁRIO
    private float salario;
    
    private String nomeDaLoja;


    public Funcionario(String nome, String cpf, String senha){
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }
    public void setCPF(String cpf){
        this.cpf = cpf;
    }
    public String getCPF(){
        return cpf;
    }
    public void setSenha(String senha){
        this.senha = senha;
    }
    public String getSenha(){
        return senha;
    }
    public float getSalario() {
        return salario;
    }
    public void setSalario(float salario) {
        this.salario = salario;
    }
	public String getNomeDaLoja() {
		return nomeDaLoja;
	}
	public void setNomeDaLoja(String nomeDaLoja) {
		this.nomeDaLoja = nomeDaLoja;
	}


	public void pagamento(){}
}
