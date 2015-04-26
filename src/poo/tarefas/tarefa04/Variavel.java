package poo.tarefas.tarefa04;

public class Variavel {
	private String variavel,tipo;
	String	valor;
	public Variavel(String variavel,String  tipo,Double  valor) {
		this.variavel = variavel;
		this.tipo = tipo;
		try{
		this.valor =  Double.toString(valor);
		}
		catch(Exception exception)
		{
			this.valor = "0.0";
		}
		// TODO Auto-generated constructor stub
	}
	public Variavel(String variavel,String  tipo,String  valor) {
		this.variavel = variavel;
		this.tipo = tipo;
		this.valor = valor;
		// TODO Auto-generated constructor stub
	}
	public Variavel(String variavel,String  tipo,int  valor) {
		this.variavel = variavel;
		this.tipo = tipo;
		try{
		this.valor = Integer.toString(valor);
		}
		catch(Exception exception)
		{
			this.valor = "0";
		}
		// TODO Auto-generated constructor stub
	}
	
	public String getVariavel(){
		return variavel;
	}
	public String getTipo(){
		return tipo;
	}
	public Double getValorDouble(){
		try
		{
			return Double.valueOf(valor);
		}
		catch(Exception exception)
		{
			return null;
		}
	}
	public String getValorEmString(){
		return valor;
	}
	public void setValor(String valor){
		this.valor = valor;
	}
	public void setValor(Double valor){
		this.valor = String.valueOf(valor);
	}
	

}
