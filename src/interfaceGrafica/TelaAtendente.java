package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import base.Atendente;
import base.Main;

import base.*;

public class TelaAtendente extends JFrame implements ActionListener, KeyListener{

    private JPanel painel;
    private JLabel nomeAtendente;
    private JLabel nomeLoja;
    private JLabel nomeProduto;
    private JLabel quantidade;
    private JTextField campoNomeProduto;
    private JTextField campoQuantidade;
    private JButton adicionar;
    private JButton limpar;
    private JButton finalizar;
    private JLabel total;
    private JLabel campoTotal;
    private JButton sair;
    private SpringLayout layout;
    private JTextArea listaDeItens;
    private JScrollPane scrollLista;
    private ArrayList<String> listaProdutos;
    private ArrayList<Integer> quantidadeProdutos;
    private Atendente atendente;
    float labelPreco = 0;

    public TelaAtendente(Atendente atendente){
        this.setTitle("Caixa Atendente");
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.setResizable(false);
        this.setSize(500, 500);
        this.criarComponentes();
        this.adicionarComponentes();
        this.nomeAtendente.setText(atendente.getNome());
        this.nomeLoja.setText(atendente.getNomeDaLoja());
        this.atendente = atendente;

        this.setVisible(true);
    }

    private void criarComponentes() {

        layout = new SpringLayout();
        painel = new JPanel();
        painel.setLayout(layout);
        nomeAtendente = new JLabel("Nome Atendente");
        nomeLoja = new JLabel("Nome Loja");
        quantidade = new JLabel("Quantidade");
        nomeProduto = new JLabel("Nome Produto");
        campoNomeProduto = new JTextField();
        int height = 30, width = 130;
        campoNomeProduto.setPreferredSize(new Dimension(width, height));
        campoQuantidade = new JTextField();
        campoQuantidade.setPreferredSize(new Dimension(width, height));

        adicionar = new JButton("Adicionar");
        adicionar.setPreferredSize(new Dimension(width, height));
        limpar = new JButton("Limpar");
        total = new JLabel("Total R$");
        campoTotal = new JLabel("00,00");
        finalizar = new JButton("Finalizar");
        sair = new JButton("Sair");


        listaDeItens = new JTextArea();
        listaDeItens.setText("");
        listaDeItens.setEditable(false);
        scrollLista = new JScrollPane(listaDeItens);
        listaDeItens.setLineWrap(true);
        listaDeItens.setWrapStyleWord(true);
        scrollLista.setPreferredSize(new Dimension(340, 250));


        listaProdutos = new ArrayList<>();
        quantidadeProdutos = new ArrayList<>();


    }

    private void adicionarComponentes() {
        this.add(painel, BorderLayout.CENTER);
        painel.add(nomeAtendente);
        painel.add(nomeLoja);
        painel.add(campoNomeProduto);
        painel.add(campoQuantidade);
        painel.add(adicionar);
        painel.add(nomeProduto);
        painel.add(quantidade);
        painel.add(scrollLista);
        painel.add(limpar);
        painel.add(finalizar);
        painel.add(total);
        painel.add(campoTotal);
        painel.add(sair);

        campoQuantidade.addKeyListener(this);
        adicionar.addActionListener(this);
        limpar.addActionListener(this);
        finalizar.addActionListener(this);
        sair.addActionListener(this);

        layout.putConstraint(SpringLayout.WEST, nomeAtendente, 10, SpringLayout.WEST, painel);
        layout.putConstraint(SpringLayout.NORTH, nomeAtendente, 10, SpringLayout.NORTH, painel);

        layout.putConstraint(SpringLayout.WEST, nomeLoja, 0, SpringLayout.WEST, nomeAtendente);
        layout.putConstraint(SpringLayout.NORTH, nomeLoja, 10, SpringLayout.NORTH, nomeAtendente);

        layout.putConstraint(SpringLayout.WEST, nomeProduto, 10, SpringLayout.WEST, painel);
        layout.putConstraint(SpringLayout.NORTH, nomeProduto, 100, SpringLayout.NORTH, painel);

        layout.putConstraint(SpringLayout.WEST, campoNomeProduto, 0, SpringLayout.WEST, nomeProduto);
        layout.putConstraint(SpringLayout.NORTH, campoNomeProduto, 20, SpringLayout.NORTH, nomeProduto);

        layout.putConstraint(SpringLayout.WEST, quantidade, 0, SpringLayout.WEST, campoNomeProduto);
        layout.putConstraint(SpringLayout.NORTH, quantidade, 20, SpringLayout.SOUTH, campoNomeProduto);
        layout.putConstraint(SpringLayout.WEST, campoQuantidade, 0, SpringLayout.WEST, quantidade);
        layout.putConstraint(SpringLayout.NORTH, campoQuantidade, 20, SpringLayout.SOUTH, quantidade);

        layout.putConstraint(SpringLayout.WEST, adicionar, 0, SpringLayout.WEST, campoQuantidade);
        layout.putConstraint(SpringLayout.NORTH, adicionar, 20, SpringLayout.SOUTH, campoQuantidade);

        layout.putConstraint(SpringLayout.EAST, scrollLista, -10, SpringLayout.EAST, painel);
        layout.putConstraint(SpringLayout.NORTH, scrollLista, 0, SpringLayout.NORTH, nomeProduto);

        layout.putConstraint(SpringLayout.EAST, campoTotal, 0, SpringLayout.EAST, scrollLista);
        layout.putConstraint(SpringLayout.NORTH, campoTotal, 0, SpringLayout.SOUTH, scrollLista);

        layout.putConstraint(SpringLayout.EAST, total, -20, SpringLayout.WEST, campoTotal);
        layout.putConstraint(SpringLayout.NORTH, total, 0, SpringLayout.NORTH, campoTotal);

        layout.putConstraint(SpringLayout.EAST, finalizar, 0, SpringLayout.EAST, campoTotal);
        layout.putConstraint(SpringLayout.NORTH, finalizar, 10, SpringLayout.SOUTH, campoTotal);

        layout.putConstraint(SpringLayout.EAST, limpar, -20, SpringLayout.WEST, finalizar);
        layout.putConstraint(SpringLayout.NORTH, limpar, 0, SpringLayout.NORTH, finalizar);

        layout.putConstraint(SpringLayout.EAST, sair, -10, SpringLayout.EAST, painel);
        layout.putConstraint(SpringLayout.SOUTH, sair, -10, SpringLayout.SOUTH, painel);


    }
    @Override
    public void actionPerformed(ActionEvent evento) {
        if(evento.getSource() == adicionar) {
            String nomeProduto = campoNomeProduto.getText();
            String quantidade = campoQuantidade.getText();
            if(null != Main.loja.produtoQuerry(nomeProduto)) {
    
                quantidadeProdutos.add(Integer.parseInt(quantidade));
                listaProdutos.add(nomeProduto);
                Produto p = Main.loja.produtoQuerry(nomeProduto);
                String preco = "R$ " + p.getPreco();
                String total = "R$ " + p.getPreco() * Integer.parseInt(quantidade);
                this.labelPreco += p.getPreco() * Integer.parseInt(quantidade);
                campoTotal.setText(String.valueOf(this.labelPreco));
                String addNaLista = "";
                addNaLista += listaDeItens.getText();
                String tab = "\t", enter = "\n", espaco = "   ";

                addNaLista += nomeProduto += tab += preco += tab += quantidade += espaco += total += enter;
                listaDeItens.setText(addNaLista);
                campoNomeProduto.setText("");
                campoQuantidade.setText("");
            }else {
                JOptionPane.showMessageDialog(null, "Produto Não Cadastrado", "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }else if(evento.getSource() == limpar) {
            quantidadeProdutos.clear();
            listaProdutos.clear();
            listaDeItens.setText("");
            campoTotal.setText("00.00");
            this.labelPreco = 0;
        }else if( evento.getSource() == finalizar ) {
            campoTotal.setText("00.00");
            this.labelPreco = 0;
            if (listaProdutos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lista Vazia");
            }else {
                Main.loja.registrarVendas(listaProdutos, quantidadeProdutos, this.atendente);
                JOptionPane.showMessageDialog(null, "Comprar Finalizada");
                listaDeItens.setText("");
                listaProdutos.clear();
                quantidadeProdutos.clear();
            }
        }else if( evento.getSource() == sair ) {
            this.atendente.pagar();
            this.dispose();
            TelaLogin telaLogin = new TelaLogin();
        }

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if(keyEvent.getSource() == campoQuantidade ){
            String caracteres="9876543210";
            if(!caracteres.contains(keyEvent.getKeyChar()+"")){
                keyEvent.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

