package ru.itis.inform;

/**
 * Created by Тимур on 25.03.2016.
 */
public class Root<T> implements Comparable<T> {
    private Root left;
    private Root right;
    private T value;
    private boolean color = false;
    private int height;

    public boolean isColor() {
        return color;
    }

    //Red - is true, black - is false
    public void setColor(boolean color) {
        this.color = color;
    }

    public Root() {
    }

    public int getHeight() {
        return height;
    }

    public Root(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setRight(Root right) {
        this.right = right;
    }

    public void setLeft(Root left) {
        this.left = left;
    }

    public Root getLeft() {
        return left;
    }

    public Root getRight() {
        return right;
    }

    public int compareTo(T b) {
        int comparableEqNumber = (Integer) b - (Integer) this.value;
        switch (comparableEqNumber) {
            case 0: {
                return 0;
            }
        }
        if (comparableEqNumber < 0)
            return -1;
        else
            return 1;
    }
}
