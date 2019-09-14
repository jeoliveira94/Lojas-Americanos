package interfaceGrafica;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import base.Atendente;
import base.Gerente;
import base.ValidaCPF;

public class TelaCadastroAtendente extends JFrame implements ActionListener, KeyListener {
	
	private JPanel painel;
	private JLabel nomeGerente;
	private JLabel nomeLoja;
	private JLabel nome;
	private JLabel cpf;
	private JLabel senha;
	private JTextField campoNome;
	private JTextField campoCpf;
	private JPasswordField campoSenha;
	private JButton cadastrar;
	//private JButton limpar;
	private JButton voltar;
	private SpringLayout layout;
	private Gerente gerente;

	public TelaCadastroAtendente(Gerente gerente){
		this.setTitle("Cadastro Atendente");
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setResizable(false);
		this.setSize(500, 500);
		this.criarComponentes();
		this.adicionarComponentes();
		this.nomeGerente.setText(gerente.getNome());
		this.nomeLoja.setText(gerente.getLoja().getNome());
		this.gerente = gerente;
		this.setVisible(true);
	}

	private void criarComponentes() {
		int textFieldSize = 12;
		layout = new SpringLayout();
		painel = new JPanel();
		painel.setLayout(layout);
		nomeGerente = new JLabel("Nome Gerente");
		nomeLoja = new JLabel("Nome Loja");
		nome = new JLabel("Nome");
		campoNome = new JTextField();
		cpf = new JLabel("CPF");
		campoCpf = new JTextField();
		senha = new JLabel("Senha");
		campoSenha = new JPasswordField();
		cadastrar = new JButton("Cadastrar");
		campoNome.setPreferredSize(new Dimension(130, 30));
		campoCpf.setPreferredSize(new Dimension(130, 30));
		campoSenha.setPreferredSize(new Dimension(130, 30));
		cadastrar.setPreferredSize(new Dimension(130, 30));
		voltar = new JButton("Voltar");

	}

	private void adicionarComponentes() {
		this.add(painel, BorderLayout.CENTER);
		painel.add(nome);
		painel.add(campoNome);
		painel.add(cpf);
		painel.add(campoCpf);
		painel.add(senha);
		painel.add(campoSenha);
		painel.add(nomeGerente);
		painel.add(nomeLoja);
		painel.add(cadastrar);
		//painel.add(limpar);
		painel.add(voltar);


		campoCpf.addKeyListener(this);
		campoSenha.addKeyListener(this);
		cadastrar.addActionListener(this);
		//limpar.addActionListener(this);
		voltar.addActionListener(this);
		
		
		int distanciaBotoes = 40;
		int distaciaLabel = 20;
		
		layout.putConstraint(SpringLayout.WEST, nomeGerente, 10, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, nomeGerente, 10, SpringLayout.NORTH, painel);
		
		layout.putConstraint(SpringLayout.WEST, nomeLoja, 10, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, nomeLoja, 10, SpringLayout.NORTH, nomeGerente);
		
		layout.putConstraint(SpringLayout.WEST, nome, 160, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, nome, 100, SpringLayout.NORTH, painel);
		
		layout.putConstraint(SpringLayout.WEST, campoNome, 0, SpringLayout.WEST, nome);
		layout.putConstraint(SpringLayout.NORTH, campoNome, distaciaLabel, SpringLayout.NORTH, nome);
		
		layout.putConstraint(SpringLayout.WEST, cpf, 0, SpringLayout.WEST, campoNome);
		layout.putConstraint(SpringLayout.NORTH, cpf, distanciaBotoes, SpringLayout.NORTH, campoNome);
		
		layout.putConstraint(SpringLayout.WEST, campoCpf, 0, SpringLayout.WEST, cpf);
		layout.putConstraint(SpringLayout.NORTH, campoCpf, distaciaLabel, SpringLayout.NORTH, cpf);
		
		layout.putConstraint(SpringLayout.WEST, senha, 0, SpringLayout.WEST, campoCpf);
		layout.putConstraint(SpringLayout.NORTH, senha, distanciaBotoes, SpringLayout.NORTH, campoCpf);
		
		layout.putConstraint(SpringLayout.WEST, campoSenha, 0, SpringLayout.WEST, senha);
		layout.putConstraint(SpringLayout.NORTH, campoSenha, distaciaLabel, SpringLayout.NORTH, senha);
		
		layout.putConstraint(SpringLayout.WEST, cadastrar, 0, SpringLayout.WEST, campoSenha);
		layout.putConstraint(SpringLayout.NORTH, cadastrar, distanciaBotoes, SpringLayout.NORTH, campoSenha);

		
		layout.putConstraint(SpringLayout.EAST, voltar, -10, SpringLayout.EAST, painel);
		layout.putConstraint(SpringLayout.SOUTH, voltar, -10, SpringLayout.SOUTH, painel);

	}
	@Override
	public void actionPerformed(ActionEvent evento) {
		// TODO Auto-generated method stub
		if(evento.getSource() == cadastrar) {
			String nome = campoNome.getText();
			String cpf = campoCpf.getText();
			String senha = campoSenha.getText();
			if(ValidaCPF.isCPF(cpf)) {
				gerente.cadastrarAtendente(nome, cpf, senha, gerente.getLoja().getNome());
				campoNome.setText("");
				campoCpf.setText("");
				campoSenha.setText("");
				JOptionPane.showMessageDialog(null, "Atendente Cadastrado");
			}else {
				JOptionPane.showMessageDialog(null, "CPF Inválido", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		}else if( evento.getSource() == voltar ) {
			this.dispose();
			TelaGerente telaGerente = new TelaGerente(this.gerente);
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
		if(keyEvent.getSource() == campoCpf || keyEvent.getSource() == campoSenha){
			String caracteres="0987654321";
			if(!caracteres.contains(keyEvent.getKeyChar()+"")){
				keyEvent.consume();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {}

	@Override
	public void keyReleased(KeyEvent keyEvent) {}
}
