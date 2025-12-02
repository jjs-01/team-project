package com.studyarc.view;

import java.awt.*;

public class Styling {

    private static final Font mainFont = new Font("Roboto Slab", Font.PLAIN, 26);
    private static final Font subFont = new Font("Roboto", Font.PLAIN, 18);
    private static final Font bodyFont = new Font("Lato", Font.PLAIN, 14);
    private static final int SCROLL_PACE = 30;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    public static Font getMainFont() {
        return mainFont;
    }
    public static Font getSubFont() {
        return subFont;
    }
    public static Font getBodyFont() {
        return bodyFont;
    }
    public static int getScrollPace() {
        return SCROLL_PACE;
    }
    public static int getWidth() {
        return WIDTH;
    }
    public static int getHeight() {
        return HEIGHT;
    }

    public static Color getYellow() {
        return new Color(255, 225, 143);
    }

    public static Color getGray() {
        return new Color(232, 231, 230);
    }
}
