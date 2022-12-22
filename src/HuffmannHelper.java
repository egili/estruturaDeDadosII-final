
public class HuffmannHelper {
	
	// compressor
	
    public static byte converterStringByte(String x){
			
        byte ret = (byte) 0;

        for (byte ps = (byte)6, pb = (byte)0; pb < 7; ps--, pb++) {
            if(x.charAt(ps) == '1')
                ret = setBit(pb, ret);
        }
        return ret;
    }

	public static byte setBit(byte qualBit,byte valor){
        byte mascara = (byte) 1;

        mascara <<= qualBit;

        return (byte) (valor | mascara);
    }

    public static byte resetBit (byte qualBit,byte valor){
        byte mascara = (byte) 1;

        mascara >>= qualBit;
        mascara = (byte) ~mascara;

        return (byte) (valor & mascara);
    }
    
    // descompresor
    
    public static int converterArrByteArrInt(byte[] array) {
        int valor = 0;
        
        for (byte b : array) {
            valor = (valor << 8) + (b & 0xFF);
        }
        return valor;
    }
}
