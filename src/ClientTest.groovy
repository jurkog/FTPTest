/**
 * Created by jurko on 29/07/16.
 */
class ClientTest extends GroovyTestCase {
    File testFile = new File("/home/jurko/Desktop/test-text.txt");
    Client c = new Client();

    void testGetFileLength() {
        assertEquals(3, c.getFileLength(testFile));
    }

    void testFileContents() {
        byte[] fileByteArray = new byte[c.getFileLength(testFile)];
        c.convertFileToByteArray(testFile, fileByteArray);
        assertEquals("Fam", new String(fileByteArray).trim());
    }
}
