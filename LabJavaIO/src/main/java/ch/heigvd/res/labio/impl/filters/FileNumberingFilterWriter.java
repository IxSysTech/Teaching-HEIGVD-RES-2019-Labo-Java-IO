package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private int counter = 1;
  private boolean isBackSlashR = false;

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String substr = str.substring(off, off + len);
    String[] result = Utils.getNextLine(substr);
    String temp = null;
    if(counter == 1){
      writeLineNumber();
    }

    while(result[1] != temp){
      temp = result[1];
      if(result[0] != "") {
        super.write(result[0], 0, result[0].length());
        writeLineNumber();
      } else {
        super.write(result[1], 0, result[1].length());
      }
      result = Utils.getNextLine(result[1]);
    }
    // si la dernière ligne n'est pas vide
    if(!temp.equals("") && !temp.equals(substr)){
      super.write(result[1], 0, result[1].length());
    }


  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for(int i = 0; i < cbuf.length; ++i){
      super.write((int)cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    char r = (char)c;

    if(counter == 1){
      writeLineNumber();
    }

    super.write(c);

    if(r == '\r'){
      isBackSlashR = true;
    } else if(isBackSlashR) {
      if (r == '\n'){
        writeLineNumber();
        isBackSlashR = false;
      }
    } else if(r == '\n'){
      writeLineNumber();
    }
  }

  private void writeLineNumber() throws IOException {
    String lineNumber = String.valueOf(counter);
    super.write(lineNumber, 0, lineNumber.length());
    super.write('\t');
    counter++;
  }
}
