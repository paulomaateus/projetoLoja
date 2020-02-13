import java.util.*;


public class Venda {
    double valorVenda;
    String dataVenda; // depois fazer com que data seja do tipo Date
    List produtos;

    public Venda(double valorVenda, ArrayList mercadorias) {
        this.valorVenda = valorVenda;
        this.produtos = mercadorias;
    }

    public void setData(final String data) { // Depois editar para Date
        this.dataVenda = data;
    } 
}