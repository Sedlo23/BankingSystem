package UI;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * UI
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public class LogMessenger extends OutputStream
{
    private final JTextPane destination;

    private int logNum=0;

    SimpleDateFormat objSDF = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    private String logName="LogFile_"+objSDF.format(Date.from(Instant.now()))+".txt";

    public LogMessenger(JTextPane destination)
    {

        this.destination = destination;
    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException
    {

        final String text = new String (buffer, offset, length);
        SwingUtilities.invokeLater(new Runnable ()
        {
            @Override
            public void run()
            {
                if (!text.isBlank())
                {
                    destination.setText("["+ objSDF.format(Date.from(Instant.now())) +"."+logNum+"] "+text);
                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter(new FileWriter(logName, true));
                        writer.append('\n');
                        writer.append("["+ objSDF.format(Date.from(Instant.now())) +"."+logNum+"] "+text);
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                logNum++;
            }
        });
    }

    @Override
    public void write(int b) throws IOException
    {
        write (new byte [] {(byte)b}, 0, 1);
    }


}

