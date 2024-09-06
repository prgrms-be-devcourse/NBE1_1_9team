package gc.cafe.util.uuid;

import com.fasterxml.uuid.Generators;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UuidUtils {

    private static final int BYTES_SIZE = 16;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static byte[] generateId() {
        UUID newUuid = Generators.timeBasedGenerator().generate();
        String[] splitUuid = newUuid.toString().split("-");
        String custom = splitUuid[3] + splitUuid[2] + splitUuid[1] + splitUuid[0] + splitUuid[4];

        ByteBuffer bb = ByteBuffer.wrap(new byte[BYTES_SIZE]);
        bb.putLong(Long.parseUnsignedLong(custom.substring(0, BYTES_SIZE), 16));
        bb.putLong(Long.parseUnsignedLong(custom.substring(BYTES_SIZE), 16));
        return bb.array();
    }

    public static String bytesToString(byte[] id) {
        char[] hexChars = new char[id.length * 2];
        for (int i = 0; i < id.length; i++) {
            int v = id[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }
}
