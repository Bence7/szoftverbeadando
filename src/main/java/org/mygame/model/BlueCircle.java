package org.mygame.model;

import java.util.ArrayList;

public class BlueCircle {
    private boolean isClicked=true;
    private MyPositionType pozicio=null;
    private static final ArrayList<MyPositionType> circlePosition = new ArrayList<>();
    private final ArrayList<MyPositionType> enemyCirclePosition = new ArrayList<>();
    private final ArrayList<MyPositionType> forbiddenPosition = new ArrayList<>();
    private final ArrayList<MyPositionType> selectablePosition = new ArrayList<>();
    private final ArrayList<MyPositionType> selectableEnemyPosition = new ArrayList<>();
    private MyPositionType actualPosition;
}
