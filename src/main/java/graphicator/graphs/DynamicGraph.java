package graphicator.graphs;

/*
 *  Estrutura de gráfico que suporta adição dinâmica de linhas
 */
public class DynamicGraph extends Graph {
  private int index;
  private int columnsNumber;

  public DynamicGraph(String name, int columnsNumber) {
    super(name, 0, columnsNumber);
    index = 0;
    this.columnsNumber = columnsNumber;
  }

  public void addLine(String lineHeader, String[] values) {
    table = expand(table);
    linesHeaders = expand(linesHeaders);
    linesHeaders[index] = lineHeader;
    for(int i = 0; i < values.length; i++) {
      table[index][i] = values[i];
    }
    index++;
  }

  private String[][] expand(String[][] table) {
    String[][] ret = null;
    if(table.length == 0) {
      ret = new String[1][columnsNumber];
    } else {
      ret = new String[table.length + 1][table[0].length];
    }
    for(int i = 0; i < table.length; i++) {
      for(int j = 0; j < table[0].length; j++) {
        ret[i][j] = table[i][j];
      }
    }

    return ret;
  }

  private String[] expand(String[] array) {
    String[] ret = null;
    if(array.length == 0) {
      ret = new String[1];
    } else {
      ret = new String[array.length + 1];
    }
    for(int i = 0; i < array.length; i++) {
      ret[i] = array[i];
    }

    return ret;
  }
}
