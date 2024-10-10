package com.bingo.panels;

import com.bingo.BingoConfig;
import com.bingo.BingoScapePlugin;
import com.bingo.io.LogIn;
import com.bingo.io.Token;
import com.bingo.io.TokenManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.Setter;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

@ConfigGroup("bingo")
public class AuthPanel extends JPanel
{
	// TODO: automatically put cursor at connect field
	private BingoScapePlugin plugin;

	@Inject
	private TokenManager tokenManager;

	@Setter
	private BingoConfig.Panel panel;

	private final JLabel submitButton;
	public JPanel adminPasswordPanel;
	private final FlatTextField connectTextField;
	private final FlatTextField passwordTextField;
	private final FlatTextField adminPasswordTextField;

	public AuthPanel(BingoScapePlugin plugin, BingoConfig.Panel panel)
	{
		this.plugin = plugin;
		this.panel = panel;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(225, 200));

		JPanel connectPanel = new JPanel(new BorderLayout(0, 5));
		JLabel connectLabel = new JLabel("Connection String:");
		connectLabel.setToolTipText("Enter the connection string for the Bingo.");
		connectLabel.setAlignmentX(LEFT_ALIGNMENT);
		connectTextField = new FlatTextField();
		connectTextField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					passwordTextField.requestFocusInWindow();
				}
			}
		});
		connectTextField.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		connectTextField.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
		connectPanel.add(connectLabel, BorderLayout.WEST);
		connectPanel.add(connectTextField, BorderLayout.SOUTH);
		connectPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, connectTextField.getPreferredSize().height));

		JPanel passwordPanel = new JPanel(new BorderLayout(0, 5));
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setToolTipText("Enter the password provided to you by the bingo admin.");
		passwordLabel.setAlignmentX(LEFT_ALIGNMENT);
		passwordTextField = new FlatTextField();
		passwordTextField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					handleLogin();
				}
			}
		});
		passwordTextField.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		passwordTextField.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
		passwordPanel.add(passwordLabel, BorderLayout.WEST);
		passwordPanel.add(passwordTextField, BorderLayout.SOUTH);
		passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordTextField.getPreferredSize().height));

		adminPasswordPanel = new JPanel(new BorderLayout(0, 5));
		adminPasswordPanel.setVisible(false);
		JLabel adminPasswordLabel = new JLabel("Admin Password:");
		adminPasswordLabel.setToolTipText("Enter the admin password.");
		adminPasswordLabel.setAlignmentX(LEFT_ALIGNMENT);
		adminPasswordTextField = new FlatTextField();
		adminPasswordTextField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					handleLogin();
				}
			}
		});
		adminPasswordTextField.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
		adminPasswordTextField.setBorder(BorderFactory.createLineBorder(ColorScheme.BORDER_COLOR));
		adminPasswordPanel.add(adminPasswordLabel, BorderLayout.WEST);
		adminPasswordPanel.add(adminPasswordTextField, BorderLayout.SOUTH);
		adminPasswordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, adminPasswordTextField.getPreferredSize().height));

		this.add(connectPanel);
		this.add(Box.createVerticalStrut(10));
		this.add(passwordPanel);
		this.add(Box.createVerticalStrut(10));
		this.add(adminPasswordPanel);
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
					handleLogin();
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

	private void handleLogin()
	{
		boolean loginSuccessful = performLogin();

		if (loginSuccessful)
		{
			this.setVisible(false);
			if (this.panel == BingoConfig.Panel.MODIFY)
			{
				plugin.getModifyBingoPanel().handleSuccessfulLogin();
			}
			else
			{
				plugin.getActiveBingoPanel().handleSuccessfulLogin();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Login Failed", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void unhideAdminPanel(boolean ans)
	{
		this.adminPasswordPanel.setVisible(ans);
		this.revalidate();
		this.repaint();
	}

	private boolean performLogin()
	{
		Token token = LogIn.getSessionToken(getConnectionString(), getPassword());
		if (token == null || token.getId() == 0)
		{
			JOptionPane.showMessageDialog(AuthPanel.this, "Login failed. Please check your credentials.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			tokenManager.setActiveToken(token);
			return true;
		}
		submitButton.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		return false;
	}

	public String getConnectionString()
	{
		return connectTextField.getText();
	}

	public String getPassword()
	{
		return passwordTextField.getText();
	}

	public String getAdminPassword()
	{
		return adminPasswordTextField.getText();
	}
}