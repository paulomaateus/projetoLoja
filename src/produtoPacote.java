
public class produtoPacote extends Produto{
	private int unidades;
	
	public produtoPacote(String nome, double precoCompra, int unidades) {
		super(nome,  precoCompra);
		this.unidades = unidades;
	}

	public int getQuantidadeUnidades() {
		return this.unidades;
	}
	@Override
	public String toString(){
		return this.nome + " R$ " + this.precoVenda + " Pacotes = " + unidades;
	}
	public double getPrecoTotal(){
		return this.quantidade * this.precoVenda;
	}
}
