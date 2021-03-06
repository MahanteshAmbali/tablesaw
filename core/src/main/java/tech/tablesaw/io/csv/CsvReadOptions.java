package tech.tablesaw.io.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import tech.tablesaw.api.ColumnType;

@Value @Accessors(fluent = true)
@Builder(builderMethodName = "hiddenBuilder")
public class CsvReadOptions {

  private final InputStream stream;
  private final String tableName;
  private final ColumnType[] columnTypes;
  @Builder.Default private final boolean header = true;
  @Builder.Default private final char separator = ',';
  @Builder.Default private final boolean sample = true;

  public static CsvReadOptionsBuilder builder(InputStream stream, String tableName) {
    return hiddenBuilder().stream(stream).tableName(tableName);
  }

  public static CsvReadOptionsBuilder builder(File file) throws FileNotFoundException {
    return hiddenBuilder().stream(new FileInputStream(file)).tableName(file.getName());
  }

  public static CsvReadOptionsBuilder builder(String file) throws FileNotFoundException {
    return builder(new File(file));
  }

}
