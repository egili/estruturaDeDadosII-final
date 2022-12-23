import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArvoreHuffmann implements Cloneable {
	private No raiz = null;

	private String preOrdem (No r) {
		
		if (r == null) 
			return "";

		return r.getInfo() + " " +  this.preOrdem(r.getEsq()) + " " + this.preOrdem(r.getDir());
	}

	private String inOrdem (No r) {
		
		if (r == null) 
			return "";

		return this.inOrdem(r.getEsq()) + " " + r.getInfo() + " " + this.inOrdem(r.getDir());
	}

	private String posOrdem (No r) {
		
		if (r == null) 
			return "";

		return this.posOrdem(r.getEsq()) + " " + this.posOrdem(r.getDir()) + " " + r.getInfo();
	}
	
	public ArvoreHuffmann (ArvoreHuffmann modelo) throws Exception {
		if (modelo == null) throw new Exception ("modelo ausente");

		this.raiz = construtorDeCopia(modelo.raiz);
	}

	// Criacao da arvore pela fila PRECISA MEXER NISSO, MUDAR LOGICA
	public ArvoreHuffmann (FilaPrioridade fila) { 
		try {
			for(int i = fila.size(); i >= 2; i--){
				No no = new No(null);
				no.setEsq(fila.remover());
				no.setDir(fila.remover());
				no.setQtd(no.getDir().getQtd() + no.getEsq().getQtd());
				fila.addEnfileirado(no);
			}
		
			this.raiz = fila.remover(); 
		
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void toHashMap(No n, Map<Byte, String> map, String codigo) {
		if(n.getDir() == null && n.getEsq() == null)  //Chegou em uma folha
			map.put((Byte)n.getInfo(), codigo);
		
		if(n.getDir() != null)
			toHashMap(n.getDir(), map, codigo + "1"); //Toda vez que vai para direira 1

		if(n.getEsq() != null)
			toHashMap(n.getEsq(), map, codigo + "0"); //Vai para esquerda 0
	}

	//retorna o map ja que o metodo e privado
	public Map<Byte, String> toHashMap() {
		Map<Byte, String> map = new HashMap<>();
		toHashMap(this.raiz, map, "");

		return map;
	}

	public ArrayList<Comparable> EncontarByte(String binario) {
		No no = this.raiz;
		ArrayList<Comparable> ret = new ArrayList<>();

		for (int i = 0; i < binario.length(); i++) { //procura a folha
			if (binario.charAt(i) == '0') 
				no = no.getEsq(); //Vai para esquerda pois e 0
			else 
				no = no.getDir(); //Vai para direita pois e 1

			if (no.getInfo() != null) { //Acha informacao e coloca na array 
				ret.add(no.getInfo());
				no = this.raiz;
			}
		}
		return ret;
	}

	@Override
	public String toString () {
		String pre = this.preOrdem(this.raiz), in = this.inOrdem (this.raiz), pos = this.posOrdem(this.raiz);

		return "Pre-ordem: " + (pre.equals("") ? "Arvore vazia":pre) + "\n" + "In-ordem : " + (in .equals("") ? "Arvore vazia":in ) + "\n" + "Pos-ordem: " + (pos.equals("") ? "Arvore vazia":pos);
	}

	private boolean equals (No raizA, No raizB) {
		return (raizA == null && raizB != null) 
				|| (raizA != null && raizB == null) 
				|| (raizA == null && raizB == null) 
				|| (!raizA.getInfo().equals(raizB.getInfo())) 
				? false 
				: equals (raizA.getEsq(),raizB.getEsq()) && equals (raizA.getDir(),raizB.getDir());
	}

	@Override
    public boolean equals (Object obj) {
		return obj == this ? true : obj == null || this.getClass() != obj.getClass() ? false : equals(this.raiz, ((ArvoreHuffmann) obj).raiz);
	}

    private int hashCode (No raiz) {
		int ret = 13;

		if (raiz != null) {
			ret = 3 * ret + raiz.getInfo().hashCode();
			ret = 5 * ret + hashCode (raiz.getEsq());
			ret = 11 * ret + hashCode (raiz.getDir());
		}
		return ret < 0 ? -ret : ret;
	}

	@Override
	public int hashCode () {
		return hashCode (this.raiz);
	}

	private No construtorDeCopia (No raiz)
	{
		if (raiz == null) return null;

		return new No (
			construtorDeCopia(raiz.getEsq()),
			construtorDeCopia(raiz.getDir()),
			raiz.getInfo(),
			raiz.getQtd()
		);
	}

	public Object clone () {
		ArvoreHuffmann ret = null;
		try {
			ret = new ArvoreHuffmann (this);
		}
		catch (Exception erro) {}
		
		return ret;
	}
}