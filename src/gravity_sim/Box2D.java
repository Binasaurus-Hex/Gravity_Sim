package gravity_sim;

public class Box2D {
	private int rowStart;
	private int rowEnd;
	private int columnStart;
	private int columnEnd;
	private int[][] array;
	
	public Box2D(int rowStart,
				 int rowEnd,
				 int columnStart,
				 int columnEnd){
		this.rowStart=rowStart;
		this.rowEnd=rowEnd;
		this.columnStart=columnStart;
		this.columnEnd=columnEnd;
		setArray(new int[rowEnd-rowStart][columnEnd-columnStart]);
		
	}
	public int getColumnEnd() {
		return columnEnd;
	}
	public void setColumnEnd(int columnEnd) {
		this.columnEnd = columnEnd;
	}
	public int getColumnStart() {
		return columnStart;
	}
	public void setColumnStart(int columnStart) {
		this.columnStart = columnStart;
	}
	public int getRowEnd() {
		return rowEnd;
	}
	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}
	public int getRowStart() {
		return rowStart;
	}
	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}
	
	public void setArray(int[][] array){
		this.array=array;
	}
	
	public int[][] getArray(){
		return array;
	}
	
	public int getHeight(){
		return (rowEnd-rowStart);
	}
	public int getWidth(){
		return (columnEnd-columnStart);
	}
}
