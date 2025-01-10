public class RadioButton {

    public int bX;
    public int bY;
    public int lX;
    public int lY;

    String label;
    public final String font = "Arial";

    public final int radius = 30;
    public final int fontSize = 16;
    public final int strokeWidth = 3;

    public boolean selected = false;

    RadioButton(int bX, int bY, String label, int lX, int lY) {
        this.bX = bX;
        this.bY = bY;
        this.lX = lX;
        this.lY = lY;
        this.label = label;
    }

    public boolean isClicked(int mouseX, int mouseY) {
        return Math.abs(bX - mouseX) < radius / 2 && Math.abs(bY - mouseY) < radius / 2;
    }

    public void switchButtonState() {
        selected = !selected;
    }
}
