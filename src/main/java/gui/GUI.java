package gui;

import dispatchers.Dispatcher;
import dispatchers.DispatcherResponseObserver;
import web.searchThread.SearchThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private SearchThread thread;
    private JPanel rootPanel;
    private JTextField userSearchingField;
    private JLabel userSearchingLabel;
    private JLabel userStartLabel;
    private JTextField userStartField;
    public JTextArea responseArea;
    public JProgressBar searchingProgressBar;
    private JButton searchButton;
    private JButton stopButton;
    private JComboBox deepSelect;
    private final DispatcherResponseObserver
            dispatcherResponseObserver = new DispatcherResponseObserver();
    private Dispatcher dispatcher;

    public GUI(String name) {
        super(name);

        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(rootPanel.getPreferredSize());
        setVisible(true);

        searchButton.addActionListener(new StartFindListener());
        stopButton.addActionListener(new StopFindListener());

        searchingProgressBar.setMinimum(0);
        searchingProgressBar.setMaximum(99);

        searchingProgressBar.setStringPainted(true);

        dispatcherResponseObserver.setGUI(this);
    }

    private class StartFindListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String findValue = userSearchingField.getText();
            String startValue = userStartField.getText();
            int deep = deepSelect.getSelectedIndex() + 1;

            dispatcher = new Dispatcher(startValue, findValue, deep, dispatcherResponseObserver);

            thread = new SearchThread(dispatcher);
            thread.getThread().start();

            dispatcherResponseObserver.setDispatcher(thread.getDispatcher());

        }
    }

    private class StopFindListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            thread.stopSearching();

        }
    }
}
