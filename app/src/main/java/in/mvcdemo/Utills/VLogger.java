package in.mvcdemo.Utills;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import in.mvcdemo.BuildConfig;


public class VLogger {

    private static final String LOGGER_TAG = "TeamMate";
    private static final boolean isLogOn = true;
    private static String fileNamePattern = "yyyy_MM_dd";

    public static Logger getLogger() {
        if (isLogOn) {
            return Logger.getLogger(LOGGER_TAG);
        }
        return null;

    }

    public static void infoLog(String message) {
        if (getLogger() != null && BuildConfig.DEBUG) {
            getLogger().info(message);
        }
//        logTofile(message);
    }

    public static void infoLog(String message, String data) {
        if (getLogger() != null && BuildConfig.DEBUG) {
            getLogger().info(message);
            getLogger().info(data);

        }
//        logTofile(message);
    }

    public static void infoError(String msessage, Exception e) {
        if (getLogger() != null && BuildConfig.DEBUG) {
            getLogger().log(Level.SEVERE, msessage, e);
        }
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        String fullMessage = msessage + "\n" + exceptionAsString;
        logTofile(fullMessage);
    }

    public static void logTofile(final String str) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String pattern = "[dd/MM/yyyy HH:mm:ss]";
                    SimpleDateFormat df = new SimpleDateFormat(pattern);
                    StringBuilder builder = new StringBuilder();
                    builder.append("\n" + df.format(new Date()) + "=>" + str);
                    File file = getFile();
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(file, true);
                    fileWriter.write(builder.toString());
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public static File getFile() {
        SimpleDateFormat df = new SimpleDateFormat(fileNamePattern);
        String fileName = df.format(new Date()) + "" + ".txt";
        File dir = new File(Constant.APP_LOGS_FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File logFile = new File(dir, fileName);
        return logFile;
    }

    public void logD(String message) {
        if (getLogger() != null && BuildConfig.DEBUG) {
            getLogger().log(Level.ALL, message);

        }
    }

}
