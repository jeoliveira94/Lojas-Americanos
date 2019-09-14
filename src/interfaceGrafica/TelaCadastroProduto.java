package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import base.Gerente;

public class TelaCadastroProduto extends JFrame implements ActionListener, KeyListener
	private Gerente gerente;
	private JPanel painel;
	private JLabel nomeGerente;
	private JLabel nomeLoja;
	private JLabel nomeProduto;
	private JLabel precoProduto;
	private JTextField campoNomeProduto;
	private JTextField campoPrecoProduto;
	private JButton cadastrar;
	private JButton voltar;
	private SpringLayout layout

	public TelaCadastroProduto(Gerente gerente){
		
		this.setTitle("Cadastro Produto");
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setResizable(false);
		this.setSize(500, 500);
		this.criarComponentes();
		this.adicionarComponentes();
		this.gerente = gerente;
		this.nomeGerente.setText(gerente.getNome());
		this.nomeLoja.setText(gerente.getLoja().getNome());
		this.setVisible(true);
	}

	private void criarComponentes() {
		layout = new SpringLayout();
		painel = new JPanel();
		painel.setLayout(layout);
		
		nomeGerente = new JLabel("Nome Gerente");
		nomeLoja = new JLabel("Nome Loja");
		nomeProduto = new JLabel("Nome Produto");
		precoProduto = new JLabel("Preço Produto R$");

		campoNomeProduto = new JTextField();
		campoPrecoProduto = new JTextField();
		campoNomeProduto.setPreferredSize(new Dimension(130, 30));
		campoPrecoProduto.setPreferredSize(new Dimension(130, 30));
		cadastrar = new JButton("Cadastrar");
		cadastrar.setPreferredSize(new Dimension(130, 30));
		voltar = new JButton("Voltar");

	}

	private void adicionarComponentes() {
		this.add(painel, BorderLayout.CENTER);
		painel.add(nomeGerente);
		painel.add(nomeLoja);
		painel.add(nomeProduto);
		painel.add(campoNomeProduto);
		painel.add(precoProduto);
		painel.add(campoPrecoProduto);
		painel.add(cadastrar);
		painel.add(voltar);
		
		cadastrar.addActionListener(this);
		voltar.addActionListener(this);
		
		
		int distanciaBotoes = 40;
		int distanciaLabels = 20;
		
		layout.putConstraint(SpringLayout.WEST, nomeGerente, 10, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, nomeGerente, 10, SpringLayout.NORTH, painel);
		
		layout.putConstraint(SpringLayout.WEST, nomeLoja, 10, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, nomeLoja, 10, SpringLayout.NORTH, nomeGerente);
		
		
		layout.putConstraint(SpringLayout.WEST, nomeProduto, 170, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, nomeProduto, 100, SpringLayout.NORTH, painel);
		
		layout.putConstraint(SpringLayout.WEST, campoNomeProduto, 0, SpringLayout.WEST, nomeProduto);
		layout.putConstraint(SpringLayout.NORTH, campoNomeProduto, distanciaLabels, SpringLayout.NORTH, nomeProduto);
		
		layout.putConstraint(SpringLayout.WEST, precoProduto, 0, SpringLayout.WEST, campoNomeProduto);
		layout.putConstraint(SpringLayout.NORTH, precoProduto, distanciaBotoes, SpringLayout.NORTH, campoNomeProduto);
		
		layout.putConstraint(SpringLayout.WEST, campoPrecoProduto, 0, SpringLayout.WEST, precoProduto);
		layout.putConstraint(SpringLayout.NORTH, campoPrecoProduto, distanciaLabels, SpringLayout.NORTH, precoProduto);
		
		layout.putConstraint(SpringLayout.WEST, cadastrar, 0, SpringLayout.WEST, campoPrecoProduto);
		layout.putConstraint(SpringLayout.NORTH, cadastrar, distanciaBotoes, SpringLayout.NORTH, campoPrecoProduto);
		
		
		layout.putConstraint(SpringLayout.EAST, voltar, -10, SpringLayout.EAST, painel);
		layout.putConstraint(SpringLayout.SOUTH, voltar, -10, SpringLayout.SOUTH, painel);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent evento) {
		if(evento.getSource() == cadastrar) {
			String nomeProduto = campoNomeProduto.getText();
			float precoProduto = Float.parseFloat(campoPrecoProduto.getText());
			try {
				this.gerente.cadastrarProduto(nomeProduto, precoProduto);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao Cadastrar Produto", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
			JOptionPane.showMessageDialog(null, "Produto Cadastrado");
			campoNomeProduto.setText("");
			campoPrecoProduto.setText("");
		}else if( evento.getSource() == voltar ) {
			this.dispose();
			TelaGerente telaGerente = new TelaGerente(this.gerente);
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if(keyEvent.getSource() ==  campoPrecoProduto){
			String caracteres="0987654321.";
			if(!caracteres.contains(keyEvent.getKeyChar()+"")){
				keyEvent.consume();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
