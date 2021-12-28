package interbase.interclient;

public class JavaNIOByteToCharConverters {
    private java.nio.ByteBuffer byteBuffer;
    private java.nio.charset.CharsetDecoder decoder;
    private String encoding;
    private int nextByteIndex;
    private int nextCharIndex;

    protected JavaNIOByteToCharConverters(String encoding, boolean alternateUTF8Encoding, boolean doNotReportError) throws java.io.UnsupportedEncodingException {
        //JDK 8 follows strict UTF8 encoding ,"UTF8J" retains relaxed UTF8 encoding
        if (alternateUTF8Encoding &&
                (encoding.equalsIgnoreCase("UTF8") || encoding.equalsIgnoreCase("UTF-8") || encoding.equalsIgnoreCase("UTF_8")))
            encoding = "UTF8J";
        decoder = java.nio.charset.Charset.forName(encoding).newDecoder();
        decoder.onMalformedInput(doNotReportError ? java.nio.charset.CodingErrorAction.REPLACE : java.nio.charset.CodingErrorAction.REPORT);
        decoder.onUnmappableCharacter(doNotReportError ? java.nio.charset.CodingErrorAction.REPLACE : java.nio.charset.CodingErrorAction.REPORT);
        this.encoding = encoding;
        nextByteIndex = 0;
        nextCharIndex = 0;
    }

    public int convert(byte[] rawBytes, int byteOffset, int byteEnd, char[] output, int charOffset, int charEnd) throws java.nio.charset.MalformedInputException, java.nio.BufferOverflowException, java.nio.charset.UnmappableCharacterException, java.nio.charset.CharacterCodingException {
        //first pass, initialize ByteBuffer
        if (byteBuffer == null) byteBuffer = java.nio.ByteBuffer.allocate(byteEnd - byteOffset + 3);
        int spareBytesPosition = byteBuffer.position();
        //increase ByteBuffer if needed.
        if (byteBuffer.limit() < (byteEnd - byteOffset)) {
            java.nio.ByteBuffer newBB = java.nio.ByteBuffer.allocate(byteEnd - byteOffset + byteBuffer.position() + 3);
            byteBuffer.flip();
            while (byteBuffer.position() < byteBuffer.limit())
                newBB.put(byteBuffer.get());
            byteBuffer = newBB;
        }
        byteBuffer.put(rawBytes, byteOffset, byteEnd - byteOffset);
        byteBuffer.flip();
        java.nio.CharBuffer charBuffer = java.nio.CharBuffer.wrap(output, charOffset, charEnd - charOffset).slice();
        java.nio.charset.CoderResult result = null;
        result = decoder.decode(byteBuffer, charBuffer, false);
        nextByteIndex = byteBuffer.position() + byteOffset - spareBytesPosition;
        if (result.isError()) result.throwException();
        if (result == java.nio.charset.CoderResult.UNDERFLOW && (byteBuffer.position() < (byteBuffer.limit()))) {
            byte[] unconvertedBytes = new byte[byteBuffer.limit() - byteBuffer.position()];
            byteBuffer.get(unconvertedBytes);
            nextByteIndex = byteBuffer.position() + byteOffset - spareBytesPosition;
            byteBuffer.clear();
            byteBuffer.put(unconvertedBytes);
        } else {
            byteBuffer.clear();
        }
        charBuffer.flip();
        nextCharIndex = charBuffer.limit();
        //to simulate sun.io.ConversionBufferFullException
        if (result == java.nio.charset.CoderResult.OVERFLOW) result.throwException();
        return nextCharIndex;
    }

    public char[] convertAll(byte[] rawBytes) throws java.nio.charset.CharacterCodingException {
        java.nio.ByteBuffer byteBuffer = java.nio.ByteBuffer.wrap(rawBytes);
        java.nio.CharBuffer charBuffer = decoder.decode(byteBuffer);
        char[] returnVal = new char[charBuffer.limit()];
        charBuffer.get(returnVal);
        return returnVal;
    }

    public String getCharacterEncoding() {
        return encoding;
    }
}