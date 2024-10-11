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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

@ConfigGroup("bingo")
public class AuthPanel extends JPanel
{
	public BingoConfig.Panel id = BingoConfig.Panel.AUTH;
	private final BingoScapePlugin plugin;
	private final TokenManager tokenManager;

	@Getter
	@Setter
	private BingoConfig.Panel panel;

	private final JButton submitButton;
	private final JPanel adminPasswordPanel;
	private final FlatTextField connectTextField;
	private final FlatTextField passwordTextField;
	private final FlatTextField adminPasswordTextField;

	// TODO: automatically put cursor at connect field

	public AuthPanel(BingoScapePlugin plugin, TokenManager tokenManager, BingoConfig.Panel panel)
	{
		this.plugin = plugin;
		this.tokenManager = tokenManager;
		this.panel = panel;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(225, 200));

		JPanel connectPanel = new JPanel(new BorderLayout(0, 5));
		JLabel connectLabel = new JLabel("Connection String:");
		connectLabel.setToolTipText("Enter the connection string for the Bingo.");
		connectLabel.setAlignmentX(LEFT_ALIGNMENT);
		connectTextField = new FlatTextField();
		connectTextField.requestFocusInWindow();
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
		connectTextField.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));
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
		passwordTextField.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));
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
		adminPasswordTextField.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));
		adminPasswordPanel.add(adminPasswordLabel, BorderLayout.WEST);
		adminPasswordPanel.add(adminPasswordTextField, BorderLayout.SOUTH);
		adminPasswordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, adminPasswordTextField.getPreferredSize().height));

		add(connectPanel);
		add(Box.createVerticalStrut(10));
		add(passwordPanel);
		add(Box.createVerticalStrut(10));
		add(adminPasswordPanel);
		add(Box.createVerticalStrut(10));

		submitButton = new JButton("Submit");
		setupSubmitButton(submitButton);

		JPanel submitPanel = new JPanel();
		submitPanel.add(submitButton);
		this.add(submitPanel, BorderLayout.SOUTH);
	}

	private void setupSubmitButton(JButton submitButton)
	{
		submitButton.setBackground(ColorScheme.CONTROL_COLOR);
		submitButton.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));
		submitButton.setBorder(new EmptyBorder(10, 10, 10, 10));
		submitButton.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					handleLogin();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				submitButton.setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
				submitButton.setBorder(new LineBorder(ColorScheme.MEDIUM_GRAY_COLOR));
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				submitButton.setBackground(ColorScheme.CONTROL_COLOR);
				submitButton.setBorder(new LineBorder(ColorScheme.BORDER_COLOR));

			}
		});
	}

	private boolean validateInputs() {
		if (getConnectionString().isEmpty() || getPassword().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Connection String and Password are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}

	private void handleLogin()
	{
		if (!validateInputs()) return;
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

	public void showAdminPanel(boolean visible)
	{
		this.adminPasswordPanel.setVisible(visible);
		this.revalidate();
		this.repaint();

	}

	private boolean performLogin()
	{
		Token token;
		if (panel == BingoConfig.Panel.ACTIVE)
		{
			token = LogIn.getSessionToken(getConnectionString(), getPassword());
		}
		else
		{
			token = LogIn.getSessionToken(getConnectionString(), getPassword(), getAdminPassword());
		}

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