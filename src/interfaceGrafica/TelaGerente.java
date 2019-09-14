package interfaceGrafica;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import base.Gerente;
import base.Main;


public class TelaGerente extends JFrame implements ActionListener{
	
	private JPanel painel;
	private JLabel nomeGerente;
	private JLabel nomeLoja;
	private JLabel fotoGerente;
	private JButton cadastrarAtendente;
	private JButton cadastrarProduto;
	private JButton gerarRelatorio;
	private JButton desconectar;
	private SpringLayout layout;
	private JButton alterarFoto;
	private Gerente gerente;
	private JScrollPane scrollPaneRelatorio;
	private JTextArea areaRelatorio;
	
	public TelaGerente(Gerente gerente){
		this.setTitle("Gerente");
		this.gerente = gerente;
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setResizable(false);
		this.setSize(500, 500);
		this.criarComponentes();
		this.adicionarComponentes();
		this.nomeGerente.setText(gerente.getNome());
		this.nomeLoja.setText(gerente.getLoja().getNome());
		this.setVisible(true);
	}

	private void criarComponentes() {
		int width = 200, height = 40;
		layout = new SpringLayout();
		painel = new JPanel();
		painel.setLayout(layout);
		nomeGerente = new JLabel("Nome Gerente");
		nomeLoja = new JLabel("Nome Loja");
		cadastrarAtendente = new JButton("Cadastrar Atendente");
		cadastrarAtendente.setPreferredSize(new Dimension(width, height));
		cadastrarProduto = new JButton("Cadastrar Produto");
		cadastrarProduto.setPreferredSize(new Dimension(width, height));
		gerarRelatorio = new JButton("Gerar Relatório");
		gerarRelatorio.setPreferredSize(new Dimension(width, height));
		desconectar = new JButton("Desconectar");
		alterarFoto = new JButton("Alterar Foto");
		alterarFoto.setPreferredSize(new Dimension(150, 20));
		
		areaRelatorio = new JTextArea();
		areaRelatorio.setEditable(false);
		scrollPaneRelatorio = new JScrollPane(areaRelatorio);
		areaRelatorio.setLineWrap(true);
		areaRelatorio.setWrapStyleWord(true);
		scrollPaneRelatorio.setPreferredSize(new Dimension(300, 500));
		
		fotoGerente = new JLabel();
		fotoGerente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		fotoGerente.setIcon(new ImageIcon(getClass().getResource("/imagens/foto-padrao-150x150.jpg")));
		fotoGerente.setPreferredSize(new Dimension(150, 150));

	}

	private void adicionarComponentes() {
		this.add(painel, BorderLayout.CENTER);
		painel.setBounds(0, 0, 750, 500);
		painel.add(nomeGerente);
		painel.add(fotoGerente);
		painel.add(nomeLoja);
		painel.add(cadastrarAtendente);
		painel.add(cadastrarProduto);
		painel.add(gerarRelatorio);
		painel.add(desconectar);


		cadastrarAtendente.addActionListener(this);
		cadastrarProduto.addActionListener(this);
		gerarRelatorio.addActionListener(this);
		desconectar.addActionListener(this);
		
		//constraints
		//nomeGerente
		layout.putConstraint(SpringLayout.WEST, nomeGerente, 10, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, nomeGerente, 10, SpringLayout.NORTH, painel);
		
		layout.putConstraint(SpringLayout.WEST, nomeLoja, 10, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, nomeLoja, 10, SpringLayout.NORTH, nomeGerente);
		
		
		int distanciaBotoes = 60;
		
		layout.putConstraint(SpringLayout.WEST, fotoGerente, 180, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, fotoGerente, 10, SpringLayout.NORTH, painel);
		
		layout.putConstraint(SpringLayout.WEST, alterarFoto, 0, SpringLayout.WEST, fotoGerente);
		layout.putConstraint(SpringLayout.NORTH, alterarFoto, 0, SpringLayout.SOUTH, fotoGerente);
		
		
		layout.putConstraint(SpringLayout.WEST, cadastrarAtendente, 150, SpringLayout.WEST, painel);
		layout.putConstraint(SpringLayout.NORTH, cadastrarAtendente, 200, SpringLayout.NORTH, painel);
		
		layout.putConstraint(SpringLayout.WEST, cadastrarProduto, 0, SpringLayout.WEST, cadastrarAtendente);
		layout.putConstraint(SpringLayout.NORTH, cadastrarProduto, distanciaBotoes, SpringLayout.NORTH, cadastrarAtendente);
		
		layout.putConstraint(SpringLayout.WEST, gerarRelatorio, 0, SpringLayout.WEST, cadastrarProduto);
		layout.putConstraint(SpringLayout.NORTH, gerarRelatorio, distanciaBotoes, SpringLayout.NORTH, cadastrarProduto);
		
		layout.putConstraint(SpringLayout.EAST, desconectar, -10, SpringLayout.EAST, painel);
		layout.putConstraint(SpringLayout.SOUTH, desconectar, -10, SpringLayout.SOUTH, painel);
		

	}
	@Override
	public void actionPerformed(ActionEvent evento) {
		if(evento.getSource() == cadastrarAtendente) {
			this.dispose();
			TelaCadastroAtendente telaCadastroAtendente = new TelaCadastroAtendente(this.gerente);
		}else if(evento.getSource() == cadastrarProduto) {
			this.dispose();
			TelaCadastroProduto telaCadastroProduto = new TelaCadastroProduto(this.gerente);
		}else if(evento.getSource() == gerarRelatorio) {
			Main.gerarRelatorio();
            String relatorio = null;
            try {
                relatorio = Main.arquivoToString("Relatorio/relatorio.txt", StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            areaRelatorio.setText(relatorio);
			JOptionPane.showMessageDialog(null, scrollPaneRelatorio, "Relatório", JOptionPane.INFORMATION_MESSAGE);
		}else if(evento.getSource() ==  desconectar) {
			this.gerente.pagar();
			this.dispose();
			TelaLogin telaLogin = new TelaLogin();
		}
	}


}
