import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ACMEDelivery {

	private Scanner entrada = null;
	private PrintStream saidaPadrao = System.out;

	private Cliente segr;
	private Entrega edo;
	private Clientela clientela;
	private CadastroEntregas VcadastroEntregas;
	private int quantidadeEntregas = 0;

	public ACMEDelivery() {
		try {
			BufferedReader streamEntrada = new BufferedReader(new FileReader("entrada.txt"));
			entrada = new Scanner(streamEntrada);
			PrintStream streamSaida = new PrintStream("saida.txt", StandardCharsets.UTF_8);
			System.setOut(streamSaida);
		} catch (Exception e) {
			System.out.println(e);
		}
		Locale.setDefault(Locale.ENGLISH);

		segr  = new Cliente("Marcelo", "marcelo.yamaguti@pucrs.br", "nao sei");
		edo = new Entrega(10, 0.0, "Nota 10 no boletim do nicolas", segr);
		clientela = new Clientela();
		VcadastroEntregas = new CadastroEntregas();
	}

	private void restauraES() {
		System.setOut(saidaPadrao);
		entrada = new Scanner(System.in);
	}

	public void executa() {
		puxaTudo();
		restauraES();
		pontoExtra();
		segredo();
	}

	private void pontoExtra() {
		menu();
		int opcao = entrada.nextInt();
		while (opcao!=0) {
			switch (opcao) {
				case 1:
					opcao1();
					break;
				case 2:
					opcao2();
					break;
				case 3:
					opcao3();
					break;
				case 4:
					System.out.println(clientela.toString());
					break;
				case 5:
					System.out.println(VcadastroEntregas.toString());
					break;
				case 6:
					System.out.println(clientela.toString());
					System.out.println(VcadastroEntregas.toString());
					break;
				default:
					break;
			}
			menu();
			opcao = entrada.nextInt();
		}
	}

	private void puxaTudo() {
		cadastraCliente();
		cadastraEntrega();
		quantidadeClientes();
		quantidadeEntregas();
		verCliente();
		verEntrega();
		dadosEntregaCliente();
		entregaMaiorValor();
		enderecoEntrega();
		somatorioCliente();
	}

	private void cadastraCliente() {
		String email;
		String nome;
		String end;

		email = entrada.nextLine();
		while (!email.equals("-1")) {
			nome = entrada.nextLine();
			end = entrada.nextLine();

			if(clientela.cadastraCliente(new Cliente(nome, email, end))) {
				System.out.println("1;" + email + ";" + nome + ";" + end);
			}
			email = entrada.nextLine();
		}
	}

	private void cadastraEntrega() {
		ArrayList<Integer> codigosCadastrados = new ArrayList<>();

		int codigo;
		double preco;
		String desc;
		String email;
		codigo = entrada.nextInt();

		while (codigo != -1) {
			preco = entrada.nextDouble();
			entrada.nextLine();
			desc = entrada.nextLine();
			email = entrada.nextLine();

			if(VcadastroEntregas.cadastraEntrega(new Entrega(codigo, preco, desc, clientela.pesquisaCliente(email)))) {
				System.out.println("2;" + codigo + ";" + preco + ";" + desc + ";" + email);
				quantidadeEntregas++;
			}

			codigo = entrada.nextInt();
		}
	}

	private void quantidadeClientes() {
		System.out.println("3;" + clientela.listaClientes.size());
	}

	private void quantidadeEntregas() {
		System.out.println("4;" + VcadastroEntregas.entregas.size());
	}

	private void verCliente() {
		boolean ver = true;
		entrada.nextLine();
		String email = entrada.nextLine();
		for(Cliente x : clientela.listaClientes) {
			if(email.equalsIgnoreCase(x.getEmail())) {
				System.out.println("5;" + x.getEmail() + ";" + x.getNome() + ";" + x.getEndereco());
				ver = false;
			}
		}
		if(ver) {
			System.out.println("5;Cliente inexistente");
		}
	}

	private void verEntrega() {
		boolean ver = true;
		int codigo = entrada.nextInt();
		for(Entrega e : VcadastroEntregas.entregas) {
			if(codigo == e.getCodigo()) {
				System.out.println("6;" + e.getCodigo() + ";" + e.getValor() + ";" + e.getDescricao() + ";" + e.getCliente().getEmail() + ";" + e.getCliente().getNome() + ";" + e.getCliente().getEndereco());
				ver = false;
			}
		}
		if(ver) {
			System.out.println("6;Entrega inexistente");
		}
	}

	private void dadosEntregaCliente() {
		boolean ver = true;
		entrada.nextLine();
		String email = entrada.nextLine();
		for(Cliente c : clientela.listaClientes) {
			if(email.equalsIgnoreCase(c.getEmail())) {
				c.retornaDadosEntrega();
				ver = false;
			}
		}
		if(ver) {
			System.out.println("7;Cliente inexistente");
		}
	}

	private void entregaMaiorValor() {
		Entrega a = new Entrega(0,0,"", null);
		double maiorValor = 0;
		if(VcadastroEntregas.entregas.isEmpty()) System.out.println("8;Entrega inexistente");
		for(Entrega e : VcadastroEntregas.entregas) {
			if(e.getValor()>maiorValor) {
				a=e;
				maiorValor=e.getValor();
			}
		}
		System.out.println("8;" + a.getCodigo() + ";" + a.getValor() + ";" + a.getDescricao());
	}

	private void enderecoEntrega() {
		boolean ver = true;
		double codigo = entrada.nextDouble();
		for(Entrega e : VcadastroEntregas.entregas) {
			if(codigo == e.getCodigo()) {
				System.out.println("9;" + e.getCodigo() + ";" + e.getValor() + ";" + e.getDescricao() + ";" + e.getCliente().getEndereco());
				ver = false;
			}
		}
		if(ver) {
			System.out.println("9;Entrega inexistente");
		}
	}

	private void somatorioCliente() {
		boolean ver = true;
		entrada.nextLine();
		String email = entrada.nextLine();
			for(Cliente c : clientela.listaClientes) {
				if(email.equalsIgnoreCase(c.getEmail())) {
					ver=false;
					if(c.temEntregas()) {
						String x = "10;" + c.getEmail() + ";" + c.getNome() + ";";
						String fim = String.format(x + "%.2f" , c.somatorioEntregas());
						System.out.printf(fim);
					} else {
						System.out.println("10;Entrega Inexistente");
					}
				}
			}
			if(ver) {
				System.out.println("10;Cliente inexistente");
			}

	}
	private void menu() {
		System.out.println("Insira a opcao desejada:");
		System.out.println("[1] Cadastrar um novo cliente. \n" +
				"[2] Cadastrar uma nova entrega. \n" +
				"[3] Cadastrar um novo cliente e uma entrega correspondente. \n" +
				"[4] Mostrar todos os clientes.  \n" +
				"[5] Mostrar todas as entregas. \n" +
				"[6] Mostrar todas informacoes do sistema. \n" +
				"[0] Sair do sistema." );
	}

	private void opcao1() {
		System.out.println("Insira o Nome, Email e Endereco do cliente desejado: ");
		entrada.nextLine();
		String nome = entrada.nextLine();
		String email = entrada.nextLine();
		String ende = entrada.nextLine();
		if(clientela.cadastraCliente(new Cliente(nome, email, ende))) {
			System.out.println("Cliente cadastrado!");
		} else {
			System.out.println("Cliente invalido!");
		}
	}

	private void opcao2() {
		System.out.println("Insira o Codigo, Preco, Descricao e o Email do cliente da entrega");
		int codigo = entrada.nextInt();
		double preco = entrada.nextDouble();
		entrada.nextLine();
		String desc = entrada.nextLine();
		String email = entrada.nextLine();
		Cliente c = clientela.pesquisaCliente(email);
		Entrega e = new Entrega(codigo, preco, desc, c);
		if(VcadastroEntregas.cadastraEntrega(e)) {
			c.adicionaEntrega(e);
			System.out.println("Entrega cadastrada!");
		} else {
			System.out.println("Entrega invalida!");
		}
	}

	private void opcao3() {
		System.out.println("Insira o nome, email e endereco em ordem, para o seu cliente:");
		entrada.nextLine();
		String nome = entrada.nextLine();
		String email = entrada.nextLine();
		String endereco = entrada.nextLine();
		Cliente c = new Cliente(nome, email, endereco);
		if(clientela.cadastraCliente(c)) {
			System.out.println("Cliente cadastrado!");
		} else {
			System.out.println("Cliente invalido");
		}
		System.out.println("Insira uma entrega correspondente (codigo, preco, descricao): ");
		int codigo = entrada.nextInt();
		double preco = entrada.nextDouble();
		entrada.nextLine();
		String desc = entrada.nextLine();
		System.out.println(desc);
		Entrega e = new Entrega(codigo, preco, desc, c);
		if(VcadastroEntregas.cadastraEntrega(e)) {
			System.out.println("Entrega registrada!");
			c.adicionaEntrega(e);

		} else {
			System.out.println("Entrega invalida!");
		}
	}

	private void segredo() {
		System.out.println(segr.a + clientela.b + edo.c + VcadastroEntregas.d + "<3");
	}


}
