    public class Main {
        public static void main(String[] args) {

            try{
                Huffmann huffmann = new Huffmann();
                Huffmann.Compressor comp = huffmann.new Compressor(); 
                Huffmann.Descompressor descomp = huffmann.new Descompressor();
                
                comp.compress("C:\\Users\\adria\\Documents\\GitHub\\estruturaDeDadosII-final\\src\\WhatsApp Image 2022-03-11 at 13.09.31.jpeg");
                descomp.unzip("C:\\Users\\adria\\Documents\\GitHub\\estruturaDeDadosII-final\\src\\WhatsApp Image 2022-03-11 at 13.09.31.jpeg.compress");
            }
            catch (Exception e) {
                 throw new RuntimeException(e);
         }
        }
    }
