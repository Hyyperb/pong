import javax.swing.JFrame;

public class gameFrame extends JFrame {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 322716435265236788L;

	gameFrame(){
		this.add(new gamePanel());
		this.setTitle("PONG");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}

}
