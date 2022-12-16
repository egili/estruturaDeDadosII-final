import java.util.ArrayList;
import java.util.Arrays;

public class FilaPrioridade<T> {
	
	private ArrayList<T> fila = new ArrayList<T> ();
	private int primeiro = 0;
	private int ultimo = 0;
	private int total = 0;

	public FilaPrioridade() throws Exception {
		ArrayList fila = new ArrayList(10);
		//this(10);
	}
	
	public FilaPrioridade(int capacidadeInicial) throws Exception {
		if(capacidadeInicial <= 0)
			throw new Exception ("O tamananho da fila precisa ser um numero positivo");
		
		ArrayList fila = new ArrayList(capacidadeInicial);
	}
	
	public boolean isEmpty() {
		return total == 0;
	}
	
	public boolean isFull() {
		return total == fila.size();
	}
	
	public void adicionar(T elementoAAdcionar) throws Exception { // um segundo parametro para definir a prioridade do elemento adicionado
		if (isFull()) {
			this.redimensionsar();
		}
		
		fila.get(ultimo) = elementoAAdcionar;
		ultimo = ultimo ++;
		//preciso voltar aqui para redimensionar 
	}
		
	
	public T remover() throws Exception {
		if(isEmpty()) {
			throw new Exception ("Nao e possivel retirar nenhum elemento, a fila esta vazia");
		}
		
		T elemento = fila.get(primeiro);
		primeiro = primeiro + 1;
		total --;
		return elemento;
	}
	
	
	public T verProximoElementoASerRemovido() { // nao remove, tem necessidade de ter esse metodo ??
		T ProxRemov = fila.get(primeiro + 1);
		return ProxRemov;		
	}
	
	public void redimensionsar (FilaPrioridade<T> filaARedimensionar) {
		
	}
	
	@Override
	public String toString() {
		return "[" + fila + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
		if (obj == null || obj.getClass() != FilaPrioridade.class) 
			return false;
		
		FilaPrioridade filaPrioridade = (FilaPrioridade) obj;
		
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