import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class Log {

    /**
     * Writes data of a request on the log.
     * @param request Request whose data you want to log.
     * @throws IOException Can't open log file.
     */
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

    /**
     * Generates the current timestamp
     * @return String containing the current timestamp.
     */
    private String getTimestamp(){
        return String.valueOf(new Date().getTime());
    }

    /**
     * Generates a string that contains the parameters.
     * @param parameters Request's parameters.
     * @return String containing the parameters.
     */
    private String getParametersAsString(Map<String, String> parameters){
        String result = "";
        for (String key : parameters.keySet()){
            result = result + key + "=" + parameters.get(key) + "&";
        }
        return result;
    }

}
