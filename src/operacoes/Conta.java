package operacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conta{
		private final static String acceptedOperations = "-+*/";	
		private static Pattern pattern = Pattern.compile("\\s*([0-9]{1,}([.][0-9]{1,}){0,1})\\s*["+acceptedOperations+"]\\s*([0-9]{1,}([.][0-9]{1,}){0,1})\\s*");
		private static Matcher matcher;
		
		
		
		
		public static double fazerConta(String conta){
			matcher = pattern.matcher(conta);
			matcher.find();
			double resultado = 0;
			if(conta==null){
            	return -9999999;
            }
            if(pattern.matcher(conta).matches()){
            	resultado = fazerOperacao(matcher);
            	if(conta.split("["+acceptedOperations+"]").length % 2 != 0)
            	{//Pega o resultado, o último operando e o último valor e faz a conta
            		Matcher lastOperation = Pattern.compile("\\s*["+acceptedOperations+"]\\s*([0-9]{1,}([.][0-9]{1,}){0,1})\\s*$").matcher(conta);
            		resultado = fazerOperacao(resultado + " " + lastOperation.group(lastOperation.groupCount()-1) + " " + conta.split("["+acceptedOperations+"]")[conta.split("["+acceptedOperations+"]").length-1]);
            	}
	           return resultado;
            }
            else if(Pattern.compile("([0-9]{1,}([.][0-9]{1,}){0,1})").matcher(conta).matches()){
            	return Double.valueOf(conta);
            }
            else
            {
            	return -9999999;
            }
		}
		
		private static double fazerOperacao(Matcher RegexMatcher)
		{
			Double resultado = 0.0;
			while(RegexMatcher.find()){
	            if(RegexMatcher.group().contains("*")){
	            	resultado += fazerMultiplicacao(RegexMatcher.group());
	            }
	            else if(RegexMatcher.group().contains("/")){
	            	resultado += fazerDivisao(RegexMatcher.group());
	            }
	            else if(RegexMatcher.group().contains("+")){
	            	resultado += fazerSoma(RegexMatcher.group());
	            }
	            else if(RegexMatcher.group().contains("-")){
	            	resultado += fazerSubtracao(RegexMatcher.group());
	            }
	            else{
	            	resultado =  Double.valueOf(RegexMatcher.group());
	            }
			}
			return resultado;
		}
		
		private static double fazerOperacao(String conta)
		{
			Double resultado = 0.0;
	            if(conta.contains("*")){
	            	resultado += fazerMultiplicacao(conta);
	            }
	            else if(conta.contains("/")){
	            	resultado += fazerDivisao(conta);
	            }
	            else if(conta.contains("+")){
	            	resultado += fazerSoma(conta);
	            }
	            else if(conta.contains("-")){
	            	resultado += fazerSubtracao(conta);
	            }
	            else{
	            	resultado =  Double.valueOf(conta);
	            }
			return resultado;
		}
		
		private static double fazerSoma(String Conta) {
			matcher = pattern.matcher(Conta);
			matcher.find();
			try{
			return Double.valueOf(matcher.group(1)) + Double.valueOf(matcher.group(2));
			}
			catch(Exception e){
				return -1;
			}
		}
		
		private static double fazerSubtracao(String Conta) {
			matcher = pattern.matcher(Conta);
			matcher.find();
			try{
			return Double.valueOf(matcher.group(1)) - Double.valueOf(matcher.group(2));
			}
			catch(Exception e){
				return -1;
			}
		}
		
		
		
		private static double fazerMultiplicacao(String Conta) {
			matcher = pattern.matcher(Conta);
			matcher.find();
			try{
			return Double.valueOf(matcher.group(1)) * Double.valueOf(matcher.group(2));
			}
			catch(Exception e){
				return -1;
			}
		}
		
		private static double fazerDivisao(String Conta) {
			matcher = pattern.matcher(Conta);
			matcher.find();
			try{
			return Double.valueOf(matcher.group(1)) / Double.valueOf(matcher.group(2));
			}
			catch(Exception e){
				return -1;
			}
		}
	

	
	
	
	
	
}
