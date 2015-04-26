package operacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conta{
		private static Pattern pattern = Pattern.compile("\\s*([0-9]{1,}([.][0-9]{1,}){0,1})\\s*[-+/*]\\s*([0-9]{1,}([.][0-9]{1,}){0,1})\\s*");
		private static Matcher matcher;
		
		
		
		public static double fazerConta(String conta){
			matcher = pattern.matcher(conta);
			matcher.find();
			double resultado = 0;
			if(conta==null){
            	return -9999999;
            }
            if(pattern.matcher(conta).matches()){
            	while(matcher.find()){
		            if(matcher.group().contains("*")){
		            	resultado += fazerMultiplicacao(matcher.group());
		            }
		            else if(matcher.group().contains("/")){
		            	resultado += fazerDivisao(matcher.group());
		            }
		            else if(matcher.group().contains("+")){
		            	resultado += fazerSoma(matcher.group());
		            }
		            else if(matcher.group().contains("-")){
		            	resultado += fazerSubtracao(matcher.group());
		            }
		            else{
		            	return Double.valueOf(matcher.group());
		            }
            	}
            	if(conta.split("[-+/*]").length % 2 != 0)
            	{
            		resultado += Double.valueOf(conta.split("[-+/*]")[conta.split("[-+/*]").length-1]) ;
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
