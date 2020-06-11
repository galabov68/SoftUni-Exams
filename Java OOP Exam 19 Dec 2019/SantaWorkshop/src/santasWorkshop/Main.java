package santasWorkshop;

import santasWorkshop.core.Engine;
import santasWorkshop.core.EngineImpl;

public class Main {
    public static void main(String[] args) {
        Engine engine = new EngineImpl();
        engine.run();
    }
}
