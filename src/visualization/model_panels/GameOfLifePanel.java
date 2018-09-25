package visualization.model_panels;

import java.util.HashMap;
import java.util.Map;

/**
 * GameOfLifePanel extends the abstract class ModelPanel.
 * It has no extra parameters.
 *
 * @author Vincent Liu
 */

public class GameOfLifePanel extends ModelPanel {
    public GameOfLifePanel() { super(); }

    @Override
    public Map<String, String> getParams() { return new HashMap<>(); }
}
