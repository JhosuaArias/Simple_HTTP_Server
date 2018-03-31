import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class Log {

    private String logFileName;

    public void WriteLog(HttpRequest request) throws IOException {
        FileWriter fw = new FileWriter(this.logFileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Metodo: " + request.method.getName());
        bw.newLine();
        bw.write("Estampilla de tiempo: " + this.getTimestamp());
        bw.newLine();
        bw.write("Servidor: " + request.host);
        bw.newLine();
        bw.write("Refiere: " + request.referer);
        bw.newLine();
        bw.write("URL: " + request.filename);
        bw.newLine();
        bw.write("Datos: " + this.getParametersAsString(request.parameters));
        bw.newLine();
        bw.newLine();
        bw.close();
    }

    private String getTimestamp(){
        return String.valueOf(new Date().getTime());
    }

    private String getParametersAsString(Map<String, String> parameters){
        String result = "";
        for (String key : parameters.keySet()){
            result = result + key + "=" + parameters.get(key) + "&";
        }
        return result;
    }

}
