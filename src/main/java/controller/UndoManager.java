package controller;

import java.util.Stack;

public class UndoManager {
    private final Stack<Command> history = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    private Command currentCommand;

    public void beginCommand(Command command) {
        if (currentCommand != null && currentCommand.end()) {
            history.push(currentCommand);
        }
        currentCommand = command;
        redoStack.clear();
        command.execute();
    }

    public void endCommand() {
        if (currentCommand != null && currentCommand.end()) {
            history.push(currentCommand);
        }
        currentCommand = null;
    }

    public void cancelCommand() {
        currentCommand = null;
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command command = history.pop();
            if (command.undo()) {
                redoStack.push(command);
            }
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            if (command.redo()) {
                history.push(command);
            }
        }
    }
}
