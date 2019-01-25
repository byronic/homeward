package com.amalgamated_incorporated.jam.impl.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.HyperlinkListener;

import com.amalgamated_incorporated.jam.api.model.Verbs;
import com.amalgamated_incorporated.jam.api.view.GameView;
import com.amalgamated_incorporated.jam.impl.controller.MessageTopic;
import com.amalgamated_incorporated.messaging.api.Message;
import com.amalgamated_incorporated.messaging.api.MessageController;
import com.amalgamated_incorporated.messaging.api.Subscriber;

public class GameWindow implements GameView, Subscriber {
  /**
   * Windowing pieces
   */
  private JFrame frame = new JFrame();
  private JPanel panel = new JPanel();
  private JEditorPane viewPane = new JEditorPane();
  private JEditorPane inventoryPane = new JEditorPane();
  private JEditorPane commandLabel = new JEditorPane();
  private final MessageController messageSystem;
  private static final Border buttonBorder = new LineBorder(Color.BLACK, 1);

  /*
   * menubar pieces
   */
  JMenuBar menuBar;
  JMenu optionsMenu;
  JMenu helpMenu;
  List<JMenuItem> optionsItems;
  List<JMenuItem> helpItems;
  private HyperlinkListener listener;
  private Function<Verbs, ActionListener> makeActionListener;

  /*
   * These verb buttons are important !!!!
   */
  List<JButton> buttons;

  public GameWindow(HyperlinkListener listener, Function<Verbs, ActionListener> makeActionListener,
      MessageController messageSystem) {
    this.listener = listener;
    this.makeActionListener = makeActionListener;
    this.messageSystem = messageSystem;
    this.messageSystem.subscribe(MessageTopic.VERB_CLICK, this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.amalgamated_incorporated.jam.impl.view.GameView#start()
   */
  @Override
  public void start() {
    buildAndDisplayGui();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.amalgamated_incorporated.jam.impl.view.GameView#setTitle(java.lang.
   * String)
   */
  @Override
  public void setTitle(String s) {
    frame.setTitle(s);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.amalgamated_incorporated.jam.impl.view.GameView#setSceneDescription(java.
   * lang.String)
   */
  @Override
  public void setSceneDescription(String s) {
    viewPane.setText(s);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.amalgamated_incorporated.jam.impl.view.GameView#setCommandDescription(
   * java.lang.String)
   */
  @Override
  public void setCommandDescription(String s) {
    commandLabel.setText(s);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.amalgamated_incorporated.jam.impl.view.GameView#setInventoryDescription(
   * java.lang.String)
   */
  @Override
  public void setInventoryDescription(String s) {
    inventoryPane.setText(s);
  }

  private void buildContent(JFrame aFrame) {
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    viewPane = setupPane(listener);
    inventoryPane = setupPane(listener);
    commandLabel = setupPane(listener);

    panel.add(viewPane);
    panel2.add(commandLabel);
    panel.setPreferredSize(new Dimension(620, 350));
    panel2.setPreferredSize(new Dimension(770, 70));
    panel3.setPreferredSize(new Dimension(630, 30));
    panel4.setPreferredSize(new Dimension(630, 30));
    this.buttons = Arrays.asList(Verbs.values()).stream().map(x -> {
      JButton b = new JButton(x.toString().replace('_', '/').replace("GO", "WALK"));
      b.addActionListener(makeActionListener.apply(x));
      return b;
    }).collect(Collectors.toList());
    buttons.forEach((JButton b) -> {
      panel3.add(b);
    });
    panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    panel4.add(inventoryPane);

    aFrame.getContentPane().add(panel);
    aFrame.getContentPane().add(panel2);
    aFrame.getContentPane().add(panel4);
    aFrame.getContentPane().add(panel3);
  }

  private JEditorPane setupPane(HyperlinkListener a) {
    JEditorPane pane = new JEditorPane();
    pane = new JEditorPane();
    pane.setContentType("text/html");
    pane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
    pane.setFont(new Font("Courier", Font.PLAIN, 16));
    pane.setEditable(false);
    pane.setOpaque(false);
    pane.addHyperlinkListener(a);
    return pane;
  }

  private void buildMenus(JFrame aFrame) {
    menuBar = new JMenuBar();
    optionsMenu = new JMenu("Options");
    optionsItems = Arrays.asList(new JMenuItem("Save", KeyEvent.VK_T), new JMenuItem("Load", KeyEvent.VK_L));
    optionsItems.forEach(item -> {
      optionsMenu.add(item);
    });
    helpItems = Arrays.asList(new JMenuItem("About"));
    helpMenu = new JMenu("Help");
    helpItems.forEach(item -> {
      helpMenu.add(item);
    });
    menuBar.add(optionsMenu);
    menuBar.add(helpMenu);
    aFrame.setJMenuBar(menuBar);
  }

  private void buildAndDisplayGui() {
    frame = new JFrame();
    frame.setLayout(new FlowLayout());
    buildContent(frame);
    buildMenus(frame);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(new Dimension(800, 600));

    frame.setVisible(true);
  }

  @Override
  public void receive(String topic, Message message) {
    buttons.forEach((JButton b) -> {
      if (b.getText().equals(message.get(MessageTopic.VERB_CLICK).toString())) {
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setBorder(GameWindow.buttonBorder);
      } else {
        b.setBackground(Color.GRAY);
        b.setForeground(Color.BLACK);
        b.setBorder(GameWindow.buttonBorder);
      }
    });
  }

}
