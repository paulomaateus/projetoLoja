import java.util.HashMap;
import java.util.Map;

/**
 * Controller dos produtos da loja!
 * Os produtos podem ser unidades ou pacotes. Produtos que sao pacotes tambem tem unidades dentro dele.
 * Os produtos contidos dentro dos pacotes tambem ser�o salvos no map de produtos para contabiliza�ao total
 * de todas as unidades.
 * Quando a quantidade de unidades formar um respectivo pacote daquele produto, atualizamos a quantidade de 
 * pacotes do respectivo produto.
 * No sistema e possivel adicionar produtos que sejam apenas unidade, mas quando se adiciona um produto que � 
 * um pacote, a unidade daquele produto tambem e adicionada ao sistema. 
 * E possivel cadastrar produtos unidades e pacotes como tambem adicionar produtos unidades e pacotes.
 * 
 * 
 * @author Paulo Moreira
 *
 */
public class CRUDProduto {
	/**
	 * Nesse mapa esta salvo qualquer produto que exista na loja, de uma bala a 500 unidades de cacha�a.
	 */
	private Map<String, Produto> produtos;
	
	/**
	 * Nesse mapa esta salvo apenas produtos que sejam pacotes. Esses pacotes tem que ter a quantidade de 
	 * produtos que contem.
	 */
	private Map<String, produtoPacote> pacotes;
	private validadorDeEntradas validador = new validadorDeEntradas();
	
	/**
	 * No construtor do CRUD e feito apenas a inicializacao das estruturas de dados.
	 */
	public CRUDProduto() {
		this.produtos = new HashMap<>();
		this.pacotes = new HashMap<>();
	}	
	
	/*pode haver um erro de funcionalidade aqui. Quando a unidade ja estiver cadastrada, o preco de compra da unidade pode ser diferente ao
	 * do pacote. Verificar depois qual melhor solucao. (RESOLVIDO)
	 */
	/**
	 * Nesse metodo serao cadastrados pacotes de produtos ao sistema. Se informa o nome, preco que foi comprado
	 * o produto e quantas unidades aquele produto tem.
	 * Quando o objeto pacote e criado ele e salvo dentro do map da pacotes. A chave e o nome daquele produto. 
	 * Quando um pacote e adicionado, a sua unidade tambem e adicionada ao map de produtos, e a chave no map tambem e
	 * o nome daquele produto.
	 * 
	 * @param nome Nome do produto que esta se adicionado.
	 * @param precoCompra Preco que o produto foi comprado.
	 * @param unidadesNoPacote Quantidade de unidades que o pacote devera ter.
	 */
	public void cadastraPacote(String nome, double precoCompra, int unidadesNoPacote ) {
		this.validador.notNull(new Object[]{nome, precoCompra, unidadesNoPacote});
		this.validador.stringVazia(new String[]{nome});
		this.validador.validadorPreco(new Double[]{precoCompra});
		this.validador.validadorValores(new int[]{unidadesNoPacote});
		if(this.pacotes.containsKey(nome)) {
			throw new IllegalArgumentException("Erro! o produto que se tenta cadastrar ja esta cadastrado no sistema!");
		}if(!this.produtos.containsKey(nome)) {
			this.cadastraUnidade(nome, precoCompra/unidadesNoPacote);
		}else{
			this.produtos.get(nome).setPrecoCompra(precoCompra/unidadesNoPacote);
		}
		this.pacotes.put(nome, new produtoPacote(nome, precoCompra, unidadesNoPacote));
	}
	
	/**
	 * Metodo que funciona para o cadastro de unidades e funciona da mesma forma que o metodo que cadastra pacotes.
	 * Porem, esse metodo sera usado apenas quando o usuario quiser cadastrar produtos que ele nao comprara pacotes mas
	 * apenas unidades.
	 * 
	 * @param nome Nome do produto a ser adicionado.
	 * @param precoCompra Preco que o produto foi comprado.
	 */
	public void cadastraUnidade(String nome, double precoCompra) {
		this.validador.notNull(new Object[]{nome, precoCompra});
		this.validador.stringVazia(new String[]{nome});
		this.validador.validadorPreco(new Double[]{precoCompra});
		if(this.produtos.containsKey(nome)) {
			throw new IllegalArgumentException("Erro! o produto que se tenta cadastrar ja esta cadastrado no sistema!");
		}
		this.produtos.put(nome, new Produto(nome, precoCompra));
		
	}
	
	/**
	 * Depois de pre-cadastrado, pode-se adicionar pacotes do respectivo produto. Apenas precisa ser informado o nome e 
	 * a quantidade de pacotes que se quer adicionar.
	 * Quando o pacote e adicionado, as unidades que contem naquele pacote tambem serao contabilizadas no sistema.
	 * @param nome Nome do produto que se quer adicionar.
	 * @param qtdPacotes Quantidade de pacotes do respectivo produto.
	 */
	public void adicionaPacotes(String nome, int qtdPacotes) {
		this.validador.notNull(new Object[]{nome, qtdPacotes});
		this.validador.stringVazia(new String[]{nome});
		this.validador.validadorValores(new int[]{qtdPacotes});
		if(!this.pacotes.containsKey(nome)) {
			throw new IllegalArgumentException("Erro, produto nao esta cadastrado no sistema");
		}
		this.pacotes.get(nome).atualizaQuantidade(qtdPacotes);
		this.produtos.get(nome).atualizaQuantidade((this.pacotes.get(nome).getQuantidadeUnidades()) * qtdPacotes);
	}
	
	/**
	 * Adiciona unidades de produtos no sistema. Ao adicionar unidades que formem um pacote, um pacote e adicionado ao mapa
	 * de pacotes mas as unidades permanecem salvas. ao vender unidades que desmanchem um pacote, os pacotes desmanchados sao excluidos 
	 * do mapa de pacotes.
	 * @param nome
	 * @param qtdUnidades
	 */
	public void adicionaUnidades(String nome, int qtdUnidades) {
		this.validador.notNull(new Object[]{nome, qtdUnidades});
		this.validador.stringVazia(new String[]{nome});
		this.validador.validadorValores(new int[]{qtdUnidades});
		if(!this.produtos.containsKey(nome)){
			throw new IllegalArgumentException("Erro, produto nao esta cadastrado no sistema!");
		}
		this.produtos.get(nome).atualizaQuantidade(qtdUnidades);
		if(this.pacotes.containsKey(nome))contabilizaProdutos(nome);
	}
	/**
	 * Metodo privado para auxiliar na atualizacao das estruturas de dados e atualizacao da quantidade de cada protudo.
	 * Esse metodo atualiza quantidade de pacotes ou unidades quando cada um � adicionado, vendido ou zerado do sistema.
	 * @param nome Nome do produto a ser contabilizado.
	 */
	private void contabilizaProdutos(String nome) {
		int quantidadePacotesPorUnidade = this.produtos.get(nome).getQuantidade() / this.pacotes.get(nome).getQuantidadeUnidades();
		int quantidadePacotes = this.pacotes.get(nome).getQuantidade();
		this.pacotes.get(nome).atualizaQuantidade(quantidadePacotesPorUnidade - quantidadePacotes); 
	}

	/**
	 * Metodo que seta o preco de venda de um pacote. O preco nao pode ser menor ou igual ao preco que o produto foi comprado
	 * @param nome nome do produto
	 * @param preco preco de venda do produto
	 */
	public void setPrecoVendaPacote(String nome, double preco) {
		this.validador.notNull(new Object[]{nome, preco});
		this.validador.stringVazia(new String[]{nome});
		this.validador.validadorPreco(new Double[]{preco});
		if(!this.pacotes.containsKey(nome)) {
			throw new IllegalArgumentException("Produto nao existe.");
		}
		if(this.pacotes.get(nome).getPrecoCompra() >= preco) {
			throw new IllegalArgumentException("O preco para se vender o produto nao pode ser menor ou igual que o preco que ele foi comprado.");
		}
		this.pacotes.get(nome).setPrecoVenda(preco);
	}

	/**
	 * Metodo que seta o preco de venda de uma unidade. O preco nao pode ser menor ou igual ao preco que a unidade foi comprada.
	 * @param nome nome do produto
	 * @param preco preco de venda do produto.
	 */
	public void setPrecoVendaUnidade(String nome, double preco){
		this.validador.notNull(new Object[]{nome, preco});
		this.validador.stringVazia(new String[]{nome});
		this.validador.validadorPreco(new Double[]{preco});
		if(!this.produtos.containsKey(nome))throw new IllegalArgumentException("Produto inexistente.");
		if(this.produtos.get(nome).getPrecoCompra() >= preco)throw new IllegalArgumentException("O preco para se vender o produto nao pode ser menor ou igaul ao preco ue foi comprado.");
		this.produtos.get(nome).setPrecoVenda(preco);
	}

	// metodos auxiliares para testes.
	public String toStringPacotes(){
		String retorno = "";
		for(produtoPacote produto : this.pacotes.values()){
			retorno += produto.toString() + "\n";
		}
		return retorno;
	}
	public String toStringProdutos(){
		String retorno = "";
		for(Produto produto : this.produtos.values()){
			retorno += produto.toString() + "\n";
		}
		return retorno;
	}
	public String getPrecoVendaPacote(String nome){
		if(this.pacotes.containsKey(nome)){
			return "R$" +  this.pacotes.get(nome).getPrecoVenda();
		}else{
			return "produto inexistente";
		}
	}
	public String getPrecoVendaProduto(String nome){
		if(this.produtos.containsKey(nome)){
			return "R$" + this.produtos.get(nome).getPrecoVenda();
		}else{
			return "produto inexistente.";
		}
	}

	public int getUnidades (String nome){
		return this.produtos.get(nome).getQuantidade();
	}
	public int getPacotes (String nome){
		return this.pacotes.get(nome).getQuantidade();
	}
	//fim dos metodos auxiliares.

	/**
	 * Metodo para vender produto que e um pacote. Esse metodo deve subtrair nos dois mapas a quantidade de pacotes vendidos
	 * Esse metodo retorna um objeto produtoPacote.
	 * @param nome: pacote a ser vendido
	 * @param preco: preco que o pacote esta sendo vendido
	 * @param qtd: quantidade de pacotes vendidos 
	 * @return retorna um produtoPacote para ser usado em metodos de registros de vendas em outra classe
	 */
	public produtoPacote venderPacote(String nome, double preco, int qtd) {
		this.validador.notNull(new Object[]{nome, preco, qtd});
		this.validador.stringVazia(new String[]{nome});
		this.validador.validadorPreco(new Double[]{preco});
		this.validador.validadorValores(new int[]{qtd});
		if(!this.pacotes.containsKey(nome)) {
			throw new IllegalArgumentException("Produto nao existe.");
		}
		if(this.pacotes.get(nome).getPrecoCompra() >= preco) {
			throw new IllegalArgumentException("Voce nao pode vender o produto pelo mesmo preco que comprou ou mais barato.");
	
		}
		Produto produtoVendido =  this.pacotes.get(nome).clone();
		produtoVendido.setQuantidade(qtd);
		produtoVendido.setPrecoVenda(preco);
		this.pacotes.get(nome).atualizaQuantidade(-qtd);
		this.produtos.get(nome).atualizaQuantidade(-qtd * this.pacotes.get(nome).getQuantidadeUnidades());
		return (produtoPacote) produtoVendido;

	}
	/**
	 * Metodo para realizar a venda de um produto que e uma unidade nos sistema. Ao ser vendido as unidades devem ser subtraidas
	 * dos dois mapas
	 * @param nome: Nome do produto a ser vendido
	 * @param preco: preco do produto a ser vendido
	 * @param qtd: quantidade do produto a ser vendido
	 * @return retorna um produto unidade para que seja usado em metodos de registros de vendas
	 */
	public Produto venderUnidade(String nome, double preco, int qtd){
		this.validador.notNull(new Object[]{nome, preco, qtd});
		this.validador.stringVazia(new String[]{nome});
		this.validador.validadorPreco(new Double[]{preco});
		this.validador.validadorValores(new int[]{qtd});
		if(!this.produtos.containsKey(nome))throw new IllegalArgumentException("Produto nao existe");
		if(this.produtos.get(nome).getPrecoCompra() >= preco) throw new IllegalArgumentException("Voce nao pode vender o produto pelo mesmo preco que comprou ou mais barato.");
		Produto produtoVendido = this.produtos.get(nome).clone();
		produtoVendido.setQuantidade(qtd);
		produtoVendido.setPrecoVenda(preco);
		this.produtos.get(nome).atualizaQuantidade(-qtd);
		this.contabilizaProdutos(nome);
		return produtoVendido;
	}
}

