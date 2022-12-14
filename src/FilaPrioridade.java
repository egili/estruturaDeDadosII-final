public class FilaPrioridade<T> {
	
	int quantosElementosEstaoNaFila;

	
	public FilaPrioridade() throws Exception {
		this(10);
	}
	
	public FilaPrioridade(int capacidadeInicial) throws Exception {
		if(capacidadeInicial <= 0)
			throw new Exception ("O tamananho da fila precisa ser um numero positivo");
		
		quantosElementosEstaoNaFila = 0;
		
	}
	
	public void adicionar(T elementoAAdcionar) { // um segundo parametro para definir a prioridade do elemento adicionado
		
	}
	
	public T remover() {
		
	}
	
	public T verProximoElementoASerRemovido() { // nao remove, tem necessidade de ter esse metodo ??
		
	}
	
	public void redimensionsar (FilaPrioridade<T> filaARedimensionar) {
		// quando estiver completamente preenchido aumenta a capacidade da fila
	}
	
	
	@Override
	public String toString() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		
	}
	
	@Override
	public int hashCode() {
		
	}
}