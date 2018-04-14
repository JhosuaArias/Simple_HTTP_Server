import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class Log {

    public void WriteLog(HttpRequest request) throws IOException {
        String logFileName = "bitacora.csv";
        FileWriter fw = new FileWriter(logFileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(request.method.getName() + ",");
        bw.write(this.getTimestamp() + ",");
        bw.write(request.host + ",");
        bw.write(request.referer + ",");
        bw.write(request.filename + ",");
        bw.write(this.getParametersAsString(request.parameters) + ",");
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
