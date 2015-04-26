package poo.tarefas.tarefa04;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import operacoes.Conta;

//A MÃ¡quina Z Ã© um computador capaz de executar as seguintes instruÃ§Ãµes:
//	AtribuiÃ§Ã£o
//		Sintaxe:
//			(variÃ¡vel a | variÃ¡vel b | variÃ¡vel c) = (valor inteiro)
//	Soma
//		Sintaxe:
//			c += (valor inteiro | variÃ¡vel a | variÃ¡vel b)
//	Escrita
//		Sintaxe:
//			escrever
//			(ao fazer isso, Ã© escrito o valor de c)
//
public class InterpretadorMaquinaZ {
	private Pattern validCommandLinePattern = Pattern.compile("^(([A-Za-z]{1,}\\s?([+]?=)\\s?([A-Za-z]{1,}|[0-9]{1,})|(int\\s[A-Za-z]{1,}));|(//.*))$");
	private Pattern validVariableDeclarationPattern = Pattern.compile("^\\s{0,}[A-Za-z]{1,}\\s{1,}[A-Za-z]{1}([A-Za-z0-9]{1,})?\\s{0,};$");
	private Pattern validVariableDeclarationWithAttributionPattern = Pattern.compile("^(\\s{0,}[A-Za-z]{1,}\\s{1,}[A-Za-z]{1}([A-Za-z0-9]{1,})?\\s{0,})=\\s{0,}(([0-9]{1,})|(\"\\s{0,}[A-za-z0-9]{0,}\\s{0,}\"))\\s{0,};$");
	private Pattern validVariableValueAttributionPattern = Pattern.compile("^(((([A-Za-z]{1,}\\s?([+]?=)\\s?(([A-Za-z]{1,}[A-Za-z0-9]{0,})|([0-9]{1,})|(\"[A-Za-z0-9 ]{1,}\")))|(int\\s[A-Za-z]{1,}[A-Za-z0-9]{0,}));(\\s{0,}//.*)?)|(//.*))$");
	private Pattern validWritePattern = Pattern.compile("escrever(\\(((\\s{0,}([A-Za-z]){1,}[A-Za-z0-9]{0,})|(\"[A-Za-z0-9 ]{1,}\"))\\s{0,}(([+]|[,])((\\s{0,}([A-Za-z]){1,}[A-Za-z0-9]{0,})|(\"[A-Za-z0-9 ]{1,}\"))){0,}\\s{0,}\\))?");
	private List<Variavel> variaveis = new ArrayList<Variavel>();
	private List<String> tipos = new ArrayList<String>();

	public InterpretadorMaquinaZ() {
		tipos.add("string");
		tipos.add("int");
		tipos.add("double");
	}

	public void executar(String codigoFonte) {
		for (String linha : codigoFonte.split("\n")) {
			if(!validCommandLinePattern.matcher(linha).matches()){
				System.out.println("O código contém erros.");
				return;
			}
			else{
				executarLinha(linha);
			}
			
		}
	}

	public void executar(List<String> linhasDeCodigo) {
		for (String linha : linhasDeCodigo) {
			executarLinha(linha);
		}
	}
	
	public Variavel encontrarVariavel(String nomeVariavel){
		for(Variavel variavel : variaveis){
			if(variavel.getVariavel().equals(nomeVariavel)){
				return variavel;
			}
		}
		return null;
	}

	private void executarLinha(String linha) {
		try{
			if(validVariableDeclarationPattern.matcher(linha).matches() || validVariableDeclarationWithAttributionPattern.matcher(linha).matches()) // Declaração de variável
			{
				String nomeVariavel;
				String valorVariavel;
				String tipoVariavel;

					if(validVariableDeclarationWithAttributionPattern.matcher(linha).matches()){
						nomeVariavel = linha.split(" ")[1].split("=")[0].replace(" ", "");
						valorVariavel = linha.split("=")[1];
						tipoVariavel = linha.split(" ")[0];
						if(Pattern.compile("^\\s{0,}\"(\\s{0,}[A-Za-z0-9]{0,}\\s{0,})\"\\s{0,};").matcher(linha.split("=")[1]).matches()){//Caso seja uma atribuição de string
							if(tipos.contains(tipoVariavel) && tipoVariavel.equals("string") ){
								valorVariavel = Pattern.compile("^\\s{0,}\"(\\s{0,}[A-Za-z0-9]{0,}\\s{0,})\"\\s{0,};").matcher(valorVariavel).group(1);
								variaveis.add(new Variavel(nomeVariavel,tipoVariavel,valorVariavel));
							}
							else if(tipos.contains(tipoVariavel)){
								System.out.println("Cannot set text to "+tipoVariavel);
							}
							else{
								System.out.println(tipoVariavel + "is not valid.");
							}
						}
						else{
							if(tipos.contains(tipoVariavel) && !tipoVariavel.equals("string") ){
								valorVariavel = String.valueOf(Conta.fazerConta(valorVariavel));
								variaveis.add(new Variavel(nomeVariavel,tipoVariavel,valorVariavel));
							}
							else if(tipos.contains(tipoVariavel)){
								System.out.println("Cannot set text to "+tipoVariavel);
							}
							else{
								System.out.println(tipoVariavel + "is not valid.");
							}	
						}
						
					}
					else{//Caso seja uma declaração sem atribuição
						nomeVariavel = linha.split(" ")[1].replace(" ", "").replace(";","");
						tipoVariavel = linha.split(" ")[0];
						switch(tipoVariavel.toLowerCase())
						{
							case"int":
								valorVariavel = "0";
								break;
							case"float":
								valorVariavel = "0.0";
								break;
							default:
								valorVariavel = "";
								break;
						}
						
						variaveis.add(new Variavel(nomeVariavel,tipoVariavel, valorVariavel));
					}

			}
			else if(validVariableValueAttributionPattern.matcher(linha).matches()){//Atribuição de valor
				if(!linha.split("[+]?=")[1].split(";")[0].contains("\"")){
					String[] variaveisConta = linha.split("[+]?=")[1].replace(";", "").split(" ");
					String linhaTemp = linha;
					Variavel variavelTemp;
					Variavel variavel = encontrarVariavel(linha.split("[+]?=")[0].replace(" ", "").replace(";", ""));
					String contaOuValor = linha.split("[+]?=")[1].replace(" ", "").replace(";", "");
					
					for(int i =0;i<variaveisConta.length;i++){
						variavelTemp = encontrarVariavel(variaveisConta[i]);
						if(variavelTemp != null){
							linhaTemp = linhaTemp.replace(variaveisConta[i], variavelTemp.getValorEmString());
						}
					}
					
					
					if(variavel != null){
						if(variavel.getTipo().toLowerCase().equals("double") || variavel.getTipo().toLowerCase().equals("int") ){
							if(Pattern.compile("[^+]=").matcher(linha).matches()){//caso seja uma atribuição
								variavel.setValor( Conta.fazerConta(contaOuValor) );
							}
							else{ // Caso seja uma atribuição com adição
								variavel.setValor( variavel.getValorDouble() + Conta.fazerConta(contaOuValor) );
							}
						}
						else{
							System.out.println("Can't set values of type '"+variavel.getTipo()+"' to int or double");
						}
					}
					else{
						System.out.println("Variable not declared");
					}
				}
				else{ // Caso seja uma string


					String[] variaveisConta = linha.split("[+]?=")[1].replace(";", "").split("(?![A-Za-z0-9 ]{0,}\")\\s{1,}(?![A-Za-z0-9 ]{0,}\")");
					Variavel variavelAtribuida = encontrarVariavel(linha.split("[+]?=")[0].replace(" ", ""));
					Variavel variavelTemp;
					String resultado = "";
					Double resultadoDouble = 0.0;
					for(int i =0;i<variaveisConta.length;i++){
						if(Pattern.compile("([A-Za-z0-9 ]{0,}\")([A-Za-z0-9 ]{0,}\")").matcher(linha).matches()){
							if(Pattern.compile("([A-Za-z0-9 ]{0,}\")([A-Za-z0-9 ]{0,}\")").matcher(variaveisConta[i]).matches()){
								resultado+= variaveisConta[i].replace("\"", "");
							}
							else
							{
								variavelTemp = encontrarVariavel(variaveisConta[i]);
								if(variavelTemp != null){
									resultado+= variavelTemp.getValorEmString();
								}
							}
						}
						else{
							variavelTemp = encontrarVariavel(variaveisConta[i]);
							if(variavelTemp != null){
								resultado+= variavelTemp.getValorEmString();
							}
						}
					}
					
					if(variavelAtribuida != null ){
						if(variavelAtribuida.getTipo().toLowerCase().equals("string")){
							if(Pattern.compile("[^+]=").matcher(linha).matches()) // Caso seja uma atribuição
							{
								variavelAtribuida.setValor(resultado == null ? Double.toString(resultadoDouble) : resultado);
							}
							else // Caso seja uma adição
							{
								variavelAtribuida.setValor(resultado == null ? Double.toString(variavelAtribuida.getValorDouble() + resultadoDouble) : variavelAtribuida.getValorEmString() +  resultado);
							}
						}
						else{
							System.out.println("Can't set values of type '"+variavelAtribuida.getTipo()+"' to string");
						}
					}
					else if(validWritePattern.matcher(linha).matches()){//Escrever na tela
						if(Pattern.compile("^\\s{0,}escrever\\s{0,};").matcher(linha).matches())//Escrever tudo
						{
							for(Variavel variavel : variaveis){
								System.out.println(variavel.getVariavel() + " = " + variavel.getValorEmString());
							}
						}
						else if(Pattern.compile("^\\s{0,}escrever[(]\\s{0,}(([A-Za-z]{1,}[A-Za-z0-9]{0,})|(\"[A-Za-z0-9 ]{0,}\")|([0-9]{1,}))(\\s{1,}[+]\\s{1,}(([A-Za-z]{1,}[A-Za-z0-9]{0,})|(\"[A-Za-z0-9 ]{0,}\")|([0-9]{1,}))){0,}\\s{0,}[)]\\s{0,};").matcher(linha).matches()){
							String linhaTemp = Pattern.compile("^\\s{0,}escrever[(]").matcher(linha).replaceAll("");
							String[] elements = linhaTemp.split("\\s{1,}[+]\\s{1,}");
							StringBuilder resultEmString;
							boolean fazerConta = true;
							linhaTemp = Pattern.compile("[)]\\s{0,};\\s{0,}$").matcher(linha).replaceAll("");
							if(Pattern.compile("\"[A-Za-z0-9  ]{0,}[^+]\"").matcher(linha).matches()){//Caso tenha uma string pura
								fazerConta = false;
							}
							if(elements.length>=1){
								for(int i =0;i<elements.length;i++){
									Variavel variavelTemporaria = encontrarVariavel(elements[i]);
									if(variavelTemporaria != null){
										linhaTemp = linhaTemp.replace(elements[i], variavelTemporaria.getValorEmString());
										if(variavelTemporaria.getTipo().equals("string")){
											fazerConta = false;
										}
									}
								}
							}
							else
							{
								Variavel variavelTemporaria = encontrarVariavel(linhaTemp);
								if(variavelTemporaria != null){
									linhaTemp = variavelTemporaria.getValorEmString();
									if(variavelTemporaria.getTipo().equals("string")){
										fazerConta = false;
									}
								}
							}
							if(fazerConta){
								System.out.println(String.valueOf(Conta.fazerConta(linhaTemp)));
							}
							else{
								elements = linhaTemp.split("\\s{1,}[+]\\s{1,}");
								if(elements.length>=1){
									resultEmString = new StringBuilder();
									for(int i =0;i<elements.length;i++){
										resultEmString.append(elements[i]);
									}
									System.out.println(resultEmString.toString());
								}
								else{
									System.out.println(linhaTemp);
								}
							}
							
						}
					}
					else{
						System.out.println("Variable not declared");
					}
				}
			}
			else
			{
				System.out.println("Linha inválida: " + linha);
			}
		}
		catch(Exception exception){
			
		}
	}
}

