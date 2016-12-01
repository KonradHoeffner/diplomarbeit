package wortschatz2dbpedia.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class SerializeTest extends JFrame
{
	JTextField textField = new JTextField("text");

	private void saveState(String fileName) throws IOException
	{
		
	}

	private void loadState() throws IOException
	{
		
	}
	
	void createAndShowGUI()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(textField);
		JButton button = new JButton("save");
		this.getContentPane().add(button);
		this.getContentPane().setLayout(new GridLayout(0,1));
		this.setVisible(true);
		this.setSize(300,300);

		ActionListener listener = 
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						try
						{
							System.out.println("saving");
							new ObjectOutputStream(new FileOutputStream("temp/temp.ser")).writeObject(this);
						} catch (FileNotFoundException e)
						{
							e.printStackTrace();
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				};
				button.addActionListener(listener);
		
	}

	public SerializeTest()
	{
		super();
		createAndShowGUI();
	}

	public static void main(String[] args)
	{
		new SerializeTest();
	}
}
