package graphics;
import jexer.TApplication;

public class JexerExample extends TApplication {

    public JexerExample() throws Exception {
        super(BackendType.SWING);

        addToolMenu();
        addFileMenu();
        addWindowMenu();
    }

    public static void main(String [] args) throws Exception {
        JexerExample app = new JexerExample();
        app.run();
    }
}
