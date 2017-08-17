package in.mvcdemo.Custom;

public interface DrawableClickListener {

    void onClick(DrawablePosition target);

    enum DrawablePosition {TOP, BOTTOM, LEFT, RIGHT}
}