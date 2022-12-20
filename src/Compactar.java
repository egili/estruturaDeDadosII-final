import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Compactar
{

    byte[] arquivoDeBytes;
    FilaPrioridade filaP = new FilaPrioridade();
    Arvore arvore;

    public void compactar(String arquivo) throws Exception {
        lerArquivo(arquivo);
        criarListaDePrioridade();
        arvore = new Arvore((FilaPrioridade)filaP.clone());
        gerarArquivoCompactado(arquivo);
    }

    private void lerArquivo(String arquivo) throws  Exception{
       File newFIle = new File(arquivo);
       RandomAccessFile acessoAoArquivo = new RandomAccessFile(newFIle, "rw");
       this.arquivoDeBytes = new byte[(int)acessoAoArquivo.length()];
       acessoAoArquivo.read(this.arquivoDeBytes);
       acessoAoArquivo.close();
   }

       private String addBytesArvore(Arvore a){
        StringBuilder mapDeBytes = new StringBuilder();
        Map<Byte, String>  map = a.toHashMap();
        for (byte arquivoDeBytes : this.arquivoDeBytes) {
            mapDeBytes.append(map.get(arquivoDeBytes));
        }
        return mapDeBytes.toString();
    }
   private void criarListaDePrioridade() {
    Map<Byte, Integer> frequenciaDeBytes = new HashMap<>();

    for (byte arquivoDeBytes : this.arquivoDeBytes) {
        if(!frequenciaDeBytes.containsKey(arquivoDeBytes)) { 
            frequenciaDeBytes.put(arquivoDeBytes, 1);
        } else {
            frequenciaDeBytes.put(arquivoDeBytes,frequenciaDeBytes.get(arquivoDeBytes) + 1); 
        }
    }

    frequenciaDeBytes.forEach((key, value) -> filaP.adicionar(new No(key, value)));
}

    private void gerarArquivoCompactado(String path){ 
        ArrayList teste = new ArrayList();
        StringBuilder dados = new StringBuilder(addBytesArvore(arvore));
        try {
            File arquivo = new File(path+".compress");
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
                    byte b = deStringParaByte(auxiliar.toString());
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
    private byte deStringParaByte(String x){

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


