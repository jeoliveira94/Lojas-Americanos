package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import base.*;

public class TelaLogin extends JFrame implements ActionListener, KeyListener
	private JPanel painel;
	private JLabel cpf;
	private JTextField campoCpf;
	private JLabel senha;
	private JPasswordField campoSenha;
	private JButton login;
	private JButton sair;
	private JLabel labelLogo;
	private SpringLayout layout;

	public TelaLogin() {
		this.setTitle("Login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(500, 500);
		this.criarComponentes();
		this.adicionarComponentes();
		this.setVisible(true);

	}
	public void criarComponentes() {
		layout = new SpringLayout();
		painel = new JPanel();
		painel.setLayout(layout);
		labelLogo = new JLabel();
		labelLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelLogo.setIcon(new ImageIcon(getClass().getResource("/imagens/logoEmpresa.png")));
		labelLogo.setPreferredSize(new Dimension(500, 200));
		cpf = new JLabel("CPF");
		senha = new JLabel("Senha");
		int tamanhoTextField = 12;	
		campoCpf = new JTextField(tamanhoTextField);
		campoSenha = new JPasswordField(tamanhoTextField);
		login = new JButton("Login");
		sair = new JButton("Sair");
		

	}

	public void adicionarComponentes() {
		this.add(painel, BorderLayout.CENTER);
		painel.add(labelLogo);
		painel.add(cpf);
		painel.add(campoCpf);
		painel.add(senha);
		painel.add(campoSenha);
		painel.add(login);
		painel.add(sair);
		
		campoSenha.addKeyListener(this);
		campoCpf.addKeyListener(this);
		login.addActionListener(this);
		sair.addActionListener(this);

		layout.putConstraint(SpringLayout.WEST, labelLogo, 0, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, labelLogo, 0, SpringLayout.NORTH, painel);

		layout.putConstraint(SpringLayout.WEST, cpf, 120, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, cpf, 200, SpringLayout.NORTH, painel);

		layout.putConstraint(SpringLayout.WEST, campoCpf, 60, SpringLayout.WEST, cpf);
		layout.putConstraint(SpringLayout.NORTH, campoCpf, 200, SpringLayout.NORTH, painel);

		layout.putConstraint(SpringLayout.WEST, senha, 0, SpringLayout.WEST, cpf);
		layout.putConstraint(SpringLayout.NORTH, senha, 20, SpringLayout.NORTH, cpf);

		layout.putConstraint(SpringLayout.WEST, campoSenha, 60, SpringLayout.WEST, senha);
		layout.putConstraint(SpringLayout.NORTH, campoSenha, 20, SpringLayout.NORTH, campoCpf);

		layout.putConstraint(SpringLayout.WEST, login, 0, SpringLayout.WEST, campoSenha);
		layout.putConstraint(SpringLayout.NORTH, login, 20, SpringLayout.NORTH, campoSenha);	

		layout.putConstraint(SpringLayout.EAST, sair, -1, SpringLayout.EAST, campoSenha);
		layout.putConstraint(SpringLayout.NORTH, sair, 20, SpringLayout.NORTH, campoSenha);



	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		if(evento.getSource() == login) {
			String cpf = "00000000000";
			String senha = "";
			try {
				cpf = campoCpf.getText();
				senha = campoSenha.getText();
			}catch(Exception e) {
				e.printStackTrace();
				cpf = "00000000000";
			}

			if(ValidaCPF.isCPF(cpf)) {
				if (Main.gerente.getCPF().equals(cpf) && Main.gerente.getSenha().equals(senha)) {
					this.dispose();
					TelaGerente telaGerente = new TelaGerente(Main.gerente);
					Main.EscreveNoLog("O gerente " + Main.gerente.getNome() + " logou");
				}else{
					Atendente atendente = Main.loja.loginAtendente(cpf, senha);
					if(atendente != null) {
						this.dispose();
						TelaAtendente telaAtendente = new TelaAtendente(atendente);
						Main.EscreveNoLog("O atendente " + atendente.getNome() + " logou");
					}else {
						JOptionPane.showMessageDialog(null, "Funcionário Não Cadastrado");
					}
				}
			}else {
				JOptionPane.showMessageDialog(null, "CPF Inválido", "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		}else if(evento.getSource() == sair) {
			this.dispose();
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
