
public class Produto implements Cloneable {
	protected String nome;
	protected double precoCompra;
	protected int quantidade;
	protected double precoVenda;

	public Produto(String nome, double precoCompra) {
		this.nome = nome;
		this.precoCompra = precoCompra;
		this.quantidade = 0;
		this.precoVenda = 1.3 * precoCompra;
	}

	public Produto(String nome, double precoCompra, double precoVenda) {
		this.nome = nome;
		this.precoCompra = precoCompra;
		this.quantidade = 0;
		this.precoVenda = precoVenda;
	}

	public String getNome() {
		return this.nome;
	}

	public double getPrecoCompra() {
		return this.precoCompra;
	}

	public void atualizaQuantidade(int quantidade) {
		this.quantidade += quantidade;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public double getPrecoVenda() {
		return this.precoVenda;
	}

	public void setPrecoCompra(double precoCompra) {
		this.precoCompra = precoCompra;
	}

	@Override
	public String toString() {
		return this.nome + " R$ " + this.precoVenda + " unidades = " + this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public Produto clone() {
		Produto p;
		try {
			p = (Produto) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
		return p;
	}


}
