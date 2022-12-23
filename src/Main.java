public class Main {
    public static void main(String[] args) {

        try{
            Huffmann huffmann = new Huffmann();
            Huffmann.Compressor comp = huffmann.new Compressor(); 
            Huffmann.Descompressor descomp = huffmann.new Descompressor();
            
            comp.compress("src\\TesteXML.xml");
            descomp.unzip("src\\TesteXML.xml.zip");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}