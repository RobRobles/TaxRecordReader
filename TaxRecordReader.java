import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

public class TaxRecordReader {
	
	public int bSize = 0; 
	public DataInputStream in = null;
 

	public TaxRecordReader(String fileName, int blockSize) throws FileNotFoundException
	{
		bSize = blockSize; 
		String tempFile = fileName; 
		in = new DataInputStream(new FileInputStream(tempFile));

	}
	 
	public Vector<Record> ReadPage() throws IOException
	{

		Vector<Record> vector = new Vector<Record>();
		
		int ssn, nameSize, stateSize, income, endBuffer, frontBuffer;
	
		
		while(in.available() > 0)
		{
			//getting frontBuffer
			frontBuffer = in.readInt();
			in.skipBytes(frontBuffer);
			
			//reading ssn
			ssn = in.readInt();
			System.out.println("ssn: " + ssn);
			//reading size of name 
			nameSize = in.readInt(); 
			//System.out.println("nameSize: " + nameSize);

			char s; 
			char a; 
			String S = "";
			String A = ""; 

			for(int i = 0; i < nameSize; i++)
			{
				s = in.readChar();
				S = S + s; 
			}
			System.out.println(S);

			//reading in income
			income = in.readInt();
			System.out.println("income: " + income);
			stateSize = in.readInt();
			//System.out.println("stateSize: " + stateSize);
			
			System.out.println("bitCount ssn:   " + Integer.bitCount(ssn));
			System.out.println("bitCount income: " + Integer.bitCount(income));

			for(int j = 0; j < stateSize; j++)
			{
				a = in.readChar();
				A = A + a;
			}
			System.out.println(A);
			
			endBuffer = in.readInt(); 
			System.out.println(endBuffer);
			
			in.skipBytes(endBuffer);
			
			Record record = new Record(); 
			record.ssn = ssn; 
			record.name = S; 
			record.income = income; 
			record.state = A;   
			
			vector.add(record); 
		}
		return vector;
	}
	
	public void Close() throws IOException
	{
		in.close(); 
	}
	
	public static void main(String[] args) throws IOException {

		TaxRecordReader readIn = new TaxRecordReader("OutPut.db", 8);
		readIn.ReadPage(); 
		readIn.Close(); 
		
	
	}

}
