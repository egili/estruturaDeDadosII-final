import java.util.ArrayList;
import java.util.Arrays;

public class FilaPrioridade<T> {
	
	private ArrayList<T> fila = new ArrayList<T> ();
	private int primeiro = 0;
	private int ultimo = 0;
	private int total = 0;

	public FilaPrioridade() throws Exception {
		ArrayList<T> fila = new ArrayList<T>(10);
		//this(10);
	}
	
	public FilaPrioridade(int capacidadeInicial) throws Exception {
		if(capacidadeInicial <= 0)
			throw new Exception ("O tamananho da fila precisa ser um numero positivo");
		
		ArrayList<T> fila = new ArrayList<T>(capacidadeInicial);
	}
	
	public boolean isEmpty() {
		return total == 0;
	}
	
	public void adicionar(T elementoAAdcionar) throws Exception { 
		Comparable<T> chave = (Comparable<T>) elementoAAdcionar;
		int i;
		
		for (i = 0; i < fila.size(); i++) {
			if(chave.compareTo(fila.get(i)) < 0)
				break;	
		}
		fila.add(i, elementoAAdcionar);
	}
		
	public T remover() throws Exception {
		if(isEmpty()) {
			throw new Exception ("Nao e possivel retirar nenhum elemento, a fila esta vazia");
		}
		
		primeiro = primeiro + 1;
		total --;
		return fila.remove(0);
	}	
	
	public T verProximoElementoASerRemovido() {
		T ProxRemov = fila.get(primeiro + 1);
		return ProxRemov;		
	}
	
    public int tamanho(){ 
        return fila.size();
    }
    
//	public void redimensionsar (FilaPrioridade<T> filaARedimensionar) {	
//	}
	
	@Override
	public String toString() {
		String texto = "";
		
		for (int i = 0; i < fila.size() - 1; i++) {
			texto = fila.get(i) + ",";
		}
		
		return "[" + texto + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
		if (obj == null || obj.getClass() != FilaPrioridade.class) 
			return false;
		
		FilaPrioridade<T> filaPrioridade = (FilaPrioridade<T>) obj;
		
		if(this.primeiro != filaPrioridade.primeiro) 
			return false;
		if(this.ultimo != filaPrioridade.ultimo) 
			return false;
		if(this.total != filaPrioridade.total) 
			return false;
		if(this.fila != filaPrioridade.fila)
			return false;
		
		return true;
	}
	
	
	@Override
	public int hashCode() {
		int ret = 666;
	    ret = 13 * ret + Integer.valueOf(primeiro).hashCode();
	    ret = 13 * ret + Integer.valueOf(ultimo).hashCode();
	    ret = 13 * ret + Integer.valueOf(total).hashCode();
	   // ret = 13 * ret + T.valueOf(fila).hashCode(); Não sei se isso existe

	    return ret < 0 ? -ret : ret;
		
	}
}