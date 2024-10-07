package com.bingo.panels;


import com.bingo.io.LogIn;
import com.bingo.io.Token;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

@ConfigGroup("bingo")
public class AuthPanel extends JPanel
{
	private final JLabel submitButton;
	private final FlatTextField connectTextField;
	private final FlatTextField passwordTextField;

	public AuthPanel(ActiveBingoPanel parent)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel connectLabel = new JLabel("Connection String:");
		connectLabel.setToolTipText("Enter the connection string for the Bingo.");
		connectLabel.setAlignmentX(LEFT_ALIGNMENT);
		connectTextField = new FlatTextField();
		connectTextField.setPreferredSize(new Dimension(200, 20));
		connectTextField.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		connectTextField.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));

		JLabel passwordLabel = new JLabel("Password:");
		passwordTextField = new FlatTextField();
		passwordTextField.setPreferredSize(new Dimension(200, 20));
		passwordTextField.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		passwordTextField.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));

		this.add(connectLabel);
		this.add(connectTextField);
		this.add(Box.createVerticalStrut(10));
		this.add(passwordLabel);
		this.add(passwordTextField);
		this.add(Box.createVerticalStrut(10));

		submitButton = new JLabel("Submit");
		submitButton.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
		submitButton.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		submitButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					submitButton.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					Token token = LogIn.getSessionToken(getConnectionString(), getPassword());
					if (token == null)
					{
						JOptionPane.showMessageDialog(AuthPanel.this, "Login failed. Please check your credentials.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else
					{
						parent.plugin.setActiveToken(token);
						System.out.println("WE MADE IT HERE FOLKS");
						parent.updatePanelVisibility();
					}
					submitButton.setBackground(ColorScheme.DARKER_GRAY_COLOR);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				submitButton.setBackground(ColorScheme.DARK_GRAY_COLOR);
				submitButton.setBorder(BorderFactory.createLineBorder(ColorScheme.MEDIUM_GRAY_COLOR));
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				submitButton.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
				submitButton.setBackground(ColorScheme.DARKER_GRAY_COLOR);
			}
		});

		JPanel submitPanel = new JPanel();
		submitPanel.add(submitButton);
		this.add(submitPanel, BorderLayout.SOUTH);
	}

	public String getConnectionString()
	{
		return connectTextField.getText();
	}

	public String getPassword()
	{
		return passwordTextField.getText();
	}
}