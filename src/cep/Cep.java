package cep;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;

public class Cep extends JFrame {

	private JPanel contentPane;
	private JTextField txtEndereco;
	private JLabel lblNewLabel_1;
	private JTextField txtCep;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JLabel lblNewLabel_4;
	private JComboBox cboUf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cep frame = new Cep();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cep() {
		setTitle("Endere\u00E7o");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cep.class.getResource("/img/mail.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("CEP");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(37, 30, 46, 14);
		contentPane.add(lblNewLabel);
		
		txtEndereco = new JTextField();
		txtEndereco.setBounds(103, 71, 294, 20);
		contentPane.add(txtEndereco);
		txtEndereco.setColumns(10);
		
		lblNewLabel_1 = new JLabel("UF");
		lblNewLabel_1.setBounds(37, 184, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtCep = new JTextField();
		txtCep.setColumns(10);
		txtCep.setBounds(103, 28, 72, 20);
		contentPane.add(txtCep);
		
		lblNewLabel_2 = new JLabel("Endere\u00E7o");
		lblNewLabel_2.setBounds(37, 74, 83, 14);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Bairro");
		lblNewLabel_3.setBounds(37, 110, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(103, 107, 294, 20);
		contentPane.add(txtBairro);
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(103, 142, 294, 20);
		contentPane.add(txtCidade);
		
		lblNewLabel_4 = new JLabel("Cidade");
		lblNewLabel_4.setBounds(37, 145, 46, 14);
		contentPane.add(lblNewLabel_4);
		
		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUf.setBounds(103, 180, 46, 22);
		contentPane.add(cboUf);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}

			
		});
		btnLimpar.setBounds(37, 216, 89, 23);
		contentPane.add(btnLimpar);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtCep.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe o Cep");
					txtCep.requestFocus();
				} else {
					pesquisarCep();
				}
			}
		});
		btnPesquisar.setBounds(202, 27, 103, 23);
		contentPane.add(btnPesquisar);
		
		// Validação do campo CEP
		RestrictedTextField validar = new RestrictedTextField(txtCep);
		validar.setOnlyNums(true);
		validar.setLimit(8);
	}
	
	// Método para pesquisar o cep
	private void pesquisarCep() {
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("https://viacep.com.br/ws/" + cep + "/xml/");
			 SAXReader xml = new SAXReader();
			 Document documento = xml.read(url);
			 Element root = documento.getRootElement();
			 for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			        Element element = it.next();
			        if (element.getQualifiedName().equals("logradouro")) {
			        	txtEndereco.setText(element.getText());
			        }
			        if (element.getQualifiedName().equals("bairro")) {
			        	txtBairro.setText(element.getText());
			        }
			        if (element.getQualifiedName().equals("localidade")) {
			        	txtCidade.setText(element.getText());
			        }
			        if (element.getQualifiedName().equals("uf")) {
			        	cboUf.setSelectedItem(element.getText());
			        }
			        if (element.getQualifiedName().equals("resultado")) {
			        	resultado = element.getText();
			        	if (resultado.equals("1")) {
			        		
			        	} else {
			        		JOptionPane.showMessageDialog(null, "Cep não encontrado");
							txtCep.requestFocus();
			        	}
			        }
			    }
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Método para limpar campos
	
	private void limparCampos() {
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem(null);
		txtCep.requestFocus();
	}
}
