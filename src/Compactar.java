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
    FilaDePrioridades filaDePrioridades = new FilaDePrioridades();
    Arvore arvore;

    //compactar arquivo passado
    public void compactar(String arquivo) throws Exception {
        lerArquivo(arquivo);
        criarListaDePrioridade();
        arvore = new Arvore((FilaDePrioridades)filaDePrioridades.clone());
        gerarArquivoCompactado(arquivo);
    }

    private void lerArquivo(String arquivo) throws  Exception{
        // ler o dococumento
       File newFIle = new File(arquivo);
       RandomAccessFile acessoAoArquivo = new RandomAccessFile(newFIle, "rw");// e possivel ler e escrever dentro do arquivo
       this.arquivoDeBytes = new byte[(int)acessoAoArquivo.length()];//ler a quantidade de bytes existentes no arquivo
       acessoAoArquivo.read(this.arquivoDeBytes); //Lê um byte de dados deste arquivo.
       acessoAoArquivo.close(); //fechar instancia do arquivo
   }

       //cria o map com bytes da arvore e adiciona na string
       private String addBytesArvore(Arvore a){
        StringBuilder mapDeBytes = new StringBuilder();
        Map<Byte, String>  map = a.toHashMap();
        //para cada Byte do MAP, pega o valor e adiciona na string -> pega o caminho da arvore e cria um map de bytes
        for (byte arquivoDeBytes : this.arquivoDeBytes) {
            mapDeBytes.append(map.get(arquivoDeBytes));//adicionar na ultima ocorrencia o mapeamento do arquivo de bytes
        }
        return mapDeBytes.toString();
    }
   private void criarListaDePrioridade() { //Aqui há os bytes e sua frequencia
    Map<Byte, Integer> frequenciaDeBytes = new HashMap<>();

   //verifica se o byte possui iteração(chave que o haspMap cria)
    for (byte arquivoDeBytes : this.arquivoDeBytes) {
        if(!frequenciaDeBytes.containsKey(arquivoDeBytes)) { 
            // soma o valor da chave
            frequenciaDeBytes.put(arquivoDeBytes, 1);
            // se existir anda uma casa para a direita e fico como maior prioridade

        } else {
            // se NÃo existir, adicionar o byte com o valor 1
            frequenciaDeBytes.put(arquivoDeBytes,frequenciaDeBytes.get(arquivoDeBytes) + 1); 
        }
    }

    // para cada chave do MAP, gera um novo Nó e o enfileira
    // Cada Nó tem sua chave e valor 
    frequenciaDeBytes.forEach((key, value) -> filaDePrioridades.enfileirar(new No(key, value)));
}


    //gera o arquivo de compactação
    private void gerarArquivoCompactado(String path){ //logica alterada por etapas e separada por bytes
        ArrayList teste = new ArrayList();
        StringBuilder dados = new StringBuilder(addBytesArvore(arvore));
        try {
            // criar um arquivo e abrir ele para escrita
            File arquivo = new File(path+".compress");
            RandomAccessFile arquivoDeEscita = new RandomAccessFile(arquivo.getAbsolutePath(),"rw");

            // escreve primeiro os bytes e suas frequencias (byte freq)
            ArrayList<String> filaString = filaDePrioridades.showFila();

            for (int i = 0; i < filaString.size(); i++) {
                if(i%2==0){  //verifica se é par e
                    arquivoDeEscita.writeByte((byte)Integer.parseInt(filaString.get(i)));  //escrevendo um byte
                }
                else {
                    arquivoDeEscita.writeInt(Integer.parseInt(filaString.get(i)));
                }
            }

            //escrever tres bytes 128 para separar
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
                auxiliar.append(dados.charAt(i)); //criar grupos de 7
                if((i+1)%7==0){
                    byte b = deStringParaByte(auxiliar.toString());
                    arquivoDeEscita.write(b); //escrevendo no arquivo
                    auxiliar = new StringBuilder();
                }
            }

            //bytes para separar
            arquivoDeEscita.write(Byte.MIN_VALUE);
            arquivoDeEscita.write(Byte.MIN_VALUE);
            arquivoDeEscita.write(Byte.MIN_VALUE);

            //escrever o contador
            arquivoDeEscita.write(contador);


            //fecha o processo
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


