public class Main {
    public static void main(String[] args) {

        try{
            Huffmann huffmann = new Huffmann();
            Huffmann.Compressor comp = huffmann.new Compressor(); 
            Huffmann.Descompressor descomp = huffmann.new Descompressor();
            
            comp.compress("src\\TesteCSS.css");
            descomp.unzip("src\\TesteCSS.css.zip");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}