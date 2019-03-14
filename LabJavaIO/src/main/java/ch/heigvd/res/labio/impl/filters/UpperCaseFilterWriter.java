package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    String result = "";
    for(int i = off; i < off + len; ++i){
      result += Character.toUpperCase(str.charAt(i));
    }

    super.write(result, 0, result.length());
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String result = "";
    for(int i = off; i < off + len; ++i){
      result += Character.toUpperCase(cbuf[i]);
    }

    super.write(result, 0, result.length());
  }

  @Override
  public void write(int c) throws IOException {
    char result = (char)c;
    result = Character.toUpperCase(result);

    super.write(result);
  }

}
