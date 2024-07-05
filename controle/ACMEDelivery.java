package controle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import base.Cliente;
import base.Entrega;

public class ACMEDelivery {

	private Scanner entrada;
	private Clientela clientela;
	private CadastroEntregas cadastroEntregas;

	public ACMEDelivery() {
		try {
			entrada = new Scanner(new BufferedReader(new FileReader("arquivoentrada.txt")));
			System.setOut(new PrintStream("arquivosaida.txt", StandardCharsets.UTF_8));
		} catch (Exception e) {}

		Locale.setDefault(Locale.ENGLISH);

		clientela = new Clientela();
		cadastroEntregas = new CadastroEntregas();
	}

	private void restauraES() {
		System.setOut(System.out);
		entrada = new Scanner(System.in);
	}

	public void executa() {
		getAll();
		restauraES();
		// pontoExtra();
	}

	private void pontoExtra() {
		try {
			menu();
			int opcao = entrada.nextInt();
			while (opcao!=0) {
				switch (opcao) {
					case 1 -> opcao1();
					case 2 -> opcao2();
					case 3 -> opcao3();
					case 4 -> System.out.println(clientela.toString());
					case 5 -> System.out.println(cadastroEntregas.toString());
					case 6 -> System.out.println(clientela.toString() + "\n " + cadastroEntregas.toString());
				}
				menu();
				opcao = entrada.nextInt();
			}
		} catch(Exception e) {} 
		finally {entrada.close();}
	}

	private void getAll() {
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
		String email, nome, end;

		email = entrada.nextLine();
		while (!email.equals("-1")) {
			nome = entrada.nextLine();
			end = entrada.nextLine();

			if(clientela.cadastraCliente(new Cliente(nome, email, end))) {
				System.out.println(String.format("1;%s;%s;%s", email, nome, end));
			}
			email = entrada.nextLine();
		}
	}

	private void cadastraEntrega() {
		int codigo;
		double preco;
		String desc;
		String email;
		codigo = Integer.parseInt(entrada.nextLine());

		while (codigo != -1) {
			preco = Double.parseDouble(entrada.nextLine());
			desc = entrada.nextLine();
			email = entrada.nextLine();

			if(cadastroEntregas.cadastraEntrega(new Entrega(codigo, preco, desc, clientela.pesquisaCliente(email)))) {
				System.out.println("2;" + codigo + ";" + preco + ";" + desc + ";" + email);
			}

			codigo = Integer.parseInt(entrada.nextLine());
		}
	}

	private void quantidadeClientes() {
		System.out.println("3;" + clientela.getListaCliente().size());
	}

	private void quantidadeEntregas() {
		System.out.println("4;" + cadastroEntregas.getListaEntregas().size());
	}

	private void verCliente() {
		AtomicBoolean ver = new AtomicBoolean(true);
		String email = entrada.nextLine();
		clientela.getListaCliente().stream().filter(x -> email.equalsIgnoreCase(x.getEmail())).
		forEach(x -> {System.out.println("5;" + x.getEmail() + ";" + x.getNome() + ";" + x.getEndereco()); ver.set(false);});
		if(ver.get()) System.out.println("5;Cliente inexistente");
	}

	private void verEntrega() {
		AtomicBoolean ver = new AtomicBoolean(true);
		int codigo = Integer.parseInt(entrada.nextLine());
		cadastroEntregas.getListaEntregas().stream().filter(x -> codigo == x.getCodigo()).
		forEach(e -> {System.out.println(String.format("6;%d;%.2f;%s;%s;%s;%s", e.getCodigo(), e.getValor(), e.getDescricao(), e.getCliente().getEmail(), e.getCliente().getNome(), e.getCliente().getEndereco())); ver.set(false);});
		if(ver.get())System.out.println("6;Entrega inexistente");
	}

	private void dadosEntregaCliente() {
		AtomicBoolean ver = new AtomicBoolean(true);
		String email = entrada.nextLine();
		clientela.getListaCliente().stream().filter(x -> email.equalsIgnoreCase(x.getEmail()))
		.forEach(e -> {e.retornaDadosEntrega(); ver.set(false);});
		if(ver.get()) System.out.println("7;Cliente inexistente");
	}

	private void entregaMaiorValor() {
		Optional<Entrega> entregacomMaiorValor = 
		cadastroEntregas.getListaEntregas().stream()
		.max(Comparator.comparing(Entrega::getValor));
		if(entregacomMaiorValor.isPresent()) {
		System.out.println("8;" + entregacomMaiorValor.get().getCodigo() +
		";" + entregacomMaiorValor.get().getValor() + 
		";" + entregacomMaiorValor.get().getDescricao());
		} else System.out.println("8;Entrega inexistente");
	}

	private void enderecoEntrega() {
		AtomicBoolean ver = new AtomicBoolean(true);
		double codigo = Double.parseDouble(entrada.nextLine());
		cadastroEntregas.getListaEntregas().stream().filter(x -> codigo == x.getCodigo())
		.forEach(e -> {System.out.println(String.format("9;%d;%.2f;%s;%s", e.getCodigo(), e.getValor(), e.getDescricao(), e.getCliente().getEndereco())); ver.set(false);});
		if(ver.get()) System.out.println("9;Entrega inexistente");
	}

	private void somatorioCliente() {
		boolean ver = true;
		String email = entrada.nextLine();
			for(Cliente c  : clientela.getListaCliente()) { if(email.equalsIgnoreCase(c.getEmail())) {
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
		System.out.println("Insira a opcao desejada :"); 
		System.out.println("[1] Cadastrar um novo cliente. \n" +
				  		   "[2] Cadastrar uma nova entrega. \n" +
				  		   "[3] Cadastrar um novo cliente e uma entrega correspondente. \n" +
				  		   "[4] Mostrar todos os clientes.  \n" +
				  		   "[5] Mostrar todas as entregas. \n" +
				  		   "[6] Mostrar todas informacoes do sistema. \n" +
				  		   "[0] Sair do sistema." );
	}

	private void opcao1() {
		System.out.println("Insira o Nome, Email e Endereco do cliente desejado : "); entrada.nextLine();
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
		if(cadastroEntregas.cadastraEntrega(e)) {
			c.adicionaEntrega(e);
			System.out.println("Entrega cadastrada!");
		} else {
			System.out.println("Entrega invalida!");
		}
	}

	private void opcao3() {
		System.out.println("Insira o nome, email e endereco em ordem, para o seu cliente :"); entrada.nextLine();
		String nome = entrada.nextLine();
		String email = entrada.nextLine();
		String endereco = entrada.nextLine();
		Cliente c = new Cliente(nome, email, endereco);
		if(clientela.cadastraCliente(c)) {
			System.out.println("Cliente cadastrado!");
		} else {
			System.out.println("Cliente invalido");
		}
		System.out.println("Insira uma entrega correspondente (codigo, preco, descricao) : "); int codigo = entrada.nextInt();
		double preco = entrada.nextDouble();
		entrada.nextLine();
		String desc = entrada.nextLine();
		System.out.println(desc);
		Entrega e = new Entrega(codigo, preco, desc, c);
		if(cadastroEntregas.cadastraEntrega(e)) {
			System.out.println("Entrega registrada!");
			c.adicionaEntrega(e);

		} else {
			System.out.println("Entrega invalida!");
		}
	}
}
