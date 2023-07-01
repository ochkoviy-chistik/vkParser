package dispatchers;

import models.UserModel;
import gui.GUI;

public class DispatcherResponseObserver {
    static private UserModel user;
    private GUI gui;
    private Dispatcher dispatcher;

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void addUser(UserModel user) {
        this.gui.responseArea.append(user.toString() + "\n");
    }

    public void addUser(String user) {
        this.gui.responseArea.append(user + "\n");
    }

    public void setProgressInfo(double per) {
        this.gui.searchingProgressBar.setValue(
                (int) per
        );
    }

}
