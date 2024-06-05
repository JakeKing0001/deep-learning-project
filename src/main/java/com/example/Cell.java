package com.example;

import javax.swing.JLabel;

public class Cell extends JLabel{

    private int x;
    private int y;
    private boolean isChoosed;
    private boolean subChoosed;
    private boolean killable;
    private String coordinate;

    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isChoosed = false;
        this.subChoosed = false;
        this.killable = false;
    }

    /**
     * Returns the coordinate of the cell.
     *
     * @return the coordinate of the cell
    */
    public String getCoordinate() {
        return coordinate;
    }

    /**
     * Sets the coordinate of the cell.
     *
     * @param coordinate the new coordinate of the cell
    */
    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Returns the value of the private field x.
     *
     * @return the value of x
    */
    public int _getX() {
        return x;
    }

    /**
     * Sets the value of the private field x.
     *
     * @param  x  the new value for the x field
    */
    public void _setX(int x) {
        this.x = x;
    }

    /**
     * Returns the value of the private field y.
     *
     * @return the value of y
    */
    public int _getY() {
        return y;
    }

    /**
     * Sets the value of the private field y.
     *
     * @param  y  the new value for the y field
    */
    public void _setY(int y) {
        this.y = y;
    }

    /**
     * Returns the value of the private field isChoosed.
     *
     * @return the value of isChoosed
    */
    public boolean isChoosed() {
        return isChoosed;
    }

    /**
     * Sets the value of the private field isChoosed.
     *
     * @param  isChoosed  the new value for the isChoosed field
    */
    public void setChoosed(boolean isChoosed) {
        this.isChoosed = isChoosed;
    }

    /**
     * Returns the value of the private field subChoosed.
     *
     * @return the value of subChoosed
    */
    public boolean isSubChoosed() {
        return subChoosed;
    }

    /**
     * Sets the value of the private field subChoosed.
     *
     * @param  subChoosed  the new value for the subChoosed field
    */
    public void setSubChoosed(boolean subChoosed) {
        this.subChoosed = subChoosed;
    }

    /**
     * Returns the value of the private field killable.
     *
     * @return the value of killable
    */
    public boolean isKillable() {
        return killable;
    }

    /**
     * Sets the value of the private field killable.
     *
     * @param  killable  the new value for the killable field
    */
    public void setKillable(boolean killable) {
        this.killable = killable;
    }

    /**
     * Returns the value of the private field isWhite.
     *
     * @return the value of isWhite
    */
    protected boolean isWhite() {
        return getIcon().toString().contains("White");
    }
}