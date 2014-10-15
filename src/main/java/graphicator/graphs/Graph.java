package graphicator.graphs;

public class Graph {
  protected String name;
  protected String[][] table;
  protected String[] linesHeaders;
  protected String[] columnsHeaders;

  public Graph(String name, int lineNumbers, int columnsNumber) {
    this.name = name;
    table = new String[lineNumbers][columnsNumber];
    linesHeaders = new String[lineNumbers];
    columnsHeaders = new String[columnsNumber];
  }

  public void setLineHeader(int lineIndex, String header) {
    linesHeaders[lineIndex] = header;
  }

  public void setColumnHeader(int columnIndex, String header) {
    columnsHeaders[columnIndex] = header;
  }

  public void setLine(int lineIndex, String[] values) {
    table[lineIndex] = values;
  }

  public void setCell(int lineIndex, int columnIndex, String value) {
    table[lineIndex][columnIndex] = value;
  }

  protected String[][] getTable() {
    return table;
  }

  protected String[] getLinesHeaders() {
    return linesHeaders;
  }

  protected String[] getColumnsHeaders() {
    return columnsHeaders;
  }

  public String toString() {
    String graph = name + "\n";
    graph = graph.concat("\t");
    for(String columnHeader : columnsHeaders) {
      graph = graph.concat(columnHeader + "\t");
    }
    graph = graph.concat("\n");
    for(int i = 0; i < table.length; i++) {
      graph = graph.concat(linesHeaders[i] + "\t");
      for(int j = 0; j < table[0].length; j++) {
        graph = graph.concat(table[i][j] + "\t");
      }
      graph = graph.concat("\n");
    }

    return graph;
  }
}
