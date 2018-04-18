
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MimeTypes {
    private HashMap<String,String> formatToContentType;

    /**
     * Initialize a hashmap that contains all the Mimetypes, then fill the HashMap
     */
    public MimeTypes(){
        this.formatToContentType = new HashMap<>();
        this.init();
    }

    /**
     * This method fills the HashMap with extensions as keys and Mimetypes as values
     */
    private void init() {
        File file = new File("MimeTypes.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                String[] split = st.split(":");
                this.formatToContentType.put(split[0],split[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method return the value of a extension
     * @param format the extension of a file
     * @return the Mimetype of the extension
     */
    public String getMimeType(String format){
        return this.formatToContentType.get(format);
    }

}
