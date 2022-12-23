public class Main {
    public static void main(String[] args) {

        try{
            Huffmann huffmann = new Huffmann();
            Huffmann.Compressor comp = huffmann.new Compressor(); 
            Huffmann.Descompressor descomp = huffmann.new Descompressor();
            
            comp.compress("C:\\Users\\olive\\OneDrive\\Documentos\\GitHub\\estruturaDeDadosII-final\\src\\TesteTexto.txt");
            descomp.unzip("C:\\Users\\olive\\OneDrive\\Documentos\\GitHub\\estruturaDeDadosII-final\\src\\TesteTexto.txt.zip");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}