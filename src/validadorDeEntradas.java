public class validadorDeEntradas {

    public void notNull(Object[] objects){
        for(Object i : objects){
            if (i == null)throw new NullPointerException();
        }
    }
    public void stringVazia(String[] strings){
        for(String i : strings){
            if(toString().trim().equals(""))throw new IllegalArgumentException("String vazia");
        }
    }
    public void validadorValores(int[] valores){
        for(int i : valores){
            if(i <= 0)throw new IllegalArgumentException("valor negativo");
        }
    }
    public void validadorPreco(Double[] precos){
        for(Double i : precos){
            if(i < 0)throw new IllegalArgumentException("preco negativo");
        }
    }
}
