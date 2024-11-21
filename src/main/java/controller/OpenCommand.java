package controller;

public class OpenCommand extends Command {
    private String fileName;

    public OpenCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        model.retrieve(fileName);
    }

    @Override
    public boolean undo() {
        return false;
    }

    @Override
    public boolean redo() {
        return false;
    }
}
