/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import interfaceGrafica.TelaLogin;
 
public class Main {

    /**
     * @param args the command line arguments
     */
	public static Loja loja;

    static {
        try {
            loja = new Loja("Lojas Americanos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Gerente gerente = new Gerente("Eduardo D. Oliveira", "22222222222", "123", loja);

    public static void main(String[] args) throws IOException {
        TelaLogin telaLogin = new TelaLogin();
    }
    
    public static void EscreveNoLog(String info){
        File pastaDaLoja = new File("Log");
        pastaDaLoja.mkdirs();
        try( PrintWriter out = new PrintWriter(new FileOutputStream(
                new File("Log/historico.txt"),
                true /* append = true */))){
            out.append(info);
            out.append("\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void gerarRelatorio(){
        File pastaDaLoja = new File("Relatorio");
        boolean successful = pastaDaLoja.mkdir();
        try( PrintWriter out = new PrintWriter("Relatorio/relatorio.txt" )){
            out.println("Nome Loja: "+ loja.getNome());
            float total = 0;
            float lucro = 0;
            int numAtendentes = 0;
            gerente.pagar();
            for(Atendente atendente : loja.getAtendentes()){
                numAtendentes++;
                atendente.pagar();
                total += atendente.getSomatorioDeVendas();
                lucro += atendente.getSomatorioDeVendas() - atendente.getSalario();
            }
            lucro -= gerente.getSalario();
            out.println("Total: R$ " + total);
            out.println("Lucro: R$ " + lucro + "(Total - Salários)");
            out.println();
            out.println("Nome Gerente: "+ Main.gerente.getNome());
            out.println("CPF gerente: "+ Main.gerente.getCPF());
            out.println("Número de funcionarios: "+numAtendentes);
            Main.gerente.pagar();
            out.println("Salário: R$ "+Main.gerente.getSalario());
            out.println("\nFuncionários: ");
            for(Atendente atendente : loja.getAtendentes()){
                out.println("Nome Atendente: "+ atendente.getNome());
                out.println("CPF Atendente: "+ atendente.getCPF());
                out.println("Comissão Atendente: "+ atendente.getComissao()*100+"%");
                out.println("Salário Atendente: R$" + atendente.getSalario());
                out.println();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    static public String arquivoToString(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
