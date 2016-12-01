package wortschatz2dbpedia.gui;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class SerializeTestMinimal extends JFrame implements Externalizable
{
	JTextField textField = new JTextField("text "+new Date());

	void createAndShowGUI() throws FileNotFoundException, IOException
	{
		this.setTitle("my frame");
		this.setSize(200,200);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(textField);
		// DOES NOT WORK:
		//new ObjectOutputStream(new FileOutputStream("temp/temp.ser")).writeObject(this);
	}

	public SerializeTestMinimal() throws FileNotFoundException, IOException
	{
		createAndShowGUI();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		JFrame frame;
		if(new File("temp/temp.ser").exists())
			frame = (SerializeTestMinimal) new ObjectInputStream(new FileInputStream("temp/temp.ser")).readObject();
		else
			frame = new SerializeTestMinimal();
		// save frame (normally at closing time but just inserted here to keep example small)
		new ObjectOutputStream(new FileOutputStream("temp/temp.ser")).writeObject(frame);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException
	{
		System.out.print("Reading object...");
 		createAndShowGUI();
		textField.setText((String)in.readObject());
		System.out.println("finished");
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException
	{
		System.out.println("Writing Object...");
		out.writeObject(textField.getText());
		System.out.println("finished");
	}
}