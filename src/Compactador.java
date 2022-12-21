import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Compactador {
	
	public class Compressor 
	{
	
	    byte[] arquivoDeBytes;
	    FilaPrioridade filaP = new FilaPrioridade();
	    Arvore arvore;
	
	    public void compress(String arquivo) throws Exception {
	    	readFiles(arquivo);
	        newListaPrioridade();
	        arvore = new Arvore((FilaPrioridade)filaP.clone());
	        newCompressed(arquivo);
	    }
	
	    private void readFiles(String arquivo) throws  Exception{
	       File newFIle = new File(arquivo);
	       RandomAccessFile accessArquivo = new RandomAccessFile(newFIle, "rw");
	       this.arquivoDeBytes = new byte[(int)accessArquivo.length()];
	       accessArquivo.read(this.arquivoDeBytes);
	       accessArquivo.close();
	   }
	
	       private String addBytesArvore(Arvore a){
	        StringBuilder mapDeBytes = new StringBuilder();
	        Map<Byte, String>  map = a.toHashMap();
	        for (byte arquivoDeBytes : this.arquivoDeBytes) {
	            mapDeBytes.append(map.get(arquivoDeBytes));
	        }
	        return mapDeBytes.toString();
	    }
	
	private void newListaPrioridade() {
	    Map<Byte, Integer> qtdBytes = new HashMap<>();
	
	    for (byte arquivoDeBytes : this.arquivoDeBytes) {
	        if(!qtdBytes.containsKey(arquivoDeBytes)) { 
	        	qtdBytes.put(arquivoDeBytes, 1);
	        } else {
	        	qtdBytes.put(arquivoDeBytes,qtdBytes.get(arquivoDeBytes) + 1); 
	        }
	    }
	
	    qtdBytes.forEach((key, value) -> filaP.adicionar(new No(key, value)));
	}
	
	    private void newCompressed(String path){ 
	        ArrayList teste = new ArrayList();
	        StringBuilder dados = new StringBuilder(addBytesArvore(arvore));
	        try {
	            File arquivo = new File(path + ".compress");
	            RandomAccessFile arquivoDeEscita = new RandomAccessFile(arquivo.getAbsolutePath(),"rw");
	
	            ArrayList<String> filaString = filaP.Fila();
	
	            for (int i = 0; i < filaString.size(); i++) {
	                if(i%2==0){ 
	                    arquivoDeEscita.writeByte((byte)Integer.parseInt(filaString.get(i))); 
	                }
	                else {
	                    arquivoDeEscita.writeInt(Integer.parseInt(filaString.get(i)));
	                }
	            }
	
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	
	            StringBuilder auxiliar = new StringBuilder();
	            int contador = 0;
	
	            if(!(dados.length()%7==0)){
	                while (!(dados.length()%7==0)){
	                    dados.append("0");
	                    contador++;
	                }
	            }
	
	            for (int i = 0; i < dados.length(); i++) {
	                auxiliar.append(dados.charAt(i));
	                if((i+1)%7==0){
	                    byte b = converterStringByte(auxiliar.toString());
	                    arquivoDeEscita.write(b);
	                    auxiliar = new StringBuilder();
	                }
	            }
	
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	            arquivoDeEscita.write(Byte.MIN_VALUE);
	            
	            arquivoDeEscita.write(contador);
	
	            arquivoDeEscita.close();
	        } catch (IOException e) {
	            System.out.println("Erro - gerar arquivo compactado");
	            e.printStackTrace();
	        }
	    }
	    private byte converterStringByte(String x){
	
	        byte ret = (byte)0;
	
	        for (byte ps =(byte)6,pb=(byte)0;pb<7;ps--,pb++) {
	            if(x.charAt(ps)=='1'){
	                ret = setBit(pb,ret);
	            }
	        }
	        return ret;
	    }
	
	    private byte setBit(byte qualBit,byte valor){
	        byte mascara = (byte) 1;
	
	        mascara<<=qualBit;
	
	        return (byte) (valor | mascara);
	    }
	
	    byte resetBit (byte qualBit,byte valor){
	        byte mascara = (byte) 1;
	
	        mascara >>= qualBit;
	        mascara = (byte) ~mascara;
	
	        return (byte) (valor & mascara);
	    }
	}

	public class Descompressor {
		
	    FilaPrioridade filaP = new FilaPrioridade();
	    Arvore arvore;
	    String binario, extencao, arquivo;
	
	    public void unzip(String arquivoCompactado) throws Exception {
	        String arquivo = arquivoCompactado.replace("",""); 
	        String[] ItensDoArquivo = arquivo.split("\\."); 
	
	        arquivo = ItensDoArquivo[0];
	        extencao= "." + ItensDoArquivo[1];
	
	        readFiles(arquivoCompactado);
	        arvore = new Arvore((FilaPrioridade)filaP.clone());
	        ArrayList<String> filaString = filaP.Fila();
	
	        newUnzipper();
	    }
	
	    private void readFiles(String arquivo) throws IOException {
	
	        RandomAccessFile readFile = new RandomAccessFile(arquivo, "rw");
	        byte[] dados = new byte[(int)readFile.length()];
	        readFile.read(dados); 
	        Map<Integer, Integer> frequenciaDosBytes = new HashMap<>();
	
	        int indexAtual = 0;
	        
	        for (int i = 0; i < dados.length; i+=5) {
	            if(dados[i]==-128 && dados[i+1]==-128 && dados[i+2]==-128 ){
	                indexAtual = i+3;
	                break;
	            }
	            
	            if(i%5==0){
	                byte[] b = new byte[4]; 
	                b[0] = (dados[i+1]);
	                b[1] = (dados[i+2]);
	                b[2] = (dados[i+3]);
	                b[3] = (dados[i+4]);
	
	                filaP.enfileirar(new No((int)dados[i], converterArrByteArrInt(b)));
	            }
	        }
	
	        StringBuilder binariosDoArquivo = new StringBuilder();
	        for (int i = indexAtual; i < dados.length; i++) {
	            if(dados[i]==-128 && dados[i+1]==-128 && dados[i+2]==-128 ){
	                indexAtual = i+3;
	                break;
	            }
	
	            if(Integer.toBinaryString(dados[i]).length()!=7){
	                StringBuilder zerosQueFaltam = new StringBuilder();
	
	                int diferenca = Integer.toBinaryString(dados[i]).length() - 7;
	                
	                if (diferenca < 0) diferenca = diferenca * -1;
	                for (int j = 0; j < diferenca; j++) {
	                    zerosQueFaltam.append("0");
	                }
	
	                zerosQueFaltam.append(Integer.toBinaryString(dados[i]));
	                binariosDoArquivo.append(zerosQueFaltam);
	                zerosQueFaltam = new StringBuilder();
	            }
	            else{
	                binariosDoArquivo.append(Integer.toBinaryString(dados[i]));
	            }
	        }
	
	        int salvarNumeroDzero = dados[indexAtual];
	        StringBuilder binariosOriginais = new StringBuilder();
	
	        if(salvarNumeroDzero>0){
	            for (int i = 0; i < binariosDoArquivo.length()-salvarNumeroDzero; i++) {
	                binariosOriginais.append(binariosDoArquivo.charAt(i));
	            }
	        }else binariosOriginais = binariosDoArquivo;
	
	
	        binario = binariosOriginais.toString();
	
	    }
	
	    private void newUnzipper() throws Exception {
	        
	    	byte[] bytes = extrairBytesDaArvore();
	        
	        try {
	            FileOutputStream outputStream = new FileOutputStream(arquivo + "(unzipped)" + extencao);
	            outputStream.write(bytes);
	        } catch (IOException e) {
	            System.out.println("Erro - criar caminho de extração");
	            e.printStackTrace();
	        }
	    }
	
	    private byte[] extrairBytesDaArvore() {
	        String binarioDescompactado = this.binario;
	        ArrayList<Comparable> bytesAlt = new ArrayList<>();
	        ArrayList<Comparable> resultado = arvore.EncontarByte(binarioDescompactado);
	     
	        for (Comparable b: resultado) {
	            bytesAlt.add(b);
	        }
	
	        byte[] bytes = new byte[bytesAlt.size()];
	        
	        for (int i = 0; i < bytesAlt.size(); i++) {
	            bytes[i] = (byte) Integer.parseInt(bytesAlt.get(i).toString());
	        }
	        return bytes;
	    }
	  
	    public int converterArrByteArrInt(byte[] array) {
	        int valor = 0;
	        for (byte b : array) {
	            valor = (valor << 8) + (b & 0xFF);
	        }
	
	        return valor;
	    }
	
	}
}

