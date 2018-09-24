package visualization.model_panels;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent Liu
 */

public class WaTorPanel extends ModelPanel {
    public static final int DEFAULT_FISHBREEDPERIOD = 4;
    public static final int DEFAULT_SHARKBREEDPERIOD = 5;
    public static final int DEFAULT_SHARKSTARVEPERIOD = 5;

    private Slider fbBar = new Slider(0, 10, DEFAULT_FISHBREEDPERIOD);
    private Slider sbBar = new Slider(0, 10, DEFAULT_SHARKBREEDPERIOD);
    private Slider ssBar = new Slider(0, 10, DEFAULT_SHARKSTARVEPERIOD);

    private int fb;
    private int sb;
    private int ss;

    private boolean changeFB;
    private boolean changeSB;
    private boolean changeSS;

    public static final Label fbCaption = new Label("Fish Breed Period:");
    public static final Label sbCaption = new Label("Shark Breed Period:");
    public static final Label ssCaption = new Label("Shark Starve Period:");

    private Label fbValue = new Label(
            Integer.toString((int) fbBar.getValue()));
    private Label sbValue = new Label(
            Integer.toString((int) sbBar.getValue()));
    private Label ssValue = new Label(
            Integer.toString((int) ssBar.getValue()));

    public WaTorPanel() {
        super();
        getStyleClass().add("modelPanel");

        fb = DEFAULT_FISHBREEDPERIOD;
        sb = DEFAULT_SHARKBREEDPERIOD;
        ss = DEFAULT_SHARKSTARVEPERIOD;

        fbBar.setShowTickMarks(true);
        fbBar.setShowTickLabels(true);
        sbBar.setShowTickMarks(true);
        sbBar.setShowTickLabels(true);
        ssBar.setShowTickMarks(true);
        ssBar.setShowTickLabels(true);

        fbBar.setOnMouseReleased(e -> {
            changeFB = true;
            fb = (int) fbBar.getValue();
            fbValue.setText(Integer.toString(fb));
        });

        sbBar.setOnMouseReleased(e -> {
            changeSB = true;
            sb = (int) sbBar.getValue();
            sbValue.setText(Integer.toString(sb));
        });

        ssBar.setOnMouseReleased(e -> {
            changeSS = true;
            ss = (int) ssBar.getValue();
            ssValue.setText(Integer.toString(ss));
        });

        getChildren().addAll(fbCaption, fbValue, fbBar);
        getChildren().addAll(sbCaption, sbValue, sbBar);
        getChildren().addAll(ssCaption, ssValue, ssBar);
    }



    @Override
    public Map<String, String> getParams() {
        var ret = new HashMap<String, String>();
        ret.put("fishBreedPeriod", Integer.toString(fb));
        ret.put("sharkBreedPeriod", Integer.toString(sb));
        ret.put("sharkStarvePeriod", Integer.toString(ss));
        return ret;
    }

    @Override
    public boolean paramsChanged() {
        return changeFB || changeSB || changeSS;
    }
}
