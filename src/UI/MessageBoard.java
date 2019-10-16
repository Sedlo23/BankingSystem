package UI;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * UI
 *
 * @author Jan Frantisek Sedlacek
 * @version 1.0
 * @since 16.10.19
 */
public class MessageBoard extends OutputStream
{
    private final JTextPane destination;

    public MessageBoard(JTextPane destination)
    {

        this.destination = destination;
    }

    @Override
    public void write(byte[] buffer, int offset, int length) throws IOException
    {
        destination.setText("");
        final String text = new String (buffer, offset, length);
        SwingUtilities.invokeLater(new Runnable ()
        {
            @Override
            public void run()
            {
                destination.setText(destination.getText()+text);
            }
        });
    }

    @Override
    public void write(int b) throws IOException
    {
        write (new byte [] {(byte)b}, 0, 1);
    }


}

