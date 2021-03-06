package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.UIManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import data.Preferences;

public class AboutFrame
{

	private JFrame frmAbout;
	private JEditorPane dtrpnSnippingToolIs;

	private JSONObject pref; // outputs
	private JSONObject prefIn; // inputs
	private JSONParser prefParser;

	private String version = Preferences.VERSION;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					AboutFrame window = new AboutFrame();
					window.frmAbout.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AboutFrame()
	{

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmAbout = new JFrame();
		frmAbout.setIconImage(Toolkit.getDefaultToolkit().getImage(
				AboutFrame.class.getResource("/images/icons/about_icon.png")));
		frmAbout.setTitle("About");
		frmAbout.setBounds(100, 100, 450, 353);
		frmAbout.setResizable(false);
		frmAbout.setAlwaysOnTop(true);
		frmAbout.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frmAbout.setVisible(true);

		HTMLEditorKit kit = new HTMLEditorKit();
		StyleSheet styleSheet = kit.getStyleSheet();
		// styleSheet.addRule("A {color:red}"); //change links to red

		JPanel panel = new JPanel();
		frmAbout.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[424.00,grow]",
				"[49.00][][22.00][][95.00,grow][][][]"));

		JLabel lblSnippingTool = new JLabel("Snipping Tool++");
		lblSnippingTool.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblSnippingTool.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblSnippingTool, "cell 0 0,alignx center");

		JLabel lblNewLabel = new JLabel(
				"The simple editing and annotation tool");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.add(lblNewLabel, "cell 0 1,alignx center");

		JLabel lblVersion = new JLabel("Version " + version);
		panel.add(lblVersion, "cell 0 2,alignx center");

		JSeparator separator = new JSeparator();
		panel.add(separator, "cell 0 3,grow");

		dtrpnSnippingToolIs = new JEditorPane();
		dtrpnSnippingToolIs.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dtrpnSnippingToolIs.setForeground(UIManager
				.getColor("InternalFrame.borderColor"));
		dtrpnSnippingToolIs.setBackground(UIManager
				.getColor("InternalFrame.borderColor"));
		HyperlinkListener hyperlinkListener = new HyperlinkListener()
		{

			@Override
			public void hyperlinkUpdate(HyperlinkEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		};
		dtrpnSnippingToolIs.addHyperlinkListener(hyperlinkListener);
		dtrpnSnippingToolIs.setEditorKit(kit);
		dtrpnSnippingToolIs.setContentType("text/html");
		dtrpnSnippingToolIs
				.setText("<center><font size = \"4\">All image uploading is currently powered by the Imgur API.  Snipping Tool++ is completely free. By using this program you agree to not re-distribute it commercially, or sell it for any kind of profit.</font>\r\n<p>\r\n<font size = \"4\">If you are pleased with the tool and would like to see more features added in the future and support me in keeping it up-to-date and bug free, please feel free to donate on my website <a href=\"http://www.snippingtoolplusplus.co.nf\">www.snippingtoolplusplus.co.nf</a></font></center>");
		panel.add(dtrpnSnippingToolIs, "cell 0 4,grow");

		JSeparator separator_1 = new JSeparator();
		panel.add(separator_1, "cell 0 5,grow");

		JLabel lblAuthorShaneM = new JLabel("Author: Shane M. Israel");
		panel.add(lblAuthorShaneM, "flowx,cell 0 6");

		JLabel lblOsXSupporting = new JLabel(
				"OS X support & help: Chad R. Marmon");
		panel.add(lblOsXSupporting, "cell 0 7");
	}

}
